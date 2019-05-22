package com.example.warehouse.Model;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("message")
    private String message;
    @SerializedName("code")
    private int code;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
