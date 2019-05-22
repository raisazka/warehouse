package com.example.warehouse.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.warehouse.Model.Cart;
import com.example.warehouse.R;
import com.example.warehouse.ViewHolder.CartViewHolder;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    ArrayList<Cart> carts;

    public CartAdapter(ArrayList<Cart> carts) {
        this.carts = carts;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_cart, viewGroup,false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i) {
        cartViewHolder.name.setText(carts.get(i).getName());
        cartViewHolder.qty.setText(carts.get(i).getQty());
        cartViewHolder.remarks.setText(carts.get(i).getRemarks());
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }
}
