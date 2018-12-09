package com.test.newshop1.data.remote;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {



        OAuthInterceptor.Builder oauthBuilder = new OAuthInterceptor.Builder();
        oauthBuilder.consumerKey(Cons.COSTUMER_KEY)
                .consumerSecret(Cons.COSTUMER_SECRET)
                .oauthToken(Cons.OAUTH_TOKEN)
                .tokenSecret(Cons.OAUTH_SECRET);
        OAuthInterceptor interceptor = oauthBuilder.build();


        OkHttpClient.Builder clientBuilder1 = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        clientBuilder1.addNetworkInterceptor(interceptor);
        clientBuilder1.readTimeout(20, TimeUnit.SECONDS);
        clientBuilder1.connectTimeout(30, TimeUnit.SECONDS);

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(interceptor);


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(clientBuilder1.build())
                    .build();
        }
        return retrofit;
    }

}