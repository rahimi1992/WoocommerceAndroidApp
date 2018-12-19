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

import com.test.newshop1.R;
import com.test.newshop1.databinding.CheckoutConfirmFragBinding;


public class ConfirmFragment extends Fragment {
    private static final String TAG = "AddressFragment";

    private CheckoutViewModel mViewModel;
    private CheckoutConfirmFragBinding binding;

    public static ConfirmFragment newInstance() {
        return new ConfirmFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = CheckoutConfirmFragBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        root.findViewById(R.id.next_btn).setOnClickListener(view -> nextStep());
        return root;
    }

    private void nextStep() {
        Log.d(TAG, "nextStep: address to payment");
        //mViewModel.setCurrentStep(CheckoutStep.CONFIRM);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = CheckoutActivity.obtainViewModel(getActivity());
        binding.setViewmodel(mViewModel);

    }

}
