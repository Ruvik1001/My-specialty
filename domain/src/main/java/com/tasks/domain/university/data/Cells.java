package com.tasks.domain.university.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Cells implements Serializable {
    @SerializedName("Code")
    private int code;
    @SerializedName("ContactInfo")
    private List<ContactInfo> contactInfo;
    @SerializedName("FacultyName")
    private String facultyName;
    @SerializedName("FullName")
    private String fullName;
    @SerializedName("ShortName")
    private String shortName;
    @SerializedName("global_id")

    private long globalId;

    public Cells(int code, List<ContactInfo> contactInfo, String facultyName, String fullName, String shortName, long globalId) {
        this.code = code;
        this.contactInfo = contactInfo;
        this.facultyName = facultyName;
        this.fullName = fullName;
        this.shortName = shortName;
        this.globalId = globalId;
    }

    public int getCode() {
        return code;
    }

    public List<ContactInfo> getContactInfo() {
        return contactInfo;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public long getGlobalId() {
        return globalId;
    }
}
