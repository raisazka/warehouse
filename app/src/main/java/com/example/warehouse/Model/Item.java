package com.example.warehouse.Model;

import com.google.gson.annotations.SerializedName;


public class Item extends BaseResponse{

    @SerializedName("id")
    private String id;
    @SerializedName("item_description")
    private String name;
    @SerializedName("item_type_id")
    private String type;
    @SerializedName("stock")
    private int stock;
    @SerializedName("expiry_date")
    private String expiryDate;
    @SerializedName("size")
    private String size;


    public String getId() {
        return id;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public int getStock() {
        return stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
