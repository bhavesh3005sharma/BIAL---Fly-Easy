package com.fitnesshub.bial_flyeasy.retrofit;

import com.fitnesshub.bial_flyeasy.utils.HelperClass;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class RetrofitClient {
    private static Retrofit retrofit = null;

    @Provides
    @Singleton
    public static ApiServices getApiServices(Retrofit retrofit) {
        return retrofit.create(ApiServices.class);
    }

    @Provides
    @Singleton
    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(HelperClass.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
