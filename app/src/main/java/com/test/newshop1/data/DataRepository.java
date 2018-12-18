package com.test.newshop1.data;


import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;

import com.test.newshop1.data.database.LocalDataSource;
import com.test.newshop1.data.database.category.Category;
import com.test.newshop1.data.database.coupon.Coupon;
import com.test.newshop1.data.database.customer.Customer;
import com.test.newshop1.data.database.customer.LoginData;
import com.test.newshop1.data.database.customer.LoginResponse;
import com.test.newshop1.data.database.order.Order;
import com.test.newshop1.data.database.payment.PaymentGateway;
import com.test.newshop1.data.database.product.Product;
import com.test.newshop1.data.database.shipping.ShippingMethod;
import com.test.newshop1.data.database.shoppingcart.CartItem;
import com.test.newshop1.data.remote.RemoteDataSource;
import com.test.newshop1.ui.loginActivity.LoginStatus;

import java.util.Collections;
import java.util.List;


public class DataRepository {
    private static final String TAG = "DataRepository";

    private static DataRepository sInstance;
    private static final Object LOCK = new Object();

    private final RemoteDataSource mRemoteDataSource;
    private final LocalDataSource mLocalDataSource;

    private List<ShippingMethod> cachedShippingMethods;
    private List<PaymentGateway> cachedPayments;

    private DataRepository(LocalDataSource localDataSource,
                           RemoteDataSource remoteDataSource) {

        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;

    }

