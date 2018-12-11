package com.test.newshop1.ui.checkoutActivity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.util.Log;

import com.test.newshop1.data.DataRepository;
import com.test.newshop1.data.ResponseCallback;
import com.test.newshop1.data.database.coupon.Coupon;
import com.test.newshop1.data.database.customer.Customer;
import com.test.newshop1.data.database.order.Order;
import com.test.newshop1.data.database.order.ShippingLine;
import com.test.newshop1.data.database.payment.PaymentGateway;
import com.test.newshop1.data.database.shipping.ShippingMethod;
import com.test.newshop1.data.database.shoppingcart.CartItem;
import com.test.newshop1.utilities.PersianTextUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckoutViewModel extends ViewModel {
    private static final String TAG = "CheckoutViewModel";

    public ObservableField<String> shippingCostText = new ObservableField<>();
    public ObservableField<String> totalPriceText = new ObservableField<>();
    public ObservableField<String> discountText = new ObservableField<>();
    public ObservableField<String> totalPayment = new ObservableField<>();
    public ObservableBoolean loadingCoupon = new ObservableBoolean(false);
    public ObservableBoolean isCouponEnabled = new ObservableBoolean(true);

    private DataRepository dataRepository;
    private MutableLiveData<CheckoutStep> currentStep;
    private List<ShippingMethod> shippingMethods;
    private Integer totalPrice;
    private Integer shippingCost = 0;
    private Integer discountAmount = 0;
    private LiveData<Customer> customerLD;
    private MutableLiveData<List<ShippingMethod>> validShippingMethods = new MutableLiveData<>();
    private MutableLiveData<List<PaymentGateway>> validPayments = new MutableLiveData<>();
    private ShippingMethod selectedShippingMethod;
    private PaymentGateway selectedPaymentMethod;
    private LiveData<List<CartItem>> cartItemsLD;
    private List<CartItem> cartItems = new ArrayList<>();

    private boolean isCouponValidated = false;


    public CheckoutViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
        this.currentStep = new MutableLiveData<>();
        this.customerLD = dataRepository.getLoggedInCustomer();
        this.shippingMethods = new ArrayList<>();
        this.cartItemsLD = Transformations.map(dataRepository.getCartItems(), items -> items);
        currentStep.setValue(CheckoutStep.CART);
        initShippingAndPaymentMethods();
    }


    public void setSelectedShippingMethod(ShippingMethod selectedShippingMethod) {
        this.shippingCostText.set(PersianTextUtil.toPer(selectedShippingMethod.getCostValue()));
        this.selectedShippingMethod = selectedShippingMethod;
        this.shippingCost = Integer.valueOf(selectedShippingMethod.getCostValue());
        updatePaymentDetails();
    }


    public PaymentGateway getSelectedPaymentMethod() {
        return selectedPaymentMethod;
    }

    public ShippingMethod getSelectedShippingMethod() {
        return selectedShippingMethod;
    }

    void setSelectedPaymentMethod(PaymentGateway selectedPaymentMethod) {
        this.selectedPaymentMethod = selectedPaymentMethod;
    }

    void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
        this.totalPriceText.set(PersianTextUtil.toPer(totalPrice));
        validateShippingMethods();
        updatePaymentDetails();
    }

    private void updatePaymentDetails() {
        discountText.set(PersianTextUtil.toPer(discountAmount));
        totalPayment.set(PersianTextUtil.toPer(totalPrice + shippingCost - discountAmount));
    }

    public LiveData<Customer> getCustomerLD() {
        return customerLD;
        //return null;
    }

    LiveData<List<ShippingMethod>> getValidShippingMethods(){
        return validShippingMethods;
    }

    LiveData<List<PaymentGateway>> getValidPayments(){
        return validPayments;
    }


    MutableLiveData<CheckoutStep> getCurrentStep() {
        return currentStep;
    }

    void setCurrentStep(CheckoutStep currentStep) {
        this.currentStep.postValue(currentStep);
    }


    LiveData<List<CartItem>> getCartItemsLD(){
        return cartItemsLD;
    }

    void deleteItem(int id) {
        dataRepository.removeCartItem(id);
    }

    void addItem(int id){
        dataRepository.addItem(id);
    }

    void decreaseItem(int id) {
        dataRepository.decreaseItem(id);
    }

    private void initShippingAndPaymentMethods(){
        Log.d(TAG, "initShippingAndPaymentMethods: called");
        dataRepository.getShippingMethods(new ResponseCallback<List<ShippingMethod>>() {
            @Override
            public void onLoaded(List<ShippingMethod> response) {
                shippingMethods = response;
                validateShippingMethods();
                Log.d(TAG, "onLoaded: called");
            }

            @Override
            public void onDataNotAvailable() {
                shippingMethods = null;
                Log.d(TAG, "onDataNotAvailable: called");
            }
        });

        dataRepository.getPayments(new ResponseCallback<List<PaymentGateway>>() {
            @Override
            public void onLoaded(List<PaymentGateway> response) {
                validatePayments(response);
            }

            @Override
            public void onDataNotAvailable() {
                validPayments.postValue(null);
            }
        });


    }

    public LiveData<Integer> getTotalPrice(){return dataRepository.getTotalPrice();}

    private void validatePayments(List<PaymentGateway> paymentGateways) {
        List<PaymentGateway> methods = new ArrayList<>();
        for (PaymentGateway paymentGateway : paymentGateways) {
            if (paymentGateway.isEnabled()){
                methods.add(paymentGateway);
            }
        }
        validPayments.postValue(methods);
    }


    private void validateShippingMethods() {
        List<ShippingMethod> methods = new ArrayList<>();
        Log.d(TAG, "validateShippingMethods: " + totalPrice);
        if (totalPrice != null && shippingMethods != null){
            for (ShippingMethod method : shippingMethods) {

                if (!method.isEnabled())
                    continue;

                if (method.getMethodId().equals("free_shipping")) {
                    if (isValidFreeShipping(method, totalPrice)) {
                        validShippingMethods.postValue(Collections.singletonList(method));
                        return;
                    }
                } else {
                    methods.add(method);
                }

            }
            validShippingMethods.postValue(methods);
        }
    }


    private boolean isValidFreeShipping(ShippingMethod method, Integer total) {
        Log.d(TAG, "isValidFreeShipping: calling");
        int minTotalPrice = Integer.valueOf(method.getSettings().getMinAmount().getValue());
        return total >= minTotalPrice;
    }

    void goToPreviousStep() {
        if (currentStep.getValue() != null) {
            switch (currentStep.getValue()) {
                case ADDRESS:
                    currentStep.postValue(CheckoutStep.CART);
                    break;
                case PAYMENT:
                    currentStep.postValue(CheckoutStep.ADDRESS);
                    break;
                case CONFIRM:
                    currentStep.postValue(CheckoutStep.PAYMENT);
                    break;
            }
        }
    }

    void loadCoupon(String coupon) {

        if (TextUtils.isEmpty(coupon.trim())){
            discountAmount = 0;
            isCouponValidated = false;
            return;
        }
        isCouponEnabled.set(false);
        loadingCoupon.set(true);
        dataRepository.getCoupon(coupon, new ResponseCallback<List<Coupon>>(){
            @Override
            public void onLoaded(List<Coupon> response) {
                validateCoupon(response);
            }

            @Override
            public void onDataNotAvailable() {
                discountAmount = 0;
                updatePaymentDetails();
                loadingCoupon.set(false);
                isCouponValidated = false;
                isCouponEnabled.set(true);
            }
        });
    }

    private void validateCoupon(List<Coupon> coupons) {

        List<CartItem> items = cartItemsLD.getValue();
        //Log.d(TAG, "validateCoupon: validating");
        CouponValidator validator = new CouponValidator(items, coupons);
        Log.d(TAG, "validateCoupon: " + validator.getResultStatus());

        cartItems = validator.getResultItems();
        discountAmount = validator.getDiscountAmount().intValue();
        updatePaymentDetails();
        loadingCoupon.set(false);
        isCouponEnabled.set(discountAmount == 0);
        isCouponValidated = true;


    }

    public void removeCode(){
        discountAmount = 0;
        isCouponEnabled.set(true);
        isCouponValidated = false;
        updatePaymentDetails();
    }

    public void completeOrder() {
        Customer customer = customerLD.getValue();

        if (!isCouponValidated){
            Log.d(TAG, "completeOrder: no Coupon");
            cartItems.clear();

            for (CartItem cartItem : cartItemsLD.getValue()) {
                cartItems.add(cartItem.cloneItem());
            }
        }

//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        Log.d(TAG, "completeOrder: " + gson.toJson(cartItems));


        Order order = new Order(Order.PENDING, customer.getId(),
                "", customer.getBilling(), customer.getShipping(),
                selectedPaymentMethod.getTitle(), selectedPaymentMethod.getMethodTitle(), cartItems,"");

        ShippingLine shippingLine = new ShippingLine(selectedShippingMethod.getTitle(), selectedPaymentMethod.getId(),String.valueOf(shippingCost));
        order.setShippingLines(Collections.singletonList(shippingLine));

        dataRepository.saveOrder(order);
    }
}
