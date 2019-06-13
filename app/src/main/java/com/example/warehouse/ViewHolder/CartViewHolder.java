package com.example.warehouse.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.warehouse.R;
import com.example.warehouse.TokenManager.TokenManager;

public class CartViewHolder extends RecyclerView.ViewHolder {
    public TextView name, qty, remarks;
    public ImageView btnDelete;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        this.name = itemView.findViewById(R.id.item_name);
        this.qty = itemView.findViewById(R.id.item_qty);
        this.remarks = itemView.findViewById(R.id.item_remarks);
        this.btnDelete = itemView.findViewById(R.id.btn_delete);
    }
}
