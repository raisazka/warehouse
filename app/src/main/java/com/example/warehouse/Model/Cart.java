package com.example.warehouse.Model;

import com.google.gson.annotations.SerializedName;

public class Cart extends BaseResponse{

    @SerializedName("item_name")
    private String name;
    @SerializedName("item_qty")
    private int qty;
    @SerializedName("remarks")
    private String remarks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getRemarks() {
        return remarks;
    }
}
