
package com.test.newshop1.ui;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.StringRes;

public class SnackbarMessageText extends SingleLiveEvent<String> {

    public void observe(LifecycleOwner owner, final SnackbarObserver observer) {
        super.observe(owner, t -> {
            if (t == null) {
                return;
            }
            observer.onNewMessage(t);
        });
    }

    public interface SnackbarObserver {

        void onNewMessage(String snackbarMessageText);

    }

}
