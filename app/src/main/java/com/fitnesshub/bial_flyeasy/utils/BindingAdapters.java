package com.fitnesshub.bial_flyeasy.utils;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.fitnesshub.bial_flyeasy.R;
import com.fitnesshub.bial_flyeasy.models.FlightModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            else if (status == Constants.ERROR) title = "OOPS!!  Error Occurred.";
            else if (status == Constants.REGISTRATION_SUCCESS)
                title = "User Registered Successfully. Proceed to Login.";
        }

        textView.setText(title);
    }

    @BindingAdapter("topCurvedRadius")
    public static void topCurvedRadius(MaterialCardView cardView, float corner_size) {
        ShapeAppearanceModel.Builder shapeAppearanceModel = new ShapeAppearanceModel().toBuilder();
        shapeAppearanceModel.setTopLeftCorner(CornerFamily.ROUNDED, corner_size);
        shapeAppearanceModel.setTopLeftCorner(CornerFamily.ROUNDED, corner_size);
        cardView.setShapeAppearanceModel(shapeAppearanceModel.build());
    }

    @BindingAdapter("loadImage")
    public static void loadImage(ImageView imageView, String uri) {
        Picasso.get().load(Uri.parse(uri)).placeholder(R.drawable.image_plane).into(imageView);
    }

    @BindingAdapter("setTime")
    public static void setTime(TextView textView, String date_time) {
        textView.setText(HelperClass.getTime(date_time));
    }

    @BindingAdapter("setDate")
    public static void setDate(TextView textView, String date_time) {
        textView.setText(HelperClass.getDate(date_time));
    }

    @BindingAdapter("visible")
    public static void visible(View view, boolean b) {
        if(b) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.GONE);
    }

    @BindingAdapter("setFlightDetails")
    public static void setFlightDetails(TextView textView, FlightModel flight) {
        String text = "No Flight is Selected. Please select flight.";
        if (flight.get_id()!=null && !flight.get_id().equals("-1")) {
            text = "Flight No. : " + flight.getFlightNo() + "\n" +
                    "Flight Name : " + flight.getCompany() + "\n" +
                    "Price : " + flight.getPrice() + "\n" +
                    "Flight Time : " + HelperClass.getTime(flight.getDepartureTime());
        }
        textView.setText(text);
    }
}
