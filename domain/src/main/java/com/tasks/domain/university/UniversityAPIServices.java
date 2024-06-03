package com.tasks.domain.university;

import com.tasks.domain.university.data.UniversityListItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface UniversityAPIServices {
    @GET("datasets/3329/rows")
    Call<List<UniversityListItem>> searchUniversity(
            @Query("api_key") String apiKey,
            @Query("$top") int count
    );
}