package com.test.newshop1.ui.profileActivity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.newshop1.R;
import com.test.newshop1.ui.ViewModelFactory;
import com.test.newshop1.utilities.InjectorUtil;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewModelFactory factory = InjectorUtil.provideViewModelFactory(getActivity());
        mViewModel = ViewModelProviders.of(getActivity(), factory).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

}
