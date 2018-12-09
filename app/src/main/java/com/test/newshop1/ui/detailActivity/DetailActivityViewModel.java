package com.test.newshop1.ui.detailActivity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.test.newshop1.data.ResponseCallback;
import com.test.newshop1.data.DataRepository;
import com.test.newshop1.data.database.product.Product;
import com.test.newshop1.data.database.shoppingcart.CartItem;
import com.test.newshop1.utilities.ImageUtil;

import java.util.List;


public class DetailActivityViewModel extends ViewModel implements ResponseCallback<Product> {
    private static final String TAG = "DetailActivityViewModel";

    private DataRepository dataRepository;

    private MutableLiveData<Product> product;

    private final MutableLiveData<List<Integer>> relatedIds = new MutableLiveData<>();
    private final LiveData<List<Product>> relatedProducts =
            Transformations.switchMap(relatedIds, (ids) -> dataRepository.getRelatedProducts(ids));


    public DetailActivityViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
        product = new MutableLiveData<>();

    }

    public MutableLiveData<Product> getProduct(int productId) {
        dataRepository.getProduct(productId, this);
        return product;
    }

    @Override
    public void onLoaded(Product product) {
        relatedIds.setValue(product.getRelatedIds());
        Log.d(TAG, "getRelatedProducts: called and is ids null? " + (product.getRelatedIds() == null));
        this.product.postValue(product);
    }

    public LiveData<List<Product>> getRelatedProducts() {
        return relatedProducts;
    }

    @Override
    public void onDataNotAvailable() {

    }

    public void addToCart(){
        dataRepository.addToCart(createCartItem(product.getValue()));
    }


    private CartItem createCartItem(Product product) {
        String imageSrc = ImageUtil.getThumb(product.getImages().get(0).getSrc(),ImageUtil.SMALL_SIZE);
        return new CartItem( product.getName(), product.getId(), 0, 1, product.getPrice(), imageSrc, product.getCategories());
    }

    public LiveData<Integer> getCartItemCount() {
        return dataRepository.getCartItemCount();
    }
}
