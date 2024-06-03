package com.tasks.data.univesity;

import com.tasks.domain.university.UniversityAPIServices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UniversityRetrofitClient {
    private static final String BASE_URL = "https://apidata.mos.ru/v1/";
    private static UniversityAPIServices apiServiceInstance;

    public static UniversityAPIServices getApiService() {
        if (apiServiceInstance == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiServiceInstance = retrofit.create(UniversityAPIServices.class);
        }
        return apiServiceInstance;
    }
}
