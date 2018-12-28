package com.test.newshop1.data.database;

import android.util.Log;

import com.test.newshop1.AppExecutors;
import com.test.newshop1.data.ProductListOptions;
import com.test.newshop1.data.ResponseCallback;
import com.test.newshop1.data.database.category.Category;
import com.test.newshop1.data.database.category.CategoryDao;
import com.test.newshop1.data.database.customer.Customer;
import com.test.newshop1.data.database.customer.CustomerDao;
import com.test.newshop1.data.database.order.Order;
import com.test.newshop1.data.database.order.OrderDao;
import com.test.newshop1.data.database.product.Product;
import com.test.newshop1.data.database.product.ProductCategoryJoin;
import com.test.newshop1.data.database.product.ProductCategoryJoinDao;
import com.test.newshop1.data.database.product.ProductDao;
import com.test.newshop1.data.database.product.SimpleCategory;
import com.test.newshop1.data.database.shoppingcart.CartDao;
import com.test.newshop1.data.database.shoppingcart.CartItem;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

public class LocalDataSource {
    private static final String TAG = LocalDataSource.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static LocalDataSource sInstance;
    private final AppExecutors executors;
    private final ProductDao productDao;
    private final ProductCategoryJoinDao productCategoryJoinDao;
    private final OrderDao orderDao;
    private final CartDao cartDao;
    private final CategoryDao categoryDao;
    private final CustomerDao customerDao;
    private LiveData<List<CartItem>> cartItemsLiveData;
    private LiveData<Integer> cartItemCount;

    private LocalDataSource(AppDatabase database, AppExecutors executors) {

        this.executors = executors;
        this.productDao = database.productDao();
        this.orderDao = database.orderDao();
        this.cartDao = database.cartDao();
        this.categoryDao = database.categoryDao();
        this.productCategoryJoinDao = database.productCategoryJoinDao();
        this.customerDao = database.customerDao();
        cartItemsLiveData = cartDao.getCartItems();
        cartItemCount = cartDao.getCartItemCount();
    }


    public static LocalDataSource getInstance(AppDatabase database, AppExecutors executors) {
        Log.d(TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new LocalDataSource( database, executors);
                Log.d(TAG, "Made new network data source");
            }
        }
        return sInstance;
    }


    public DataSource.Factory<Integer, Product> getProducts(ProductListOptions options){


        Log.d(TAG, "getProducts: called " + options.getParentId() + options.getSearchQuery());
        switch(options.getOrderBy()){
            case DATE:
                if (options.hasParentId()) {
                    return productDao.getProductsDateOrder(options.getParentId());
                } else {
                    Log.d(TAG, "getProducts: no parent called " + options.getSearchQuery());
                    return productDao.getProductsDateOrder("%" + options.getSearchQuery() + "%",
                            options.hasLimit()?options.getLimit():1000);
                }
            case RATING:
                if (options.hasParentId()) {
                    return productDao.getProductsRatingOrder(options.getParentId());
                } else {
                    return productDao.getProductsRatingOrder("%" + options.getSearchQuery() + "%",
                            options.hasLimit()?options.getLimit():1000);
                }
            case BEST_SELL:
                if (options.hasParentId()) {
                    return productDao.getProductsBestSellOrder(options.getParentId());
                } else {
                    return productDao.getProductsBestSellOrder("%" + options.getSearchQuery() + "%",
                            options.hasLimit()?options.getLimit():1000);
                }
            case PRICE_ASC:
                if (options.hasParentId()) {
                    return productDao.getProductsPriceOrderASC(options.getParentId());
                } else {
                    return productDao.getProductsPriceOrderASC("%" + options.getSearchQuery() + "%",
                            options.hasLimit()?options.getLimit():1000);
                }
            case PRICE_DESC:
                if (options.hasParentId()) {
                    return productDao.getProductsPriceOrderDESC(options.getParentId());
                } else {
                    return productDao.getProductsPriceOrderDESC("%" + options.getSearchQuery() + "%",
                            options.hasLimit()?options.getLimit():1000);
                }

        }
        return null;

    }

    public LiveData<List<Product>> getNewProducts(int limit){
        return productDao.getNewProducts(limit);
    }

    public LiveData<List<Product>> getFeaturedProducts(int limit){
        return productDao.getFeaturedProduct(limit);
    }

    public LiveData<List<Product>> getOnSaleProducts(int limit){
        return productDao.getOnSaleProduct(limit);
    }

