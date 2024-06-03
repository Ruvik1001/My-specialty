package com.tasks.domain.university.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Phone implements Serializable {
    @SerializedName("Phone")
    private String phone;
    @SerializedName("global_id")
    private int globalId;
    @SerializedName("is_deleted")
    private int isDeleted;

    public Phone(String phone, int globalId, int isDeleted) {
        this.phone = phone;
        this.globalId = globalId;
        this.isDeleted = isDeleted;
    }

    public String getPhone() {
        return phone;
    }

    public int getGlobalId() {
        return globalId;
    }

    public int getIsDeleted() {
        return isDeleted;
    }
}
