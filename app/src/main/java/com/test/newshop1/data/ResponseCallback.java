package com.test.newshop1.data;

public interface ResponseCallback<T> {

    void onLoaded(T response);

    void onDataNotAvailable();

}
