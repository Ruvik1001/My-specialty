package com.tasks.domain.university.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class UniversityListItem implements Serializable {
    @SerializedName("Cells")
    private Cells cells;
    @SerializedName("Number")
    private int number;
    @SerializedName("global_id")
    private long globalId;

    public UniversityListItem(Cells cells, int number, long globalId) {
        this.cells = cells;
        this.number = number;
        this.globalId = globalId;
    }

    public Cells getCells() {
        return cells;
    }

    public int getNumber() {
        return number;
    }

    public long getGlobalId() {
        return globalId;
    }
}

