package com.test.newshop1.data.util;


import com.test.newshop1.data.remote.APIService;
import com.test.newshop1.data.remote.RetrofitClient;

public class ApiUtils {
    private static final String BASE_URL = "https://femelo.com/wp-json/wc/v2/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
