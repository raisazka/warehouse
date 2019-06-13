package com.example.warehouse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;

import com.example.warehouse.TokenManager.TokenManager;

public class ChooseWarehouseActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    CardView wh1, wh2;
    TokenManager tokenManager;
    SharedPreferences prefs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_warehouse);
        wh1 = findViewById(R.id.warehouse_1);
        wh2 = findViewById(R.id.warehouse_2);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Log.d("ChooseWarehouseActivity", "onCreate: " + prefs.getString("role", null));
        tokenManager = TokenManager.getInstance(getSharedPreferences("preferences", MODE_PRIVATE));
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.logout:
                    tokenManager.deleteToken();
                    startActivity(new Intent(ChooseWarehouseActivity.this, MainActivity.class));
                    return true;
            }
            return false;
        });

        wh1.setOnClickListener(v -> {
            Intent intent = new Intent(ChooseWarehouseActivity.this, MenuActivity.class);
            prefs.edit().putInt("wh-id", 1).commit();
            startActivity(intent);
        });

        wh2.setOnClickListener(v -> {
            Intent intent = new Intent(ChooseWarehouseActivity.this, MenuActivity.class);
            prefs.edit().putInt("wh-id", 2).commit();
            startActivity(intent);
        });
    }
}
