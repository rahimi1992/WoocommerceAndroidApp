package com.test.newshop1.data.remote;

import android.util.Log;

import com.test.newshop1.data.ResponseCallback;
import com.test.newshop1.data.database.category.Category;
import com.test.newshop1.data.database.coupon.Coupon;
import com.test.newshop1.data.database.customer.Customer;
import com.test.newshop1.data.database.customer.LoginData;
import com.test.newshop1.data.database.customer.LoginResponse;
import com.test.newshop1.data.database.order.Order;
import com.test.newshop1.data.database.payment.PaymentGateway;
import com.test.newshop1.data.database.product.Product;
import com.test.newshop1.data.database.shipping.ShippingMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataSource {
    private static final String TAG = RemoteDataSource.class.getSimpleName();

    private static final int SYNC_INTERVAL_HOURS = 3;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;
    private static final String REMOTE_SYNC_TAG = "remote-sync";
    private static final int MAX_RETRY_REQUEST = 5;

    private static final Object LOCK = new Object();
    private static RemoteDataSource sInstance;

    private final APIService mService;

    private RemoteDataSource( APIService service) {
        mService = service;
    }

    public static RemoteDataSource getInstance( APIService service) {
        Log.d(TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new RemoteDataSource(service);
                Log.d(TAG, "Made new network data source");
            }
        }
        return sInstance;
    }


    public void getProducts(int parentId, String searchQuery, int page, int perPage, Callback<List<Product>> callback){
        Map<String, String> query = new HashMap<>();
        if (parentId != -1) {
            query.put("category", String.valueOf(parentId));
        }
        query.put("page", String.valueOf(page));
        query.put("per_page", String.valueOf(perPage));
        query.put("search", searchQuery);
//        if (filterType != null){
//            switch (filterType){
//                case ON_SALES:
//                    query.put("on_sale", "true");
//                    break;
//                case FEATURED:
//                    query.put("featured", "true");
//                    break;
//                case NEWEST:
//                    query.put("orderby", "date");
//
//            }
//        }


        mService.getProducts(query).enqueue(callback);
    }



    private void getProduct(int productId, ResponseCallback<Product> callBack) {
        mService.getProducts(productId).enqueue(new GenericCallback<Product>().create(callBack));
    }

    public void getCategories(ResponseCallback<List<Category>> callBack){
        //Log.d(TAG, "getCategories: loading categories");
        mService.getCategories().enqueue(new GenericCallback<List<Category>>().create(callBack));

    }

    public void getUserId(final LoginData loginData, ResponseCallback<LoginResponse> callBack){
        String login_url = "https://femelo.com/app/login.php";
        mService.login(login_url, loginData).enqueue(new GenericCallback<LoginResponse>().create(callBack));
    }

    public void getCustomer(String customerId, ResponseCallback<Customer> callBack){
        mService.getCustomer(customerId).enqueue(new GenericCallback<Customer>().create(callBack));
    }

    public void getRelatedProducts(List<Integer> ids, ResponseCallback<List<Product>> callBack) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("include", String.valueOf(ids));
        mService.getProducts(queryMap).enqueue(new GenericCallback<List<Product>>().create(callBack));
    }

    public void getShippingMethods(ResponseCallback<List<ShippingMethod>> callback){
        mService.getShippingMethods().enqueue(new GenericCallback<List<ShippingMethod>>().create(callback));
    }

    public void getPayments(ResponseCallback<List<PaymentGateway>> callback){
        mService.getPayments().enqueue(new GenericCallback<List<PaymentGateway>>().create(callback));
    }

    public void getCoupon(String coupon, ResponseCallback<List<Coupon>> callback) {
        mService.getCoupon(coupon).enqueue(new GenericCallback<List<Coupon>>().create(callback));
    }

    public void saveOrder(Order order, ResponseCallback<Order> callback) {
        mService.postOrder(order).enqueue(new GenericCallback<Order>().create(callback));
    }

    public void getOrders(int customerId, ResponseCallback<List<Order>> callback){
        mService.getOrders(customerId).enqueue(new GenericCallback<List<Order>>().create(callback));
    }

    public void updateOrder(String orderId, Order order) {
        mService.updateOrder(orderId, order).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: retrying set payment");
                    call.clone().enqueue(this);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Order> call, @NonNull Throwable t) {
                Log.d(TAG, "onResponse: retrying set payment");
                call.clone().enqueue(this);
            }
        });
    }

    public void getPayment(String id, ResponseCallback<PaymentGateway> callback) {
        mService.getPayment(id).enqueue(new GenericCallback<PaymentGateway>().create(callback));
    }

    private class GenericCallback<T>{

        Callback<T> create(ResponseCallback<T> callBack){
            AtomicInteger callCount = new AtomicInteger(0);
            return new Callback<T>(){
                @Override
                public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: successful");
                        callBack.onLoaded(response.body());
                    } else {
                        Log.d(TAG, "onResponse: not successful - response code: " + response.code() + response.errorBody());
                        callBack.onDataNotAvailable();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                    if (callCount.incrementAndGet() < MAX_RETRY_REQUEST) {
                        call.clone().enqueue(this);
                    } else {
                        Log.d(TAG, "onFailure: failed");
                        callBack.onDataNotAvailable();
                    }
                }
            };

        }
    }
}
