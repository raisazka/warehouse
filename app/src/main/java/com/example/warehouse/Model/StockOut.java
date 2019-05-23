package com.example.warehouse.Model;

import com.google.gson.annotations.SerializedName;

public class StockOut extends BaseResponse{

    @SerializedName("id")
    private int id;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("installer_id")
    private int installerId;
    @SerializedName("worker_id")
    private int workerId;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getInstallerId() {
        return installerId;
    }

    public int getWorkerId() {
        return workerId;
    }
}
