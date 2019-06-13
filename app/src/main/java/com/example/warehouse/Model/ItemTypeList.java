package com.example.warehouse.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ItemTypeList {

    @SerializedName("item_types")
    private ArrayList<ItemType> itemTypes;

    public ItemTypeList(ArrayList<ItemType> itemTypes) {
        this.itemTypes = itemTypes;
    }

    public ArrayList<ItemType> getItemTypes() {
        return itemTypes;
    }
}
