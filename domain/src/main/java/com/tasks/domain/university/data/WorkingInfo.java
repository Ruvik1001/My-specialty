package com.tasks.domain.university.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WorkingInfo implements Serializable {
    @SerializedName("DayOfWeek")
    private String dayOfWeek;
    @SerializedName("Hours")
    private String hours;
    @SerializedName("LunchBreak")
    private String lunchBreak;
    @SerializedName("global_id")
    private int globalId;
    @SerializedName("is_deleted")
    private int isDeleted;

    public WorkingInfo(String dayOfWeek, String hours, String lunchBreak, int globalId, int isDeleted) {
        this.dayOfWeek = dayOfWeek;
        this.hours = hours;
        this.lunchBreak = lunchBreak;
        this.globalId = globalId;
        this.isDeleted = isDeleted;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getHours() {
        return hours;
    }

    public String getLunchBreak() {
        return lunchBreak;
    }

    public int getGlobalId() {
        return globalId;
    }

    public int getIsDeleted() {
        return isDeleted;
    }
}
