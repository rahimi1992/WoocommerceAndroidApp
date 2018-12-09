package com.test.newshop1.data.util;


import com.test.newshop1.data.remote.APIService;
import com.test.newshop1.data.remote.Cons;
import com.test.newshop1.data.remote.RetrofitClient;

public class ApiUtils {

    public static APIService getAPIService() {
        return RetrofitClient.getClient(Cons.BASE_URL).create(APIService.class);
    }
}
