package com.test.newshop1.ui.loginActivity;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.test.newshop1.data.DataRepository;
import com.test.newshop1.data.ResponseCallback;
import com.test.newshop1.data.database.customer.LoginData;

public class LoginActivityViewModel extends ViewModel {
    private static final String TAG = LoginActivityViewModel.class.getSimpleName();

    private MutableLiveData<LoginStatus> status;

    private DataRepository dataRepository;

    public LoginActivityViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
        this.status = new MutableLiveData<>();
    }

    void login(String userName, String password, Boolean isGoogleAccount){
        status.postValue(LoginStatus.ON_PROGRESS);
        LoginData loginData = new LoginData(userName, password, isGoogleAccount);
        dataRepository.loginUser(loginData, new ResponseCallback<LoginStatus>() {
            @Override
            public void onLoaded(LoginStatus responseStatus) {
                Log.d(TAG, "onSuccess: " + responseStatus);
                status.postValue(responseStatus);

            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG, "onError: " );
                status.postValue(LoginStatus.FAILED);
            }
        });
    }

    public MutableLiveData<LoginStatus> getStatus() {
        return status;
    }
}
