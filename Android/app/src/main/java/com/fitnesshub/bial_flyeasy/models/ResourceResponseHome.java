package com.fitnesshub.bial_flyeasy.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResourceResponseHome {

    @NonNull
    public int status;

    @Nullable
    public String message;

    @Nullable
    public String wifi_password;

    @Nullable
    public List<String> guidelines;

    @Nullable
    public List<FlightModel> arrival_flights;

    @Nullable
    public List<FlightModel> departure_flights;

    @Nullable
    public TicketModel latest_ticket;

    public ResourceResponseHome(int status, @Nullable String message, @Nullable String wifi_password, @Nullable List<String> guidelines, @Nullable List<FlightModel> arrival_flights, @Nullable List<FlightModel> departure_flights, @Nullable TicketModel latest_ticket) {
        this.status = status;
        this.message = message;
        this.wifi_password = wifi_password;
        this.guidelines = guidelines;
        this.arrival_flights = arrival_flights;
        this.departure_flights = departure_flights;
        this.latest_ticket = latest_ticket;
    }

    public ResourceResponseHome(int status, @Nullable String message) {
        this.status = status;
        this.message = message;
    }
}

