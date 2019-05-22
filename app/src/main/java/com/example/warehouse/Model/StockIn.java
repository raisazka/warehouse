package com.example.warehouse.Model;

import com.google.gson.annotations.SerializedName;

public class StockIn extends BaseResponse {

    @SerializedName("user_id")
    private int userId;
    @SerializedName("item_id")
    private int itemId;
    @SerializedName("qty")
    private int qty;
    @SerializedName("remarks")
    private String remarks;
    @SerializedName("created_at")
    private String createdAt;

    public int getUserId() {
        return userId;
    }

    public int getItemId() {
        return itemId;
    }

    public int getQty() {
        return qty;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
