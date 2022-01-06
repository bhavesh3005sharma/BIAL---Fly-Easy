package com.fitnesshub.bial_flyeasy.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;

public class HelperClass {
    public static final String MY_PREFS_NAME = "codesleep-merntest-prefs";
    public static String BASE_URL = "https://codesleep-merntest.azurewebsites.net";

    public static void toast(Context context, String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    public static void showProgressbar(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
    }

    public static void hideProgressbar(ProgressBar progressBar) {
        progressBar.setVisibility(View.GONE);
    }

    public static boolean isNumeric(String str) {
        if (str == null || str.length() == 0) return false;

        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void showSnackBar(String msg, Activity activity, int backgroundColor) {
        TSnackbar snackbar = TSnackbar.make(activity.findViewById(android.R.id.content), msg, TSnackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(backgroundColor);
        TextView textView = snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
}
