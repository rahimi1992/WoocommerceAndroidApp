package com.test.newshop1.ui.checkoutActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.test.newshop1.R;
import com.test.newshop1.data.database.customer.Customer;
import com.test.newshop1.databinding.CheckoutAddressFragBinding;
import com.test.newshop1.ui.loginActivity.LoginActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


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
        Log.d(TAG, "onCreateView: called");
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
            mViewModel.checkAddress();
        } else {
            Snackbar.make(getView(), R.string.login_force_text, Snackbar.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: called");
        super.onActivityCreated(savedInstanceState);

        mViewModel = CheckoutActivity.obtainViewModel(getActivity());

        mViewModel.getCustomerLD().observe(this, this::updateUI);
        binding.setViewModel(mViewModel);


    }

    private void updateUI(Customer customer) {

        Log.d(TAG, "updateUI: called");
        isLoggedIn = customer != null;
        binding.setCustomer(customer);
        //Log.d(TAG, "updateUI: customer is null: " + (customer == null));
//        Log.d(TAG, "updateUI: customer has billing: " + (customer.hasBilling()));
        if (customer != null) {
            Log.d(TAG, "updateUI: updating billing Address" );
            mViewModel.updateAddress(customer.getBilling());
        }
    }

}
