package com.tasks.university.presentation;

import android.content.Context;
import android.util.Pair;

import androidx.lifecycle.ViewModel;

import com.tasks.domain.university.UniversityAPIServices;
import com.tasks.domain.university.data.UniversityListItem;
import com.tasks.university.R;
import com.tasks.university.UniversityRouter;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UniversityViewModel extends ViewModel {
    private Context context;
    private UniversityRouter universityRouter;
    private UniversityAPIServices universityAPIServices;
    private List<Pair<UniversityListItem, String>> items;

    public void setItems(List<Pair<UniversityListItem, String>> items) {
        this.items = items;
    }

    public List<Pair<UniversityListItem, String>> getFiltered(String contains) {
        List<Pair<UniversityListItem, String>> filteredItems = new ArrayList<>();
        if (items != null) {
            filteredItems = items.stream()
                    .filter(pair -> pair.first.getCells() != null && pair.first.getCells().getShortName() != null &&
                            pair.first.getCells().getShortName().toLowerCase().contains(contains.toLowerCase()) ||
                            pair.first.getCells() != null && pair.first.getCells().getFullName() != null &&
                                    pair.first.getCells().getFullName().toLowerCase().contains(contains.toLowerCase())
                    )
                    .collect(Collectors.toList());
        }
        return filteredItems;
    }

    public UniversityViewModel(
            Context context,
            UniversityRouter universityRouter,
            UniversityAPIServices universityAPIServices
    ) {
        this.context = context;
        this.universityRouter = universityRouter;
        this.universityAPIServices = universityAPIServices;
    }

    public void search(String mrk, UniversityCallback callback) {
        search(mrk, 20, callback);
    }

    private void search(String mrk, Integer count, UniversityCallback callback) {
        Call<List<UniversityListItem>> call = universityAPIServices.searchUniversity(
                mrk,
                count
        );
        call.enqueue(new Callback<List<UniversityListItem>>() {
            @Override
            public void onResponse(Call<List<UniversityListItem>> call, Response<List<UniversityListItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Pair<UniversityListItem, String>> items = new ArrayList<>();
                    for (int ii = 0; ii < response.body().size(); ++ii) {
                        UniversityListItem cur = response.body().get(ii);
                        items.add(new Pair<>(cur, getFirstImage(Long.toString(cur.getCells().getGlobalId()))));
                    }
                    setItems(items);
                    callback.onSuccess(items);
                } else {
                    callback.onError("Failed to fetch data");
                }
            }

            @Override
            public void onFailure(Call<List<UniversityListItem>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public String getFirstImage(String query) {
        String resourceName = context.getString(com.tasks.core.R.string.t_gid) + query;
        String pictureUrl = "";
        if (context != null) {
            int resourceId = context.getResources().getIdentifier(resourceName, "string", context.getPackageName());
            if (resourceId != 0) {
                pictureUrl = context.getString(resourceId);
            }

        }
        return pictureUrl;
    }

    public void navigateToUniversity(
            Pair<UniversityListItem, String> item
    ) {
        universityRouter.goToUniversityInfo(item);
    }

    public interface UniversityCallback {
        void onSuccess(List<Pair<UniversityListItem, String>> response);
        void onError(String errorMessage);
    }


}