package com.example.warehouse.Model;

import com.google.gson.annotations.SerializedName;

public class StockOut extends BaseResponse{

    @SerializedName("id")
    private int id;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("staff_id")
    private int staffId;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getStaffId() {
        return staffId;
    }
}
