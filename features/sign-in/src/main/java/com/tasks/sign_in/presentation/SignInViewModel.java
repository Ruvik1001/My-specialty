package com.tasks.sign_in.presentation;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.tasks.domain.auth.AuthAPIServices;
import com.tasks.domain.auth.data.AuthResponse;
import com.tasks.sign_in.SignInRouter;

public class SignInViewModel extends ViewModel {
    private SignInRouter signInRouter;
    private AuthAPIServices authAPIServices;

    public SignInViewModel(SignInRouter signInRouter, AuthAPIServices authAPIServices) {
        this.signInRouter = signInRouter;
        this.authAPIServices = authAPIServices;
    }

    public void signIn(String login, String password, String group, SignInCallback callback) {
        Call<AuthResponse> call = authAPIServices.auth(login, password, group);
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Request failed");
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void navigateToUniversity() {
        signInRouter.goToUniversity();
    }


    public interface SignInCallback {
        void onSuccess(AuthResponse response);
        void onError(String errorMessage);
    }
}