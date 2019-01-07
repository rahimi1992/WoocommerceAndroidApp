package com.test.newshop1.ui.checkoutActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.test.newshop1.R;
import com.test.newshop1.data.database.payment.PaymentGateway;
import com.test.newshop1.data.database.shipping.ShippingMethod;
import com.test.newshop1.databinding.CheckoutPaymentFragBinding;
import com.test.newshop1.ui.paymentActivity.PaymentActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PaymentFragment extends Fragment {

    private CheckoutViewModel viewModel;
    private CheckoutPaymentFragBinding binding;
    private RadioGroup shippingRG;
    private RadioGroup paymentRG;
    private EditText couponET;
    

    public static PaymentFragment newInstance() {
        return new PaymentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = CheckoutPaymentFragBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        root.findViewById(R.id.next_btn).setOnClickListener(view -> nextStep());
        shippingRG = root.findViewById(R.id.shipping);
        paymentRG = root.findViewById(R.id.payment);
        couponET = root.findViewById(R.id.coupon_et);



        return root;
    }

    private void nextStep() {
        viewModel.completeOrder();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = CheckoutActivity.obtainViewModel(getActivity());
        binding.setViewmodel(viewModel);

        viewModel.getValidShippingMethods().observe(this, this::updateShippingMethods);
        viewModel.getValidPayments().observe(this, this::updatePayments);
        viewModel.getTotalPrice().observe(this, aFloat -> viewModel.setTotalPrice(aFloat));
        viewModel.getPaymentEvent().observe(this, this::startPayment);
        viewModel.loadCoupon("");
        binding.getRoot().findViewById(R.id.discount_btn).setOnClickListener(v -> viewModel.loadCoupon(couponET.getText().toString()));
    }


    private void startPayment(Integer orderId){
        if (orderId != null) {
            Intent intent = new Intent(getActivity(), PaymentActivity.class);
            intent.putExtra(PaymentActivity.ORDER_ID, String.valueOf(orderId));
            startActivity(intent);
        }
    }


    private void updatePayments(List<PaymentGateway> paymentGateways) {
        viewModel.setSelectedPaymentMethod(null);
        paymentRG.removeAllViews();
        if (paymentGateways != null && paymentGateways.size() != 0){
            for (PaymentGateway paymentGateway : paymentGateways) {
                RadioButton rb = new RadioButton(getContext());
                rb.setText(paymentGateway.getTitle());
                rb.setTag(paymentGateway.getId());
                rb.setOnCheckedChangeListener((buttonView, isChecked) -> {

                    if (isChecked)
                        viewModel.setSelectedPaymentMethod(paymentGateway);
                });

                paymentRG.addView(rb);
            }
            if (paymentRG.getChildCount() == 1){
                ((RadioButton) paymentRG.getChildAt(0)).setChecked(true);
            }
        }

    }

    private void updateShippingMethods(List<ShippingMethod> shippingMethods) {
        viewModel.setSelectedShippingMethod(null);
        shippingRG.removeAllViews();
        if (shippingMethods != null && shippingMethods.size() != 0){
            for (ShippingMethod shippingMethod : shippingMethods) {
                RadioButton rb = new RadioButton(getContext());
                rb.setText(shippingMethod.getTitle());
                rb.setTag(shippingMethod.getId());
                rb.setOnCheckedChangeListener((buttonView, isChecked) -> {

                    if (isChecked) {
                        viewModel.setSelectedShippingMethod(shippingMethod);
                    }
                });

                shippingRG.addView(rb);
            }
            if (shippingRG.getChildCount() == 1){
                ((RadioButton) shippingRG.getChildAt(0)).setChecked(true);
            }
        }


    }



}
