package com.fitnesshub.bial_flyeasy.retrofit;

import android.content.Context;

import com.fitnesshub.bial_flyeasy.BuildConfig;
import com.fitnesshub.bial_flyeasy.database.Prefs;
import com.fitnesshub.bial_flyeasy.utils.HelperClass;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static Prefs prefs = null;

    @Provides
    @Singleton
    public static ApiServices getApiServices(Retrofit retrofit) {
        return retrofit.create(ApiServices.class);
    }

    @Provides
    @Singleton
    public static Prefs getPrefs(@ApplicationContext Context context) {
        if (prefs == null) prefs = new Prefs(context);
        return prefs;
    }

    @Provides
    @Singleton
    public static Retrofit getInstance(@ApplicationContext Context context) {

        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                logging.redactHeader("Authorization");
                logging.redactHeader("Cookie");
                httpClient.addInterceptor(logging);
            }

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(HelperClass.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(getPrefs(context).getToken());

            if (!httpClient.interceptors().contains(interceptor))
                httpClient.addInterceptor(interceptor);

            builder.client(httpClient.build());
            return retrofit = builder.build();

        }
        return retrofit;
    }
}