//    public DataSource.Factory<Integer, Product> getNewProducts(){
//        return productDao.getNewProducts();
//    }
//
//    public DataSource.Factory<Integer, Product> getBestSellProducts(){
//        return productDao.getFeaturedProduct();
//    }
//
//    public DataSource.Factory<Integer, Product> getPopProducts(){
//        return productDao.getOnSaleProduct();
//    }

//    public DataSource.Factory<Integer, Product> searchProducts(String query){
//        return productDao.searchProducts("%" + query + "%");
//    }


    public LiveData<List<Product>> getRelatedProducts(List<Integer> ids){
        return productDao.getRelatedProducts(ids);
    }

    public void saveProductsAndJoins(final List<Product> products) {
        executors.diskIO().execute(() -> {
            productDao.bulkInsert(products);
            for (Product product : products) {
                productCategoryJoinDao.insertCategories(product.getCategories());
                productCategoryJoinDao.insertJoins(createJoinList(product));
            }
        });
    }

    private List<ProductCategoryJoin> createJoinList(Product product) {
        List<ProductCategoryJoin> joins = new ArrayList<>();
        int productId = product.getId();
        for (SimpleCategory category : product.getCategories()){
            joins.add(new ProductCategoryJoin(productId, category.getId()));
        }
        return joins;
    }

    public void getProduct(final int productId, final ResponseCallback<Product> callBack) {
        Runnable runnable = () -> {
            Product product = productDao.getProduct(productId);
            //List<Product> products = (product == null) ? null : Collections.singletonList(product);
            executors.mainThread().execute(createCallBackRunnable(product, callBack));
        };

        executors.diskIO().execute(runnable);
    }

    private Runnable createCallBackRunnable(final Product product, final ResponseCallback<Product> callBack){
        return () -> {
            if (product ==null ) {
                callBack.onDataNotAvailable();
            } else {
                callBack.onLoaded(product);
            }
        };
    }

    public void addToCart(final CartItem item){
        executors.diskIO().execute(() -> {
            CartItem cartItem = cartDao.getCartItemByPId(item.getProductId(), item.getVariationId());
            if (cartItem != null){
                cartDao.increaseItem(cartItem.getId());
            }else{
                cartDao.insetItem(item);
            }

        });
    }

    public void removeFromCart(final int itemId){
        executors.diskIO().execute(() -> cartDao.removeItem(itemId));
    }

    public LiveData<List<CartItem>> getCartItem(){
        return cartItemsLiveData;
    }

    public LiveData<Integer> getTotalPrice(){
        return cartDao.getTotalPrice();
    }

    public void addItem(final int itemId){
        executors.diskIO().execute(() -> cartDao.increaseItem(itemId));
    }

    public void decreaseItem(final int itemId) {
        executors.diskIO().execute(() -> cartDao.decreaseItem(itemId));
    }

    public LiveData<Integer> getCartItemCount() {
        return cartItemCount;
    }

    public void getCategories(final ResponseCallback<List<Category>> callBack){
        Log.d(TAG, "getCategories: loading categories");
        Runnable runnable = () -> {
            final List<Category> categories = categoryDao.getCategories();
            executors.mainThread().execute(() -> {

                if (!categories.isEmpty())
                    callBack.onLoaded(categories);
                else
                    callBack.onDataNotAvailable();
            });
        };

        executors.diskIO().execute(runnable);
    }

    public void saveCategories(final List<Category> categories){
        executors.diskIO().execute(() -> categoryDao.bulkInsert(categories));
    }

    public void saveCustomer(Customer customer) {
        executors.diskIO().execute(() -> customerDao.insert(customer));
    }

    public LiveData<Customer> getLoggedInCustomer(){
        return customerDao.getLastCustomer(true);
    }

    public void logoutCustomer(int id) {
        executors.diskIO().execute(() -> customerDao.logout(id));
    }

    public void saveOrders(List<Order> orders) {
        executors.diskIO().execute(() -> {

            orderDao.Insert(orders);
        });
    }

    public LiveData<List<Order>> getOrders(int customerId){
        return orderDao.getOrders(customerId);
    }



}
