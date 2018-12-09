package com.test.newshop1.data.remote;



import com.test.newshop1.data.database.category.Category;
import com.test.newshop1.data.database.coupon.Coupon;
import com.test.newshop1.data.database.customer.Customer;
import com.test.newshop1.data.database.customer.LoginData;
import com.test.newshop1.data.database.customer.LoginResponse;
import com.test.newshop1.data.database.payment.PaymentGateway;
import com.test.newshop1.data.database.product.Product;
import com.test.newshop1.data.database.shipping.ShippingMethod;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface APIService {

    @GET("products?per_page=20&status=publish")
    Call<List<Product>> getProducts(); //

    @GET("products?status=publish")
    Call<List<Product>> getProducts(@QueryMap Map<String, String> params);

    @GET("products/{productId}")
    Call<Product> getProducts(@Path("productId") int productId);

    @GET("products?order=desc&per_page=8&status=publish")
    Call<List<Product>> getProducts(@Query("featured") boolean featured);

    @GET("products/categories?per_page=100&orderby=count&order=desc")
    Call<List<Category>> getCategories();

    @POST
    Call<LoginResponse> login(@Url String url, @Body LoginData data);

    @GET("customers/{userId}")
    Call<Customer> getCustomer(@Path("userId") String userId);

    @GET("payment_gateways")
    Call<List<PaymentGateway>> getPayments();

    @GET("shipping/zones/2/methods")
    Call<List<ShippingMethod>> getShippingMethods();


    @GET("coupons")
    Call<List<Coupon>> getCoupon(@Query("code") String code);
}
