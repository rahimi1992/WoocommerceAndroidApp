package com.test.newshop1.ui.paymentActivity;

import com.test.newshop1.data.DataRepository;
import com.test.newshop1.data.database.order.Order;
import com.test.newshop1.data.database.payment.PaymentGateway;
import com.zarinpal.ewallets.purchase.OnCallbackRequestPaymentListener;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PaymentViewModel extends ViewModel {

    private DataRepository dataRepository;
    private MutableLiveData<String> paymentMethodId = new MutableLiveData<>();

    private LiveData<PaymentGateway> paymentGateway = Transformations.switchMap(paymentMethodId,(id) -> dataRepository.getPayment(id));
    private ZarinPal zarinPal;

    private OnCallbackRequestPaymentListener onPaymentReadyListener;

    public PaymentViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    private void zarinPalPayment(Order order) {

        zarinPal.startPayment(createPaymentRequest(order), onPaymentReadyListener);
    }

    private PaymentRequest createPaymentRequest(Order order) {

//        int amount = Integer.valueOf(order.getTotal());

        int amount = 100;

        if (paymentGateway.getValue() == null) {
            return null;
        }
        String merchantId = paymentGateway.getValue().getSettings().getMerchantcode().getValue();

        PaymentRequest paymentRequest = ZarinPal.getPaymentRequest();
        paymentRequest.setMerchantID(merchantId);
        paymentRequest.setAmount(amount);
        paymentRequest.setDescription("تست");
        paymentRequest.setCallbackURL("new-shop-checkout://order_" + order.getId());     /* Your App Scheme */
        paymentRequest.setMobile(order.getBilling().getPhone());            /* Optional Parameters */
        paymentRequest.setEmail(order.getBilling().getEmail());     /* Optional Parameters */

        return paymentRequest;
    }

    LiveData<Order> getOrder(String orderId) {
        return dataRepository.getOrder(orderId);
    }

    public void setZarinPal(ZarinPal zarinPal) {
        this.zarinPal = zarinPal;
    }

    public void setOnPaymentReadyListener(OnCallbackRequestPaymentListener onPaymentReadyListener) {
        this.onPaymentReadyListener = onPaymentReadyListener;
    }

}
