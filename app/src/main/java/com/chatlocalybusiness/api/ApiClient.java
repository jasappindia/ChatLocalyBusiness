package com.chatlocalybusiness.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

       // public static final String BASE_URL = "http://192.168.0.60/chatlocaly/business_api/";
      public static final String BASE_URL = "http://184.154.53.181/chatlocaly/business_api_v1/";
   // public static final String BASE_URL = "http://184.154.53.181/chatlocaly_live/business_api_live/";


//      public static final String BASE_ vcy=45x5
// \\\\\\\\\\URL = "http://184.154.53.181/chatlocaly/business_api/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(1, TimeUnit.MINUTES).readTimeout(1000, TimeUnit.SECONDS).writeTimeout(1000, TimeUnit.SECONDS).build();

        Gson gson = new GsonBuilder().setLenient().create();

        if (retrofit==null) {
            retrofit = new Retrofit.Builder(). baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient).build();
        }
        return retrofit;
    }
}