package com.tasks.university.presentation;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tasks.data.univesity.UniversityRetrofitClient;
import com.tasks.domain.university.data.UniversityListItem;
import com.tasks.university.R;
import com.tasks.university.UniversityRouter;
import com.tasks.university.adapter.UniversityAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class UniversityFragment extends Fragment {

    private UniversityRouter universityRouter;
    private UniversityViewModel mViewModel;
    private EditText etItemName;
    private ImageButton ibClear;
    private ImageButton ibSearch;
    private RecyclerView rvItems;
    private ProgressBar progressBar;
    private UniversityAdapter universityAdapter; // Adapter for RecyclerView

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof UniversityRouter) {
            universityRouter = (UniversityRouter) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement UniversityRouter");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        universityRouter = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_university, container, false);
        mViewModel = new ViewModelProvider(
                this,
                new UniversityViewModelFactory(
                        this.getContext(),
                        universityRouter,
                        UniversityRetrofitClient.getApiService()
                )
        ).get(UniversityViewModel.class);

        // Initialize views
        etItemName = view.findViewById(R.id.etItemName);
        ibClear = view.findViewById(R.id.ibClear);
        ibSearch = view.findViewById(R.id.ibSearch);
        rvItems = view.findViewById(R.id.rvItems);
        progressBar = view.findViewById(R.id.pBar);

        // Set up RecyclerView
        rvItems.setLayoutManager(new LinearLayoutManager(getContext()));
        universityAdapter = new UniversityAdapter(new ArrayList<>(), item -> {
            universityAdapter.clearData();
            mViewModel.navigateToUniversity(item);
        });
        rvItems.setAdapter(universityAdapter);

        // Add listeners
        etItemName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ibClear.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
                universityAdapter.updateData(mViewModel.getFiltered(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        ibClear.setOnClickListener(v -> {
            etItemName.setText("");
            hideInputBoard(requireContext(), etItemName.getWindowToken());
            universityAdapter.updateData(mViewModel.getFiltered(etItemName.getText().toString()));
        });

        ibSearch.setOnClickListener(v ->
                universityAdapter.updateData(mViewModel.getFiltered(etItemName.getText().toString()))
        );

        searchUniversities();

        return view;
    }

    private void startProgress() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        setEnabledAll(false);
    }

    private void stopProgress() {
        progressBar.setVisibility(ProgressBar.GONE);
        setEnabledAll(true);
    }

    private void setEnabledAll(boolean enabled) {
        etItemName.setEnabled(enabled);
        ibClear.setEnabled(enabled);
        ibSearch.setEnabled(enabled);
    }

    private void searchUniversities() {
        startProgress();
        mViewModel.search(getString(com.tasks.core.R.string.mrk), new UniversityViewModel.UniversityCallback() {
            @Override
            public void onSuccess(List<Pair<UniversityListItem, String>> response) {
                HashSet<Pair<UniversityListItem, String>> uniquePairs = new HashSet<>(response);
                ArrayList<Pair<UniversityListItem, String>> uniqueList = new ArrayList<>(uniquePairs);

                uniqueList.removeIf(pair -> pair.first == null || pair.first.getCells() == null ||
                        pair.first.getCells().getShortName() == null ||
                        pair.first.getCells().getShortName().isEmpty());


                universityAdapter.updateData(uniqueList);
                stopProgress();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                stopProgress();
                searchUniversities();
            }
        });
    }

    private void hideInputBoard(Context applicationContext, IBinder windowToken) {
        InputMethodManager inputMethodManager = (InputMethodManager) applicationContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
        }
    }
}
