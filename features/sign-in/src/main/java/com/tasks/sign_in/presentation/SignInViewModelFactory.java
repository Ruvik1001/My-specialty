package com.tasks.sign_in.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tasks.domain.auth.AuthAPIServices;
import com.tasks.sign_in.SignInRouter;

public class SignInViewModelFactory implements ViewModelProvider.Factory {

    private SignInRouter signInRouter;
    private AuthAPIServices authAPIServices;

    public SignInViewModelFactory(SignInRouter signInRouter, AuthAPIServices authAPIServices) {
        this.signInRouter = signInRouter;
        this.authAPIServices = authAPIServices;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SignInViewModel.class)) {
            return (T) new SignInViewModel(signInRouter, authAPIServices);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
