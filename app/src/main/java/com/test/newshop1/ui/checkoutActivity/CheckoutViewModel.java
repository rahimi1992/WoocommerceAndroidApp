package com.test.newshop1.ui.checkoutActivity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.util.Log;

import com.test.newshop1.R;
import com.test.newshop1.data.DataRepository;
import com.test.newshop1.data.ResponseCallback;
import com.test.newshop1.data.database.coupon.Coupon;
import com.test.newshop1.data.database.customer.Billing;
import com.test.newshop1.data.database.customer.Customer;
import com.test.newshop1.data.database.order.Order;
import com.test.newshop1.data.database.order.ShippingLine;
import com.test.newshop1.data.database.payment.PaymentGateway;
import com.test.newshop1.data.database.shipping.ShippingMethod;
import com.test.newshop1.data.database.shoppingcart.CartItem;
import com.test.newshop1.ui.SnackbarMessageId;
import com.test.newshop1.ui.SnackbarMessageText;
import com.test.newshop1.utilities.PersianTextUtil;
import com.zarinpal.ewallets.purchase.OnCallbackRequestPaymentListener;
import com.zarinpal.ewallets.purchase.OnCallbackVerificationPaymentListener;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckoutViewModel extends ViewModel implements OnCallbackVerificationPaymentListener {
    private static final String TAG = "CheckoutViewModel";

    public ObservableField<String> shippingCostText = new ObservableField<>();
    public ObservableField<String> totalPriceText = new ObservableField<>();
    public ObservableField<String> discountText = new ObservableField<>();
    public ObservableField<String> totalPayment = new ObservableField<>();
    public ObservableField<String> orderingMessage = new ObservableField<>();
    public ObservableBoolean loadingCoupon = new ObservableBoolean(false);
    public ObservableBoolean isCouponEnabled = new ObservableBoolean(true);
    public ObservableBoolean isPaymentSelected = new ObservableBoolean(false);
    public ObservableBoolean isShippingSelected = new ObservableBoolean(false);

    public ObservableField<Billing> billingObservableField = new ObservableField<>();

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
    private final SnackbarMessageId mSnackbarMessageId = new SnackbarMessageId();
    private final SnackbarMessageText mSnackbarMessageText = new SnackbarMessageText();


    private boolean isCouponValidated = false;

    private ZarinPal zarinPal;
    private OnCallbackRequestPaymentListener onPaymentReadyListener;

    public CheckoutViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
        this.currentStep = new MutableLiveData<>();
        this.customerLD = dataRepository.getLoggedInCustomer();
        this.shippingMethods = new ArrayList<>();
        this.cartItemsLD = Transformations.map(dataRepository.getCartItems(), items -> items);
        currentStep.setValue(CheckoutStep.CART);
        initShippingAndPaymentMethods();
    }

    public void setZarinPal(ZarinPal zarinPal) {
        this.zarinPal = zarinPal;
    }

    public void setSelectedShippingMethod(ShippingMethod selectedShippingMethod) {
        if (selectedShippingMethod != null) {
            isShippingSelected.set(true);
            this.shippingCostText.set(PersianTextUtil.toPer(selectedShippingMethod.getCostValue()));
            this.shippingCost = Integer.valueOf(selectedShippingMethod.getCostValue());
            updatePaymentDetails();
        }else {
            isShippingSelected.set(false);
        }
        this.selectedShippingMethod = selectedShippingMethod;

    }

    void setSelectedPaymentMethod(PaymentGateway selectedPaymentMethod) {

        isPaymentSelected.set(selectedPaymentMethod != null);
        this.selectedPaymentMethod = selectedPaymentMethod;
    }

    public ShippingMethod getSelectedShippingMethod() {
        return selectedShippingMethod;
    }

    public PaymentGateway getSelectedPaymentMethod() {
        return selectedPaymentMethod;
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

    public SnackbarMessageId getSnackbarMessageId() {
        return mSnackbarMessageId;
    }

    public SnackbarMessageText getSnackbarMessageText() {
        return mSnackbarMessageText;
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
                //connectionError();
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
                //connectionError();
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
        mSnackbarMessageText.setValue(validator.getResultStatus());


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

        if (validPayments.getValue() == null || shippingMethods == null){
            initShippingAndPaymentMethods();
            mSnackbarMessageId.setValue(R.string.connection_error_message);
            return;
        }

        if (selectedPaymentMethod == null) {
            isPaymentSelected.set(false);
            mSnackbarMessageId.setValue(R.string.select_payment_method_message);
            return;
        }

        if ((selectedShippingMethod == null)) {
            isShippingSelected.set(false);
            mSnackbarMessageId.setValue(R.string.select_shipping_method_message);
            return;
        }


        Order order = new Order(Order.PENDING, customer.getId(),
                "", billingObservableField.get(), customer.getShipping(),
                selectedPaymentMethod.getTitle(), selectedPaymentMethod.getMethodTitle(), cartItems,"");

        ShippingLine shippingLine = new ShippingLine(selectedShippingMethod.getTitle(), selectedPaymentMethod.getId(),String.valueOf(shippingCost));
        order.setShippingLines(Collections.singletonList(shippingLine));
        dataRepository.saveOrder(order, new ResponseCallback<Order>() {
            @Override
            public void onLoaded(Order response) {
                goToPayment(response);
            }

            @Override
            public void onDataNotAvailable() {
                connectionError();
            }
        });
    }

    private void goToPayment(Order order) {
        switch (selectedPaymentMethod.getId()) {
            case "WC_ZPal":
                zarinPalPayment(order);
                orderingMessage.set("در حال انتقال به صفحه پرداخت");

                break;
            case "cod":
                orderingMessage.set("سفارش شما با موفقیت ثبت شد");
                break;
        }
        setCurrentStep(CheckoutStep.CONFIRM);
    }

    private void zarinPalPayment(Order order) {

        zarinPal.startPayment(createPaymentRequest(order), onPaymentReadyListener);
    }

    private PaymentRequest createPaymentRequest(Order order) {

//        int amount = Integer.valueOf(order.getTotal());
        int amount = 100;

        String merchantId = selectedPaymentMethod.getSettings().getMerchantcode().getValue();

        PaymentRequest paymentRequest = ZarinPal.getPaymentRequest();
        paymentRequest.setMerchantID(merchantId);
        paymentRequest.setAmount(amount);
        paymentRequest.setDescription("تست");
        paymentRequest.setCallbackURL("femeloapp1://app_" + order.getId());     /* Your App Scheme */
        paymentRequest.setMobile(order.getBilling().getPhone());            /* Optional Parameters */
        paymentRequest.setEmail(order.getBilling().getEmail());     /* Optional Parameters */

        return paymentRequest;
    }

    public void setOnPaymentReadyListener(OnCallbackRequestPaymentListener onPaymentReadyListener) {
        this.onPaymentReadyListener = onPaymentReadyListener;
    }

    @Override
    public void onCallbackResultVerificationPayment(boolean isPaymentSuccess, String refID, PaymentRequest paymentRequest) {
        currentStep.setValue(CheckoutStep.CONFIRM);
        Log.d(TAG, "onCallbackResultVerificationPayment: called");
        String orderId = paymentRequest.getCallBackURL().split("_")[1];
        if (isPaymentSuccess) {
            Log.d(TAG, "onCallbackResultVerificationPayment: trying to set payment to true: " + orderId);
            dataRepository.updateOrder(orderId, new Order(true));
        } else {

        }

    }

    private void connectionError(){
        mSnackbarMessageId.setValue(R.string.connection_error_message);
    }

    public void updateAddress(Billing billing) {
        this.billingObservableField.set(billing);
    }

    public void checkAddress() {
        Billing billing = billingObservableField.get();
        if (billing != null) {
            if (!billing.hasFirstName()) {
                mSnackbarMessageId.setValue(R.string.empty_first_name_message);
                return;
            }
            if (!billing.hasLastName()) {
                mSnackbarMessageId.setValue(R.string.empty_last_name_message);
                return;
            }
            if (!billing.hasPhone()) {
                mSnackbarMessageId.setValue(R.string.empty_phone_message);
                return;
            }
            if (!billing.hasState()) {
                mSnackbarMessageId.setValue(R.string.empty_state_message);
                return;
            }
            if (!billing.hasCity()) {
                mSnackbarMessageId.setValue(R.string.empty_city_message);
                return;
            }
            if (!billing.hasAddress1()) {
                mSnackbarMessageId.setValue(R.string.empty_address_message);
                return;
            }

        } else {
            return;
        }
        setCurrentStep(CheckoutStep.PAYMENT);
    }
}
