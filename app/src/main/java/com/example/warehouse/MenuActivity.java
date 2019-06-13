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
import android.widget.Toast;

import com.example.warehouse.TokenManager.TokenManager;

public class MenuActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TokenManager tokenManager;
    CardView inventory, stock_in, stock_out, report;
    SharedPreferences prefs;
    int whId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        report = findViewById(R.id.report);
        inventory = findViewById(R.id.inventory);
        stock_in = findViewById(R.id.stock_in);
        stock_out = findViewById(R.id.stock_out);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        whId =  prefs.getInt("wh-id", 0);
        Log.d("MenuActivity", "onCreate: " + whId);
        Log.d("MenuActivity", "onCreate: " + prefs.getString("role", null));
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        tokenManager = TokenManager.getInstance(getSharedPreferences("preferences", MODE_PRIVATE));
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.logout:
                    tokenManager.deleteToken();
                    startActivity(new Intent(MenuActivity.this, MainActivity.class));
                    return true;
            }
            return false;
        });

        inventory.setOnClickListener(v -> {
            Intent i = new Intent(MenuActivity.this, InventoryActivity.class);
            i.putExtra("wh-id", whId);
            startActivity(i);
        });

        stock_in.setOnClickListener(v -> {
            if(prefs.getString("role", null).equals("user3")){
                Toast.makeText(this, "Unauthorized", Toast.LENGTH_SHORT).show();
            }else{
                Intent i = new Intent(MenuActivity.this, StockActivity.class);
                prefs.edit().putString("stock-type", "stock-in").commit();
                startActivity(i);
            }
        });

        stock_out.setOnClickListener(v -> {
            Intent i = new Intent(MenuActivity.this, StockActivity.class);
            prefs.edit().putString("stock-type", "stock-out").commit();
            startActivity(i);
        });

        report.setOnClickListener(v -> {
            if(prefs.getString("role", null).equals("admin")){
                Intent i = new Intent(MenuActivity.this, ReportActivity.class);
                startActivity(i);
            }else{
                Toast.makeText(this, "Unauthorized", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
