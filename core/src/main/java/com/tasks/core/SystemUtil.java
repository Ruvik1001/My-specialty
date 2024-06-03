package com.tasks.core;

import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

public class SystemUtil {

    public static void hideInputBoard(Context applicationContext, IBinder windowToken) {
        InputMethodManager inputMethodManager = (InputMethodManager) applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
        }
    }

}
