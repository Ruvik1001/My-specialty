package com.tasks.core;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import androidx.core.content.ContextCompat;

public class NetworkUtil {
    public static boolean isNetworkAvailable(Context applicationContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (connectivityManager != null) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            }
        } else {
            if (connectivityManager != null) {
                android.net.NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                return networkInfo != null && networkInfo.isConnected();
            }
        }
        return false;
    }

    public static boolean toastCheckConnection(Context applicationContext) {
        return FieldValidator.toastValidateAnyField(
                applicationContext,
                applicationContext,
                NetworkUtil::isNetworkAvailable,
                applicationContext.getString(R.string.networkError));
    }

    public static boolean alertCheckConnection(Context applicationContext) {
        return FieldValidator.alertValidateAnyField(
                applicationContext,
                applicationContext,
                NetworkUtil::isNetworkAvailable,
                applicationContext.getString(R.string.networkError),
                applicationContext.getString(R.string.Ok_ru),
                () -> {},
                null,
                () -> {}
        );
    }


    public static void checkInternet(Context applicationContext) {
        if (!isNetworkAvailable(applicationContext)) {
            new AlertDialog.Builder(applicationContext)
                    .setTitle(applicationContext.getString(R.string.networkErrorTitle))
                    .setMessage(applicationContext.getString(R.string.networkError))
                    .setPositiveButton(applicationContext.getString(R.string.reloadText), (dialog, which) -> {
                        // Actions when reload button clicked
                    })
                    .setOnDismissListener(dialog -> checkInternet(applicationContext))
                    .create().show();
        }
    }
}
