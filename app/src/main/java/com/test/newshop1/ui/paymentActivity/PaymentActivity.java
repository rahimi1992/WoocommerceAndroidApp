package com.test.newshop1.ui.paymentActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.test.newshop1.R;
import com.test.newshop1.data.database.order.Order;
import com.test.newshop1.databinding.ActivityPaymentBinding;
import com.test.newshop1.ui.ViewModelFactory;
import com.test.newshop1.utilities.InjectorUtil;
import com.zarinpal.ewallets.purchase.ZarinPal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.app.TaskStackBuilder;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class PaymentActivity extends AppCompatActivity {
    private static final String TAG = "PaymentActivity";
    public static final String ORDER_ID = "order-id";

    private ActivityPaymentBinding binding;
    private Order order;
    private PaymentViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewModelFactory factory = InjectorUtil.provideViewModelFactory(this);
        viewModel = ViewModelProviders.of(this, factory).get(PaymentViewModel.class);
        binding.setViewModel(viewModel);

        String orderId = getIntent().getStringExtra(ORDER_ID);

        viewModel.getOrder(orderId).observe(this, this::updateUi);

        ZarinPal zarinPal = ZarinPal.getPurchase(this);
        viewModel.setZarinPal(zarinPal);
        viewModel.setOnPaymentReadyListener((status, authority, paymentGatewayUri, intent) -> {
            if (status == 100) {
                startActivity(intent);
            } else {
                Toast.makeText(PaymentActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();
            }
        });



        //findViewById(R.id.paymentBtn).setOnClickListener(v -> viewModel.startPayment(order));

    }

    private void updateUi(Order order) {
        if (order != null){
            Log.d(TAG, "updateUi: " + order.getId());
            this.order = order;
            viewModel.setPaymentMethodId(order.getPaymentMethod());
            binding.setOrder(order);
        }
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
