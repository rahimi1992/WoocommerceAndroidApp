package com.test.newshop1.ui.checkoutActivity;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
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

import java.util.List;

public class PaymentFragment extends Fragment {
    private static final String TAG = "PaymentFragment";

    private CheckoutViewModel mViewModel;
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
        mViewModel.completeOrder();
        //mViewModel.setCurrentStep(CheckoutStep.CONFIRM);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = CheckoutActivity.obtainViewModel(getActivity());
        binding.setViewmodel(mViewModel);

        mViewModel.getValidShippingMethods().observe(this, this::updateShippingMethods);
        mViewModel.getValidPayments().observe(this, this::updatePayments);
        mViewModel.getTotalPrice().observe(this, aFloat -> mViewModel.setTotalPrice(aFloat));
        mViewModel.loadCoupon("");
        binding.getRoot().findViewById(R.id.discount_btn).setOnClickListener(v -> mViewModel.loadCoupon(couponET.getText().toString()));
    }


    private void updatePayments(List<PaymentGateway> paymentGateways) {
        mViewModel.setSelectedPaymentMethod(null);
        paymentRG.removeAllViews();
        if (paymentGateways != null && paymentGateways.size() != 0){
            for (PaymentGateway paymentGateway : paymentGateways) {
                RadioButton rb = new RadioButton(getContext());
                rb.setText(paymentGateway.getTitle());
                rb.setTag(paymentGateway.getId());
                rb.setOnCheckedChangeListener((buttonView, isChecked) -> {

                    if (isChecked)
                        mViewModel.setSelectedPaymentMethod(paymentGateway);
                });

                paymentRG.addView(rb);
            }
            if (paymentRG.getChildCount() == 1){
                ((RadioButton) paymentRG.getChildAt(0)).setChecked(true);
            }
        }

    }

    private void updateShippingMethods(List<ShippingMethod> shippingMethods) {
        Log.d(TAG, "updateShippingMethods: updating");
        mViewModel.setSelectedShippingMethod(null);
        shippingRG.removeAllViews();
        if (shippingMethods != null && shippingMethods.size() != 0){
            for (ShippingMethod shippingMethod : shippingMethods) {
                RadioButton rb = new RadioButton(getContext());
                rb.setText(shippingMethod.getTitle());
                rb.setTag(shippingMethod.getId());
                rb.setOnCheckedChangeListener((buttonView, isChecked) -> {

                    if (isChecked) {
                        mViewModel.setSelectedShippingMethod(shippingMethod);
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
