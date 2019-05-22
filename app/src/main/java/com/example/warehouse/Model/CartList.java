package com.example.warehouse.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CartList {

    @SerializedName("carts")
    private ArrayList<Cart> carts;

    public CartList(ArrayList<Cart> carts) {
        this.carts = carts;
    }

    public ArrayList<Cart> getCarts() {
        return carts;
    }
}
