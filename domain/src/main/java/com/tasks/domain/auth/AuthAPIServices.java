package com.tasks.domain.auth;

import com.tasks.domain.auth.data.AuthResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthAPIServices {
    @FormUrlEncoded
    @POST("coursework/login.php")
    Call<AuthResponse> auth(
            @Field("lgn") String login,
            @Field("pwd") String password,
            @Field("g") String group
    );

}
