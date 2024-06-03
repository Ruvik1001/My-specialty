package com.tasks.university.presentation;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tasks.domain.auth.AuthAPIServices;
import com.tasks.domain.university.UniversityAPIServices;
import com.tasks.university.UniversityRouter;

public class UniversityViewModelFactory implements ViewModelProvider.Factory {
    private Context context;
    private UniversityRouter universityRouter;
    private UniversityAPIServices universityAPIServices;

    public UniversityViewModelFactory(
            Context context,
            UniversityRouter universityRouter,
            UniversityAPIServices universityAPIServices
    ) {
        this.context = context;
        this.universityRouter = universityRouter;
        this.universityAPIServices = universityAPIServices;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UniversityViewModel.class)) {
            return (T) new UniversityViewModel(context, universityRouter, universityAPIServices);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
