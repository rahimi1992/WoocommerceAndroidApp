package com.test.newshop1.data.remote;

import android.support.annotation.NonNull;
import android.util.Log;


import org.scribe.model.ParameterList;
import org.scribe.services.HMACSha1SignatureService;
import org.scribe.services.TimestampServiceImpl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import okhttp3.*;


public class OAuthInterceptor implements Interceptor {
    private static final String TAG = "CallRequest";

    private static final String OAUTH_CONSUMER_KEY = "oauth_consumer_key";
    private static final String OAUTH_TOKEN = "oauth_token";
    private static final String OAUTH_NONCE = "oauth_nonce";
    private static final String OAUTH_SIGNATURE = "oauth_signature";
    private static final String OAUTH_SIGNATURE_METHOD = "oauth_signature_method";
    private static final String OAUTH_SIGNATURE_METHOD_VALUE = "HMAC-SHA1";
    private static final String OAUTH_TIMESTAMP = "oauth_timestamp";
    private static final String OAUTH_VERSION = "oauth_version";
    private static final String OAUTH_VERSION_VALUE = "1.0";

    private final String consumerKey;
    private final String consumerSecret;
    private final String oauthToken;
    private final String tokenSecret;


    private OAuthInterceptor(String consumerKey, String consumerSecret, String oauthToken, String tokenSecret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.oauthToken = oauthToken;
        this.tokenSecret = tokenSecret;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        CacheControl.Builder cacheBuilder = new CacheControl.Builder().maxAge(60, TimeUnit.MINUTES);
        cacheBuilder.onlyIfCached();
        Request original = chain.request();



        HttpUrl originalHttpUrl = original.url();

        final String nonce = new TimestampServiceImpl().getNonce();
        final String timestamp = new TimestampServiceImpl().getTimestampInSeconds();

        String dynamicStructureUrl = original.url().scheme() + "://" + original.url().host() + original.url().encodedPath();

        String firstBaseString = original.method() + "&" + urlEncoded(dynamicStructureUrl);

        String generatedBaseString;


        if(original.url().encodedQuery()!=null) {
            generatedBaseString = original.url().encodedQuery() + "&oauth_consumer_key=" + consumerKey + "&oauth_token=" + oauthToken + "&oauth_nonce=" + nonce + "&oauth_signature_method=HMAC-SHA1&oauth_timestamp=" + timestamp + "&oauth_version=1.0";
        }
        else
        {
            generatedBaseString = "oauth_consumer_key=" + consumerKey + "&oauth_token=" + oauthToken + "&oauth_nonce=" + nonce + "&oauth_signature_method=HMAC-SHA1&oauth_timestamp=" + timestamp + "&oauth_version=1.0";

        }

        ParameterList result = new ParameterList();
        result.addQuerystring(generatedBaseString);
        generatedBaseString=result.sort().asOauthBaseString();

        String secondBaseString = "&" + generatedBaseString;

        if (firstBaseString.contains("%3F")) {
            secondBaseString = "%26" + urlEncoded(generatedBaseString);
        }

        String baseString = firstBaseString + secondBaseString;

        Log.d(TAG, "intercept: base string: " + baseString);
        Log.d(TAG, "intercept: cons secret: " + consumerSecret);
        Log.d(TAG, "intercept: cons key: " + consumerKey);

        String signature = new HMACSha1SignatureService().getSignature(baseString, consumerSecret, tokenSecret);

        HttpUrl url = originalHttpUrl.newBuilder()

                .addQueryParameter(OAUTH_SIGNATURE_METHOD, OAUTH_SIGNATURE_METHOD_VALUE)
                .addQueryParameter(OAUTH_CONSUMER_KEY, consumerKey)
                .addQueryParameter(OAUTH_TOKEN, oauthToken)
                .addQueryParameter(OAUTH_VERSION, OAUTH_VERSION_VALUE)
                .addQueryParameter(OAUTH_TIMESTAMP, timestamp)
                .addQueryParameter(OAUTH_NONCE, nonce)
                .addQueryParameter(OAUTH_SIGNATURE, signature)


                .build();

        // Request customization: add request headers
        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();

        return chain.proceed(request);
    }


    static final class Builder {

        private String consumerKey;
        private String consumerSecret;
        private String oauthToken;
        private String tokenSecret;

        Builder consumerKey(String consumerKey) {
            if (consumerKey == null) throw new NullPointerException("consumerKey = null");
            this.consumerKey = consumerKey;
            return this;
        }

        Builder consumerSecret(String consumerSecret) {
            if (consumerSecret == null) throw new NullPointerException("consumerSecret = null");
            this.consumerSecret = consumerSecret;
            return this;
        }

        Builder oauthToken(String oauthToken) {
            if (oauthToken == null) throw new NullPointerException("consumerSecret = null");
            this.oauthToken = oauthToken;
            return this;
        }

        Builder tokenSecret(String tokenSecret) {
            if (tokenSecret == null) throw new NullPointerException("consumerSecret = null");
            this.tokenSecret = tokenSecret;
            return this;
        }


        OAuthInterceptor build() {

            if (consumerKey == null) throw new IllegalStateException("consumerKey not set");
            if (consumerSecret == null) throw new IllegalStateException("consumerSecret not set");
            if (oauthToken == null) throw new IllegalStateException("oauthToken not set");
            if (tokenSecret == null) throw new IllegalStateException("tokenSecret not set");

            return new OAuthInterceptor(consumerKey, consumerSecret,oauthToken, tokenSecret);
        }
    }

    private String urlEncoded(String url) {
        String encodedUrl = "";
        try {
            encodedUrl = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodedUrl;
    }
}
