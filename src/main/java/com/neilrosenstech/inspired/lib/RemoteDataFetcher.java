package com.neilrosenstech.inspired.lib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;

import com.neilrosenstech.inspired.InspiredActivity;
import com.neilrosenstech.inspired.helpers.InspiredActivityHelper;
import com.neilrosenstech.inspired.lib.exceptions.InspiredServerException;
import com.neilrosenstech.inspired.models.DailyPackage;

public class RemoteDataFetcher extends AsyncTask<String, String, DailyPackage> {
    private InspiredServerException exception;
    private InspiredActivity activity;

    public RemoteDataFetcher(InspiredActivity inspiredActivity) {
        activity = inspiredActivity;
    }

    @Override
    protected void onPreExecute() {
        checkNetworkAvailabilityAndNotify();
    }

    @Override
    protected DailyPackage doInBackground(String... urls) {
        try {
            return DailyPackage.createFromRemoteData(urls[0]);
        } catch (InspiredServerException e) {
            exception = e;
            return null;
        }
    }

    protected void onPostExecute(DailyPackage pack) {
        if (exception != null) {
            handleException(exception);
            return;
        }

        activity.currentPack = pack;
        activity.backgroundPreview.setImageBitmap(pack.image);

        InspiredActivityHelper.dismissDialog(activity.dialog);
        InspiredActivityHelper.setTitle(activity.titleView, activity.lato, activity.currentPack.title.toUpperCase());
        InspiredActivityHelper.setDate(activity.dateView, activity.lato);
    }

    private void checkNetworkAvailabilityAndNotify() {
        if (networkIsAvailable()) {
            InspiredActivityHelper.showDialog(activity.dialog, "Please wait");
        } else {
            InspiredActivityHelper.showToast(activity.getApplicationContext(), "Please make sure you have an active internet connection");
            cancel(true);
        }
    }

    private boolean networkIsAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    private void handleException(InspiredServerException exception) {
        InspiredActivityHelper.dismissDialog(activity.dialog);
        InspiredActivityHelper.showToast(activity.getApplicationContext(), exception.getMessage());
    }
}
