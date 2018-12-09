package com.test.newshop1.ui.checkoutActivity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.newshop1.R;
import com.test.newshop1.data.database.customer.Customer;
import com.test.newshop1.databinding.CheckoutAddressFragBinding;
import com.test.newshop1.ui.ViewModelFactory;
import com.test.newshop1.ui.loginActivity.LoginActivity;
import com.test.newshop1.utilities.InjectorUtil;

import java.util.Objects;


public class AddressFragment extends Fragment {
    private static final String TAG = "AddressFragment";

    private CheckoutViewModel mViewModel;
    private CheckoutAddressFragBinding binding;
    private Boolean isLoggedIn = false;

    public static AddressFragment newInstance() {
        return new AddressFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = CheckoutAddressFragBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        root.findViewById(R.id.next_btn).setOnClickListener(view -> nextStep());
        root.findViewById(R.id.login_btn).setOnClickListener(view -> goToLogin());
        return root;
    }

    private void goToLogin() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    private void nextStep() {
        Log.d(TAG, "nextStep: address to payment");
        if (isLoggedIn){
            mViewModel.setCurrentStep(CheckoutStep.PAYMENT);
        } else {
            Snackbar.make(getView(), R.string.login_force_text, Snackbar.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewModelFactory factory = InjectorUtil.provideViewModelFactory(Objects.requireNonNull(getActivity()));
        mViewModel = ViewModelProviders.of(getActivity(), factory).get(CheckoutViewModel.class);

        mViewModel.getCustomerLD().observe(this, this::updateUI);
        //binding.setViewmodel(mViewModel);
    }

    private void updateUI(Customer customer) {

        isLoggedIn = customer != null;
        binding.setCustomer(customer);
    }

}
