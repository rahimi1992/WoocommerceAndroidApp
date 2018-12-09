package com.test.newshop1.data.remote;

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

    /*THIS CLASS CONTAIN ERROR ITS BECAUSE THIS APP DOES NOT IMPORTED THE RETROFIT LIBRARY*/


   /*IMPORT below dependency to gradel to fix error
   *
   *  compile 'com.squareup.retrofit2:retrofit:2.1.0'
   compile 'com.squareup.retrofit2:converter-gson:2.1.0'
   compile 'com.squareup.okhttp3:okhttp:3.3.1'
   compile 'com.squareup.okhttp3:logging-interceptor:3.3.1'
   * */

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
    public Response intercept(Chain chain) throws IOException {
        CacheControl.Builder cacheBuilder = new CacheControl.Builder().maxAge(60, TimeUnit.MINUTES);
        cacheBuilder.onlyIfCached();
        //int maxAge = 60*60;
        Request original = chain.request();
// .newBuilder()
//                .removeHeader("Pragma")
//                //.cacheControl(cacheBuilder.build())
//                .header("Cache-Control", "public, only-if-cached, max-age=" + maxAge)
//                .build();
//


        HttpUrl originalHttpUrl = original.url();
//
        Log.d(TAG, " CALLED!!!!!!!!! ");
//        Log.d("URL", original.url().toString());
//        Log.d("URL", original.url().scheme());
//        Log.d("encodedpath", original.url().encodedPath());
//        Log.d("query", ""+original.url().query());
//        Log.d("path", ""+original.url().host());
//        Log.d("encodedQuery", ""+original.url().encodedQuery());
//        ;
//        Log.d("method", ""+original.method());

        ////////////////////////////////////////////////////////////

        final String nonce = new TimestampServiceImpl().getNonce();
        final String timestamp = new TimestampServiceImpl().getTimestampInSeconds();
//        Log.d("nonce", nonce);
//        Log.d("time", timestamp);
//        Log.d(TAG, "intercept: " + original.url().toString());

        String dynamicStructureUrl = original.url().scheme() + "://" + original.url().host() + original.url().encodedPath();

        //Log.d(TAG, "intercept: " + dynamicStructureUrl);

        //Log.d("ENCODED PATH", ""+dynamicStructureUrl);
        String firstBaseString = original.method() + "&" + urlEncoded(dynamicStructureUrl);
        //Log.d("firstBaseString", firstBaseString);
        String generatedBaseString = "";


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
        //Log.d("Sorted","00--"+result.sort().asOauthBaseString());

        String secoundBaseString = "&" + generatedBaseString;

        if (firstBaseString.contains("%3F")) {
            //Log.d("iff","yess iff");
            secoundBaseString = "%26" + urlEncoded(generatedBaseString);
        }

        String baseString = firstBaseString + secoundBaseString;

        Log.d(TAG, "intercept: base string: " + baseString);
        Log.d(TAG, "intercept: cons secret: " + consumerSecret);
        Log.d(TAG, "intercept: cons key: " + consumerKey);

        String signature = new HMACSha1SignatureService().getSignature(baseString, consumerSecret, tokenSecret);
        //Log.d("Signature", signature);

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


    public static final class Builder {

        private String consumerKey;
        private String consumerSecret;
        private String oauthToken;
        private String tokenSecret;

        public Builder consumerKey(String consumerKey) {
            if (consumerKey == null) throw new NullPointerException("consumerKey = null");
            this.consumerKey = consumerKey;
            return this;
        }

        public Builder consumerSecret(String consumerSecret) {
            if (consumerSecret == null) throw new NullPointerException("consumerSecret = null");
            this.consumerSecret = consumerSecret;
            return this;
        }

        public Builder oauthToken(String oauthToken) {
            if (oauthToken == null) throw new NullPointerException("consumerSecret = null");
            this.oauthToken = oauthToken;
            return this;
        }

        public Builder tokenSecret(String tokenSecret) {
            if (tokenSecret == null) throw new NullPointerException("consumerSecret = null");
            this.tokenSecret = tokenSecret;
            return this;
        }


        public OAuthInterceptor build() {

            if (consumerKey == null) throw new IllegalStateException("consumerKey not set");
            if (consumerSecret == null) throw new IllegalStateException("consumerSecret not set");
            if (oauthToken == null) throw new IllegalStateException("oauthToken not set");
            if (tokenSecret == null) throw new IllegalStateException("tokenSecret not set");

            return new OAuthInterceptor(consumerKey, consumerSecret,oauthToken, tokenSecret);
        }
    }

    public String urlEncoded(String url) {
        String encodedurl = "";
        try {

            encodedurl = URLEncoder.encode(url, "UTF-8");
            //Log.d("TEST", encodedurl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodedurl;
    }
}
