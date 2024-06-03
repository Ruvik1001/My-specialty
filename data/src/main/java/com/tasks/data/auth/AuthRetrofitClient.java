package com.tasks.data.auth;

import com.tasks.domain.auth.AuthAPIServices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthRetrofitClient {
    private static final String BASE_URL = "https://android-for-students.ru/";
    private static AuthAPIServices apiServiceInstance;

    public static AuthAPIServices getApiService() {
        if (apiServiceInstance == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiServiceInstance = retrofit.create(AuthAPIServices.class);
        }
        return apiServiceInstance;
    }
}
