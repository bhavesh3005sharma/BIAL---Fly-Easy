package com.fitnesshub.bial_flyeasy.utils;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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
}
