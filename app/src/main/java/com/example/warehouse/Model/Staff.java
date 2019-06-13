package com.example.warehouse.Model;

import com.google.gson.annotations.SerializedName;

public class Staff extends BaseResponse {


    @SerializedName("id")
    private int staffId;
    @SerializedName("name")
    private String staffName;
    @SerializedName("position")
    private String position;

    public int getStaffId() {
        return staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getPosition() {
        return position;
    }
}
