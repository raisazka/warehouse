package com.example.warehouse.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.warehouse.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public CardView itemCard;
    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.name = itemView.findViewById(R.id.item_name);
        this.itemCard = itemView.findViewById(R.id.card_item);
    }
}
