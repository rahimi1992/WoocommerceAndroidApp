
package com.test.newshop1.ui;

import androidx.annotation.StringRes;
import androidx.lifecycle.LifecycleOwner;

public class SnackbarMessageId extends SingleLiveEvent<Integer> {

    public void observe(LifecycleOwner owner, final SnackbarObserver observer) {
        super.observe(owner, t -> {
            if (t == null) {
                return;
            }
            observer.onNewMessage(t);
        });
    }

    public interface SnackbarObserver {

        void onNewMessage(@StringRes int snackbarMessageResourceId);

    }

}
