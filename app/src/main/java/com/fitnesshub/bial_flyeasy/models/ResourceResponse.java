package com.fitnesshub.bial_flyeasy.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class ResourceResponse<T> {

    @NonNull
    public final int status;

    @SerializedName("user_data")
    @Nullable
    public T data;

    @Nullable
    public String message;

    @Nullable
    public String token;

    public ResourceResponse(@NonNull int status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
