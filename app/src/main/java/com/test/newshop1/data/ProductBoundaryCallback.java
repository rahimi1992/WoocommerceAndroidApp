package com.test.newshop1.data;

import androidx.paging.PagedList;
import androidx.annotation.NonNull;
import android.util.Log;

import com.test.newshop1.data.database.LocalDataSource;
import com.test.newshop1.data.database.product.Product;
import com.test.newshop1.data.database.product.SimpleProduct;
import com.test.newshop1.data.remote.RemoteDataSource;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductBoundaryCallback extends PagedList.BoundaryCallback<SimpleProduct> {
    private static final String TAG = "ProductBoundaryCallback";

    private int parentId = -1;
    private String searchQuery = "";
    private final LocalDataSource localDataSource;
    private final RemoteDataSource remoteDataSource;
    private static boolean isRequestInProgress = false;
    private AtomicInteger lastRequestPage;

    ProductBoundaryCallback(int parentId, LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {

        this.parentId = parentId;
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
        lastRequestPage = new AtomicInteger(1);
    }

    ProductBoundaryCallback(String searchQuery, LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {

        this.searchQuery = searchQuery;
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
        lastRequestPage = new AtomicInteger(1);
    }


    public ProductBoundaryCallback(ProductListOptions options, LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {

        if (options.hasParentId()){
            parentId = options.getParentId();
        }

        if (options.hasSearchQuery()){
            searchQuery = options.getSearchQuery();
        }
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
        lastRequestPage = new AtomicInteger(1);
    }


    @Override
    public void onZeroItemsLoaded() {
        requestAndSaveData();
    }


    @Override
    public void onItemAtEndLoaded(@NonNull SimpleProduct itemAtEnd) {
        requestAndSaveData();
    }


    private void requestAndSaveData() {

        if (isRequestInProgress) {
            return;
        }
        isRequestInProgress = true;
        Callback<List<Product>> callback = new Callback<List<Product>>() {

            @Override
            public void onResponse(@NonNull Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: lastPage: " + lastRequestPage.get() + " parent: " + parentId + " response size: " + response.body().size());
                    lastRequestPage.incrementAndGet();

                    localDataSource.saveProductsAndJoins(response.body());
                } else
                    Log.d(TAG, "onResponse: NotSuccess - code: " + response.code());

                isRequestInProgress = false;
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, Throwable t) {
                Log.d(TAG, "onFailure: message: " + t.getMessage());
                isRequestInProgress = false;
            }
        };
        Log.d(TAG, "requestAndSaveData: new Request");
        remoteDataSource.getProducts(parentId, searchQuery, lastRequestPage.get(), 30, callback);

    }
}
