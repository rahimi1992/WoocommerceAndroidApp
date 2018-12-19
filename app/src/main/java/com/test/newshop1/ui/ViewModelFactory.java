package com.test.newshop1.ui;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.test.newshop1.data.DataRepository;
import com.test.newshop1.ui.categoryActivity.CategoryViewModel;
import com.test.newshop1.ui.checkoutActivity.CheckoutViewModel;
import com.test.newshop1.ui.detailActivity.DetailActivityViewModel;
import com.test.newshop1.ui.loginActivity.LoginActivityViewModel;
import com.test.newshop1.ui.ordersActivity.OrdersViewModel;
import com.test.newshop1.ui.productListActivity.ProductListActivityViewModel;
import com.test.newshop1.ui.profileActivity.ProfileViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final DataRepository mDataRepository;

    public ViewModelFactory(DataRepository repository) {
        this.mDataRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {


        if (modelClass.isAssignableFrom(CategoryViewModel.class)) {
            //noinspection unchecked
            return (T) new CategoryViewModel( mDataRepository);
        } else if (modelClass.isAssignableFrom(DetailActivityViewModel.class)) {
            //noinspection unchecked
            return (T) new DetailActivityViewModel( mDataRepository);
        } else if (modelClass.isAssignableFrom(LoginActivityViewModel.class)) {
            //noinspection unchecked
            return (T) new LoginActivityViewModel( mDataRepository);
        } else if (modelClass.isAssignableFrom(ProductListActivityViewModel.class)) {
            //noinspection unchecked
            return (T) new ProductListActivityViewModel( mDataRepository);
        }  else if (modelClass.isAssignableFrom(CheckoutViewModel.class)) {
            //noinspection unchecked
            return (T) new CheckoutViewModel( mDataRepository);
        }  else if (modelClass.isAssignableFrom(BaseActivityViewModel.class)) {
            //noinspection unchecked
            return (T) new BaseActivityViewModel( mDataRepository);
        }  else if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            //noinspection unchecked
            return (T) new ProfileViewModel( mDataRepository);
        } else if (modelClass.isAssignableFrom(OrdersViewModel.class)) {
            //noinspection unchecked
            return (T) new OrdersViewModel( mDataRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}