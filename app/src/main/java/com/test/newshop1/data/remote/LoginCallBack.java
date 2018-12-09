package com.test.newshop1.data.remote;

import com.test.newshop1.data.database.customer.Customer;
import com.test.newshop1.data.database.customer.LoginResponse;

public interface LoginCallBack {

    interface GetLoginResponse {

        void onResponse(LoginResponse response);

        void onError(String message);
    }

    interface GetCustomer {

        void onSuccess(Customer customer);

        void onError(String message);

    }

}
