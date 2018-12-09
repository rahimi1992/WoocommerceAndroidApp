package com.test.newshop1.ui.profileActivity;

import android.arch.lifecycle.ViewModel;

import com.test.newshop1.data.DataRepository;

public class ProfileViewModel extends ViewModel {


    private DataRepository dataRepository;

    public ProfileViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }
}
