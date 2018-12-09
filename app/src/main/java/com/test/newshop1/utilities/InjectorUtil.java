package com.test.newshop1.utilities;

import android.content.Context;


import com.test.newshop1.AppExecutors;
import com.test.newshop1.data.DataRepository;
import com.test.newshop1.data.database.AppDatabase;
import com.test.newshop1.data.database.LocalDataSource;
import com.test.newshop1.data.remote.APIService;
import com.test.newshop1.data.remote.RemoteDataSource;
import com.test.newshop1.data.util.ApiUtils;
import com.test.newshop1.ui.ViewModelFactory;

public class InjectorUtil {

    private static DataRepository provideRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        APIService service = ApiUtils.getAPIService();
        RemoteDataSource networkDataSource =
                RemoteDataSource.getInstance(service);

        LocalDataSource localDataSource = LocalDataSource.getInstance(database, executors);
        return DataRepository.getInstance(localDataSource, networkDataSource);
    }


    public static ViewModelFactory provideViewModelFactory(Context context) {
        DataRepository repository = provideRepository(context.getApplicationContext());
        return new ViewModelFactory(repository);
    }

}