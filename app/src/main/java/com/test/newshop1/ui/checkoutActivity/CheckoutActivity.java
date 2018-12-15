package com.test.newshop1.ui.checkoutActivity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.shuhart.stepview.StepView;
import com.test.newshop1.R;
import com.test.newshop1.ui.ViewModelFactory;
import com.test.newshop1.utilities.InjectorUtil;
import com.zarinpal.ewallets.purchase.OnCallbackRequestPaymentListener;
import com.zarinpal.ewallets.purchase.OnCallbackVerificationPaymentListener;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;


public class CheckoutActivity extends AppCompatActivity {
    private static final String TAG = "CheckoutActivity";

    private StepView mStepView;
    private CheckoutViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_activity);

        mStepView = findViewById(R.id.cart_step_view);
        //mStepView.go(0, false);

        ViewModelFactory factory = InjectorUtil.provideViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, factory).get(CheckoutViewModel.class);

        mViewModel.getCurrentStep().observe(this, this::updateFragments);
        //mViewModel.setCurrentStep(CheckoutStep.CART);

        ZarinPal zarinPal = ZarinPal.getPurchase(this);
        mViewModel.setZarinPal(zarinPal);
        mViewModel.setOnPaymentReadyListener((status, authority, paymentGatewayUri, intent) -> {
            if (status == 100) {
                startActivity(intent);
            } else {
                Toast.makeText(CheckoutActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();
            }
        });
        Uri data = getIntent().getData();
        zarinPal.verificationPayment(data, mViewModel);
    }

    private void updateFragments(CheckoutStep currentStep) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (currentStep) {
            case CART:
                mStepView.go(0, false);
                transaction.replace(R.id.container, CartFragment.newInstance());
                break;
            case ADDRESS:
                Log.d(TAG, "updateFragments: ADDRESS");
                mStepView.go(1, false);
                transaction.replace(R.id.container, AddressFragment.newInstance());
                //transaction.addToBackStack(null);
                break;
            case PAYMENT:
                mStepView.go(2, false);
                transaction.replace(R.id.container, PaymentFragment.newInstance());
                //transaction.addToBackStack(null);

                break;
            case CONFIRM:
                mStepView.go(3, false);
                transaction.replace(R.id.container, ConfirmFragment.newInstance());
                //transaction.addToBackStack(null);
                break;
        }


        transaction.commit();
    }

    @Override
    public void onBackPressed() {

        if (mStepView.getCurrentStep() == 0) {
            super.onBackPressed();
        } else {
            mViewModel.goToPreviousStep();
        }
    }
}
