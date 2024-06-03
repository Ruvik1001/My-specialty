package com.tasks.domain.university.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Email implements Serializable {
    @SerializedName("Email")
    private String email;
    @SerializedName("global_id")
    private int globalId;
    @SerializedName("is_deleted")
    private int isDeleted;

    public Email(String email, int globalId, int isDeleted) {
        this.email = email;
        this.globalId = globalId;
        this.isDeleted = isDeleted;
    }

    public String getEmail() {
        return email;
    }

    public int getGlobalId() {
        return globalId;
    }

    public int getIsDeleted() {
        return isDeleted;
    }
}
