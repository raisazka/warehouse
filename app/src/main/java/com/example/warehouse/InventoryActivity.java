package com.example.warehouse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


import com.example.warehouse.Adapter.ItemAdapter;
import com.example.warehouse.Model.Item;

import com.example.warehouse.Model.ItemList;
import com.example.warehouse.Network.ItemService;
import com.example.warehouse.Network.RetrofitBuilder;
import com.example.warehouse.TokenManager.TokenManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InventoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    Call<ItemList> call;
    ItemService service;
    TokenManager tokenManager;
    ItemAdapter adapter;
    int whId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        service = RetrofitBuilder.getRetrofit().create(ItemService.class);
        tokenManager = TokenManager.getInstance(getSharedPreferences("preferences", MODE_PRIVATE));
        whId = getIntent().getIntExtra("wh-id", 0);
        Log.d("InventoryActivity", "onCreate: " + whId);
        call = service.getItems("Bearer " + tokenManager.getToken().getAccessToken(), whId);
        call.enqueue(new Callback<ItemList>() {
            @Override
            public void onResponse(Call<ItemList> call, Response<ItemList> response) {
                generateData(response.body().getItems());
            }

            @Override
            public void onFailure(Call<ItemList> call, Throwable t) {
                Toast.makeText(InventoryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void generateData(ArrayList<Item> items){
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdapter(items);
        recyclerView.setAdapter(adapter);
    }
}
