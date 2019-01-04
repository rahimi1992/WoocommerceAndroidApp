package com.test.newshop1.ui.checkoutActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.shuhart.stepview.StepView;
import com.test.newshop1.R;
import com.test.newshop1.ui.SnackbarMessageId;
import com.test.newshop1.ui.SnackbarMessageText;
import com.test.newshop1.ui.ViewModelFactory;
import com.test.newshop1.utilities.InjectorUtil;
import com.test.newshop1.utilities.SnackbarUtils;
import com.zarinpal.ewallets.purchase.ZarinPal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;


public class CheckoutActivity extends AppCompatActivity {
    private static final String TAG = "CheckoutActivity";


    private StepView mStepView;
    private CheckoutViewModel mViewModel;
    private View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: called");
        setContentView(R.layout.activity_checkout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_clear);
        }

        parentLayout = findViewById(android.R.id.content);
        mStepView = findViewById(R.id.cart_step_view);
        //mStepView.go(0, false);


        mViewModel = obtainViewModel(this);

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
        setupSnackBar();



    }

    private void setupSnackBar() {

        mViewModel.getSnackbarMessageText().observe(this, (SnackbarMessageText.SnackbarObserver) snackbarMessageText -> SnackbarUtils.showSnackbar(parentLayout, snackbarMessageText));
        mViewModel.getSnackbarMessageId().observe(this, (SnackbarMessageId.SnackbarObserver) snackbarMessageResourceId -> SnackbarUtils.showSnackbar(parentLayout, getString(snackbarMessageResourceId)));
    }

    public static CheckoutViewModel obtainViewModel(FragmentActivity activity) {

        ViewModelFactory factory = InjectorUtil.provideViewModelFactory(activity);

        return ViewModelProviders.of(activity, factory).get(CheckoutViewModel.class);
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
            finish();
            overridePendingTransition(0, R.anim.fade_out);
        } else {
            mViewModel.goToPreviousStep();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
