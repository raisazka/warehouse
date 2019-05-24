package com.example.warehouse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehouse.Fragment.AdjustDialogFragment;
import com.example.warehouse.Model.Item;
import com.example.warehouse.Network.ItemService;
import com.example.warehouse.Network.RetrofitBuilder;
import com.example.warehouse.TokenManager.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowItemActivity extends AppCompatActivity {

    Button btnAdjust;
    TextView itemName, stock, size, expDate;
    ItemService service;
    Call<Item> call;
    TokenManager tokenManager;
    public static int id;
    private static final String TAG = "ShowItemActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item);
        btnAdjust = findViewById(R.id.btn_adjust);
        itemName = findViewById(R.id.item_name_inventory);
        stock = findViewById(R.id.stock);
        size = findViewById(R.id.size);
        expDate = findViewById(R.id.exp_date);
        id = getIntent().getIntExtra("id", 0);
        Log.d(TAG, "ItemID: " + id);
        service = RetrofitBuilder.getRetrofit().create(ItemService.class);
        tokenManager = TokenManager.getInstance(getSharedPreferences("preferences", MODE_PRIVATE));
        call = service.showItem("Bearer " + tokenManager.getToken().getAccessToken(), id);
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                itemName.setText(response.body().getName());
                size.setText(response.body().getSize());
                expDate.setText(response.body().getExpiryDate());
                stock.setText(Integer.toString(response.body().getStock()));
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Toast.makeText(ShowItemActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        btnAdjust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v);
            }
        });
    }

    public void showDialog(View view){
        AdjustDialogFragment dialog = new AdjustDialogFragment();
        dialog.show(getSupportFragmentManager(), "Set Stock");
    }
}
