package com.test.newshop1.ui.checkoutActivity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.test.newshop1.ui.ViewModelFactory;
import com.test.newshop1.utilities.InjectorUtil;

import java.util.List;
import java.util.Objects;

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
        ViewModelFactory factory = InjectorUtil.provideViewModelFactory(Objects.requireNonNull(getActivity()));
        mViewModel = ViewModelProviders.of(getActivity(), factory).get(CheckoutViewModel.class);
        binding.setViewmodel(mViewModel);

        mViewModel.getValidShippingMethods().observe(this, this::updateShippingMethods);
        mViewModel.getValidPayments().observe(this, this::updatePayments);
        mViewModel.getTotalPrice().observe(this, aFloat -> mViewModel.setTotalPrice(aFloat));
        mViewModel.loadCoupon("");
        binding.getRoot().findViewById(R.id.discount_btn).setOnClickListener(v -> mViewModel.loadCoupon(couponET.getText().toString()));
    }


    private void updatePayments(List<PaymentGateway> paymentGateways) {
        paymentRG.removeAllViews();
        if (paymentGateways != null && paymentGateways.size() != 0){
            for (PaymentGateway paymentGateway : paymentGateways) {
                RadioButton rb = new RadioButton(getContext());
                rb.setText(paymentGateway.getTitle());
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
        shippingRG.removeAllViews();
        if (shippingMethods != null && shippingMethods.size() != 0){
            for (ShippingMethod shippingMethod : shippingMethods) {
                RadioButton rb = new RadioButton(getContext());
                rb.setText(shippingMethod.getTitle());
                rb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked)
                        mViewModel.setSelectedShippingMethod(shippingMethod);
                });
                shippingRG.addView(rb);
            }
            if (shippingRG.getChildCount() == 1){
                ((RadioButton) shippingRG.getChildAt(0)).setChecked(true);
            }
        }
    }



}
