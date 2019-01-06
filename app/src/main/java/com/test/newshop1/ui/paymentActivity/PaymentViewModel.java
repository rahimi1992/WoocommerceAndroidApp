package com.test.newshop1.ui.paymentActivity;

import android.util.Log;

import com.test.newshop1.data.DataRepository;
import com.test.newshop1.data.ResponseCallback;
import com.test.newshop1.data.database.order.Order;
import com.test.newshop1.data.database.payment.PaymentGateway;
import com.test.newshop1.ui.checkoutActivity.CheckoutStep;
import com.zarinpal.ewallets.purchase.OnCallbackRequestPaymentListener;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PaymentViewModel extends ViewModel {
    private static final String TAG = "PaymentViewModel";

    public ObservableBoolean initializing = new ObservableBoolean(true);

    private DataRepository dataRepository;
    private MutableLiveData<String> paymentMethodId = new MutableLiveData<>();
    private PaymentGateway paymentGateway;

    //private LiveData<PaymentGateway> paymentGateway = Transformations.switchMap(paymentMethodId,(id) -> dataRepository.getPayment(id));
    private ZarinPal zarinPal;

    private OnCallbackRequestPaymentListener onPaymentReadyListener;

    public PaymentViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public void startPayment(Order order) {
        Log.d(TAG, "startPayment: called");
        zarinPal.startPayment(createPaymentRequest(order), onPaymentReadyListener);
    }

    public void setPaymentMethodId(String paymentMethodId) {
        Log.d(TAG, "setPaymentMethodId: " + paymentMethodId);
        dataRepository.getPayment(paymentMethodId, new ResponseCallback<PaymentGateway>() {
            @Override
            public void onLoaded(PaymentGateway response) {
                Log.d(TAG, "onLoaded: loaded");
                initializing.set(false);
                paymentGateway = response;
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG, "onDataNotAvailable: ");
                initializing.set(false);
            }
        });
    }

    private PaymentRequest createPaymentRequest(Order order) {

        Log.d(TAG, "createPaymentRequest: called");
//        int amount = Integer.valueOf(order.getTotal());

        int amount = 100;

//        if (paymentGateway.getValue() == null) {
//            Log.d(TAG, "createPaymentRequest: payment gateway is null");
//            return null;
//        }
        String merchantId = paymentGateway.getSettings().getMerchantcode().getValue();

        Log.d(TAG, "createPaymentRequest: " + merchantId);

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
