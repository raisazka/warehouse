package com.example.warehouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;

import com.example.warehouse.TokenManager.TokenManager;

public class MenuActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TokenManager tokenManager;
    CardView inventory, stock_in, stock_out, report;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        report = findViewById(R.id.report);
        inventory = findViewById(R.id.inventory);
        stock_in = findViewById(R.id.stock_in);
        stock_out = findViewById(R.id.stock_out);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        tokenManager = TokenManager.getInstance(getSharedPreferences("preferences", MODE_PRIVATE));
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.logout:
                        tokenManager.deleteToken();
                        startActivity(new Intent(MenuActivity.this, MainActivity.class));
                        return true;
                }
                return false;
            }
        });

        inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, InventoryActivity.class);
                startActivity(i);
            }
        });

        stock_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, StockInActivity.class);
                startActivity(i);
            }
        });

        stock_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, StockOutActivity.class);
                startActivity(i);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, ReportActivity.class);
                startActivity(i);
            }
        });

    }
}
