package com.tasks.university_info;

import android.util.Pair;

import androidx.lifecycle.ViewModel;

import com.tasks.domain.university.data.UniversityListItem;

public class UniversityInfoViewModel extends ViewModel {
    private Pair<UniversityListItem, String> item = null;

    public UniversityInfoViewModel() {}

    public boolean saved() {
        return item != null;
    }

    public UniversityListItem getUniversityInfo() {
        return item.first;
    }

    public String getUniversityImage() {
        return item.second;
    }

    public void setItem(Pair<UniversityListItem, String> item) {
        this.item = item;
    }
}