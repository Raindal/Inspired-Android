package com.neilrosenstech.inspired.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InspiredActivityHelper {
    public static void setTitle(TextView title, Typeface font, String text) {
        title.setTypeface(font);
        title.setText(text);
    }

    public static void setDate(TextView dateView, Typeface font) {
        dateView.setTypeface(font);
        dateView.setText(new SimpleDateFormat("EEEE, MMMM d yyyy").format(new Date()));
    }

    public static void showDialog(ProgressDialog dialog, String message) {
        dialog.setMessage(message);
        dialog.show();
    }

    public static void dismissDialog(ProgressDialog dialog) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