    public synchronized static DataRepository getInstance(LocalDataSource localDataSource,
                                                          RemoteDataSource remoteDataSource) {

        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new DataRepository(localDataSource, remoteDataSource);
            }
        }
        return sInstance;
    }


    public LiveData<PagedList<Product>> getProducts(int parentId){


        DataSource.Factory<Integer, Product> dataSourceFactory = mLocalDataSource.getProducts(parentId);

        PagedList.Config myPagingConfig = new PagedList.Config.Builder()
                .setPageSize(20)
                .setPrefetchDistance(30)
                .build();

        ProductBoundaryCallback boundaryCallback = new ProductBoundaryCallback(parentId, mLocalDataSource, mRemoteDataSource);

        return new LivePagedListBuilder<>(dataSourceFactory, myPagingConfig)
                .setBoundaryCallback(boundaryCallback)
                .build();

    }

    public LiveData<List<Product>> getRelatedProducts(List<Integer> ids){
        Log.d(TAG, "getRelatedProducts: called and is ids null? " + (ids == null));
        if (ids != null) {
            getRelatedProductsFromRemote(ids);
            return mLocalDataSource.getRelatedProducts(ids);
        } else
            return null;

    }

    private void getRelatedProductsFromRemote(List<Integer> ids) {

        mRemoteDataSource.getRelatedProducts(ids, new ResponseCallback<List<Product>>(){

            @Override
            public void onLoaded(List<Product> products) {
                mLocalDataSource.saveProductsAndJoins(products);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

    }


    public void getProduct(final int productId, ResponseCallback<Product> callBack) {
        mLocalDataSource.getProduct(productId, new ResponseCallback<Product>(){

            @Override
            public void onLoaded(Product product) {
                callBack.onLoaded(product);
            }

            @Override
            public void onDataNotAvailable() {
                //loadProductFromRemote(productId, callBack);
            }
        });
    }





    public LiveData<List<CartItem>> getCartItems(){
        return mLocalDataSource.getCartItem();
    }

    public LiveData<Integer> getTotalPrice(){
        return mLocalDataSource.getTotalPrice();
    }

    public void addItem(int itemId){
        mLocalDataSource.addItem(itemId);
    }

    public void addToCart(CartItem item){
        mLocalDataSource.addToCart(item);
    }

    public void removeCartItem(int itemId){
        mLocalDataSource.removeFromCart(itemId);
    }

    public void decreaseItem(int id) {
        mLocalDataSource.decreaseItem(id);
    }

    public LiveData<Integer> getCartItemCount() {
        return mLocalDataSource.getCartItemCount();
    }


    public void getCategories(final ResponseCallback<List<Category>> callBack){
        Log.d(TAG, "getCategories: loading categories");
        mLocalDataSource.getCategories(new ResponseCallback<List<Category>>() {
            @Override
            public void onLoaded(List<Category> categories) {
                Log.d(TAG, "onCategoriesLoaded: loading categories");
                callBack.onLoaded(categories);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG, "onDataNotAvailable: loading categories");
                loadCategoriesFromRemote(callBack);
            }
        });
    }

    private void loadCategoriesFromRemote(ResponseCallback<List<Category>> callBack) {
        mRemoteDataSource.getCategories(new ResponseCallback<List<Category>>() {
            @Override
            public void onLoaded(List<Category> response) {
                callBack.onLoaded(response);
                mLocalDataSource.saveCategories(response);
            }

            @Override
            public void onDataNotAvailable() {
                callBack.onDataNotAvailable();
            }
        });
    }

    public void loginUser(LoginData loginData, ResponseCallback<LoginStatus> callBack){
        mRemoteDataSource.getUserId(loginData, new ResponseCallback<LoginResponse>() {
            @Override
            public void onLoaded(LoginResponse response) {
                if (response.getStatus().equals("SUCCESSFUL")) {
                    String userId = response.getUserId();
                    callBack.onLoaded(LoginStatus.SUCCESSFUL);
                    mRemoteDataSource.getCustomer(userId, createCustomerCallback());
                } else {
                    Log.d(TAG, "onLoaded: " + response.getStatus());
                    callBack.onLoaded(LoginStatus.WRONG_PASS);
                }

            }

            @Override
            public void onDataNotAvailable() {
                callBack.onDataNotAvailable();
            }

        });
    }

    private ResponseCallback<Customer> createCustomerCallback(){
        return new ResponseCallback<Customer>() {
            @Override
            public void onLoaded(Customer customer) {
                customer.setLoggedIn(true);
                mLocalDataSource.saveCustomer(customer);
            }

            @Override
            public void onDataNotAvailable() {

            }
        };
    }

    public LiveData<Customer> getLoggedInCustomer(){
        return mLocalDataSource.getLoggedInCustomer();
    }

    public void logoutCustomer(int id) {
        mLocalDataSource.logoutCustomer(id);
    }

    public void getShippingMethods(ResponseCallback<List<ShippingMethod>> callback){
        if (cachedShippingMethods != null){
            callback.onLoaded(cachedShippingMethods);
            return;
        }

        mRemoteDataSource.getShippingMethods(new ResponseCallback<List<ShippingMethod>>() {
            @Override
            public void onLoaded(List<ShippingMethod> response) {
                cachedShippingMethods = response;
                callback.onLoaded(cachedShippingMethods);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    public void getPayments(ResponseCallback<List<PaymentGateway>> callback){
        if (cachedPayments != null){
            callback.onLoaded(cachedPayments);
            return;
        }

        mRemoteDataSource.getPayments(new ResponseCallback<List<PaymentGateway>>() {
            @Override
            public void onLoaded(List<PaymentGateway> response) {
                cachedPayments = response;
                callback.onLoaded(cachedPayments);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    public void getCoupon(String coupon, ResponseCallback<List<Coupon>> callback) {
        mRemoteDataSource.getCoupon(coupon, callback);
    }

    public void saveOrder(Order order, ResponseCallback<Order> callback) {
        mRemoteDataSource.saveOrder(order, new ResponseCallback<Order>() {
            @Override
            public void onLoaded(Order response) {
                Log.d(TAG, "onLoaded: order posted successfully");
                saveOrderOnDB(response);
                callback.onLoaded(response);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG, "onDataNotAvailable: send order error");
                callback.onDataNotAvailable();
            }
        });
    }

    private void saveOrderOnDB(Order order) {
        mLocalDataSource.saveOrders(Collections.singletonList(order));
    }

    public void updateOrder(String orderId, Order order) {
        mRemoteDataSource.updateOrder(orderId, order);
    }

    public LiveData<List<Order>> getOrders(int customerId){
        updateOrdersData(customerId);
        return mLocalDataSource.getOrders(customerId);
    }

    private void updateOrdersData(int customerId) {
        mRemoteDataSource.getOrders(customerId, new ResponseCallback<List<Order>>() {
            @Override
            public void onLoaded(List<Order> response) {
                mLocalDataSource.saveOrders(response);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
