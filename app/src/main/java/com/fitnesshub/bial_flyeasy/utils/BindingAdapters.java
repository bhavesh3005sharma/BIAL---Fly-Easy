package com.fitnesshub.bial_flyeasy.utils;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class BindingAdapters {
    @BindingAdapter(value = {"setStatus", "setTitle"}, requireAll = false)
    public static void setTitle(TextView textView, int status, String title) {
        if (title == null) {
            if (status == Constants.IN_PROGRESS)
                title = "Please wait we are processing your request...";
            else if (status == Constants.USER_NOT_FOUND)
                title = "Sorry, User not Found in our Database!";
            else if (status == Constants.WRONG_PASSWORD)
                title = "OOPS, You entered a Wrong Password!\n Try Again";
            else if (status == Constants.OKAY) title = "Hurray!!  We are ready to go now.";
        }

        textView.setText(title);
    }
}
