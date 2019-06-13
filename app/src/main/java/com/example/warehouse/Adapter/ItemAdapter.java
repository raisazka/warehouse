package com.example.warehouse.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.warehouse.Model.Item;
import com.example.warehouse.R;
import com.example.warehouse.ShowItemActivity;
import com.example.warehouse.ViewHolder.ItemViewHolder;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private ArrayList<Item> items;

    public ItemAdapter(ArrayList<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder,final int i) {
        itemViewHolder.name.setText("Item Name: " + items.get(i).getName());
        itemViewHolder.id.setText("Item ID: " + items.get(i).getId());
        itemViewHolder.stock.setText("Stock: " + items.get(i).getStock());
        itemViewHolder.itemCard.setOnClickListener(v -> {
            Intent intent = new Intent(itemViewHolder.itemView.getContext(), ShowItemActivity.class);
            intent.putExtra("id", items.get(i).getId());
            itemViewHolder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
