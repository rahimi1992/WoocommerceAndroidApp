package com.test.newshop1.ui.profileActivity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.test.newshop1.R;
import com.test.newshop1.ui.BaseActivity;
import com.test.newshop1.ui.ViewModelFactory;
import com.test.newshop1.ui.loginActivity.LoginActivityViewModel;
import com.test.newshop1.utilities.InjectorUtil;

public class ProfileActivity extends AppCompatActivity {

    private ProfileViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        ViewModelFactory factory = InjectorUtil.provideViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, factory).get(ProfileViewModel.class);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ProfileFragment.newInstance())
                    .commitNow();
        }
    }
}
