package com.example.warehouse.Model;

import com.google.gson.annotations.SerializedName;

public class ItemType {

    @SerializedName("id")
    private int id;
    @SerializedName("item_type_name")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
