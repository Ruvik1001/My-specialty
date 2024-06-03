package com.tasks.university;

import android.util.Pair;

import com.tasks.domain.university.data.UniversityListItem;

public interface UniversityRouter {
    void goToUniversityInfo(Pair<UniversityListItem, String> item);
}
