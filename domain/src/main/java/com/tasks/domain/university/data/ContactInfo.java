package com.tasks.domain.university.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ContactInfo implements Serializable {
    @SerializedName("Email")
    private List<Email> email;
    @SerializedName("ExtraInfo")
    private String extraInfo;
    @SerializedName("FIO")
    private String fio;
    @SerializedName("Location")
    private String location;
    @SerializedName("Phone")
    private List<Phone> phone;
    @SerializedName("Position")
    private String position;
    @SerializedName("WorkingInfo")
    private List<WorkingInfo> workingInfo;
    @SerializedName("global_id")
    private int globalId;
    @SerializedName("is_deleted")
    private int isDeleted;

    public ContactInfo(List<Email> email, String extraInfo, String fio, String location, List<Phone> phone, String position, List<WorkingInfo> workingInfo, int globalId, int isDeleted) {
        this.email = email;
        this.extraInfo = extraInfo;
        this.fio = fio;
        this.location = location;
        this.phone = phone;
        this.position = position;
        this.workingInfo = workingInfo;
        this.globalId = globalId;
        this.isDeleted = isDeleted;
    }

    public List<Email> getEmail() {
        return email;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public String getFio() {
        return fio;
    }

    public String getLocation() {
        return location;
    }

    public List<Phone> getPhone() {
        return phone;
    }

    public String getPosition() {
        return position;
    }

    public List<WorkingInfo> getWorkingInfo() {
        return workingInfo;
    }

    public int getGlobalId() {
        return globalId;
    }

    public int getIsDeleted() {
        return isDeleted;
    }
}
