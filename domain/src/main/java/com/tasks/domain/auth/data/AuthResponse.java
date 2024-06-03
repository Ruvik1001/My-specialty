package com.tasks.domain.auth.data;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public class AuthResponse {

    @SerializedName("result_code")
    private int resultCode;

    @SerializedName("variant")
    private int variant;

    @SerializedName("title")
    private String title;

    @SerializedName("task")
    private String task;

    @SerializedName("data")
    private JsonElement data;

    @SerializedName("error")
    private String error;

    public int getResultCode() {
        return resultCode;
    }

    public int getVariant() {
        return variant;
    }

    public String getTitle() {
        return title;
    }

    public String getTask() {
        return task;
    }

    public JsonElement getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "resultCode=" + resultCode +
                ", variant=" + variant +
                ", title='" + title + '\'' +
                ", task='" + task + '\'' +
                ", data='" + data + '\'' +
                ", error='" + error + '\'' +
                '}';
    }

    public String toStringFormatted() {
        return "variant " + variant + '\n' +
                "title " + title + '\n' +
                "task " + task + '\n' +
                "data " + data + '\n';
    }
}