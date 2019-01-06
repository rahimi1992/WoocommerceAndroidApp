package com.test.newshop1.ui.ordersActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.app.TaskStackBuilder;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.test.newshop1.R;
import com.zarinpal.ewallets.purchase.ZarinPal;

public class OrdersActivity extends AppCompatActivity {
    private static final String TAG = "OrdersActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, OrdersFragment.newInstance())
                    .commitNow();
        }
        handleIntent(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        Uri data = intent.getData();
        ZarinPal zarinPal = ZarinPal.getPurchase(this);
        zarinPal.verificationPayment(data, (isPaymentSuccess, refID, paymentRequest) -> {

            Log.d(TAG, "onCallbackResultVerificationPayment: called");
            String orderId1 = paymentRequest.getCallBackURL().split("_")[1];
            if (isPaymentSuccess) {
                Log.d(TAG, "onCallbackResultVerificationPayment: trying to set payment to true: " + orderId1);
                //dataRepository.updateOrder(orderId, new Order(true));
            } else {
                Log.d(TAG, "onCallbackResultVerificationPayment: Not successful");

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)){
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                } else {
                    NavUtils.navigateUpTo(this, upIntent);
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
