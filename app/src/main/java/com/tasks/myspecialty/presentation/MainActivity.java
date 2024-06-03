package com.tasks.myspecialty.presentation;

import static androidx.navigation.Navigation.findNavController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;

import android.os.Bundle;
import android.util.Pair;

import com.tasks.domain.university.data.UniversityListItem;
import com.tasks.myspecialty.R;
import com.tasks.sign_in.SignInRouter;
import com.tasks.university.UniversityRouter;

import java.util.concurrent.CompletableFuture;


public class MainActivity extends AppCompatActivity implements SignInRouter, UniversityRouter {
    private static NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CompletableFuture.runAsync(this::init);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        navController = findNavController(this, R.id.fragmentContainerView);
    }

    @Override
    public void onBackPressed() {
        if (!navController.popBackStack()) {
            super.onBackPressed();
        }
    }

    @Override
    public void goToUniversity() {
        NavOptions navOptions = new NavOptions.Builder()
                .setPopUpTo(R.id.signInFragment, true)
                .build();
        navController.navigate(R.id.action_signInFragment_to_universityFragment, null, navOptions);
    }

    @Override
    public void goToUniversityInfo(Pair<UniversityListItem, String> item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(com.tasks.core.R.string.KEY_BUNDLE_UNIVERSITY_TO_UNIVERSITY_INFO_UNIVERSITY_LIST_ITEM), item.first);
        bundle.putString(getString(com.tasks.core.R.string.KEY_BUNDLE_UNIVERSITY_TO_UNIVERSITY_INFO_IMAGE_URL), item.second);
        navController.navigate(R.id.action_universityFragment_to_universityInfoFragment, bundle);
    }

}
