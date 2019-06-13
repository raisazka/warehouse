package com.example.warehouse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehouse.Adapter.CartAdapter;
import com.example.warehouse.Model.Cart;
import com.example.warehouse.Model.CartList;
import com.example.warehouse.Model.Staff;
import com.example.warehouse.Model.StaffList;
import com.example.warehouse.Model.StockIn;
import com.example.warehouse.Model.StockOut;
import com.example.warehouse.Network.CartService;
import com.example.warehouse.Network.RetrofitBuilder;
import com.example.warehouse.Network.StockInService;
import com.example.warehouse.Network.StockOutService;
import com.example.warehouse.TokenManager.TokenManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    CartService service;
    StockOutService stockOutService;
    StockInService stockInService;
    Call<StaffList> staffListCall;
    Call<CartList> cartListCall, cartInListCall;
    Call<StockOut> stockOutCall;
    Call<StockIn>  stockInCall;
    RelativeLayout staffLayout;
    TokenManager tokenManager;
    SharedPreferences prefs;
    AlertDialog dialog;
    Spinner spinner;
    TextView staffsTitle;
    RecyclerView recyclerView;
    CartAdapter adapter;
    Button btnCheckout;
    int staffsId;
    String positions;
    TextView title;
    ArrayAdapter<CharSequence> adapterSpinner;
    private static final String TAG = "CartActivity";

    void init(){
        staffsTitle = findViewById(R.id.workers_title);
        spinner = findViewById(R.id.spinner);
        staffLayout = findViewById(R.id.workers_layout);
        btnCheckout = findViewById(R.id.btn_checkout);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        tokenManager = TokenManager.getInstance(getSharedPreferences("preferences", MODE_PRIVATE));
        service = RetrofitBuilder.getRetrofit().create(CartService.class);
        stockOutService = RetrofitBuilder.getRetrofit().create(StockOutService.class);
        stockInService = RetrofitBuilder.getRetrofit().create(StockInService.class);
        title = findViewById(R.id.title_cart);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();
        adapterSpinner = ArrayAdapter.createFromResource(this, R.array.positions, android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinner);
        if(prefs.getString("stock-type", null).equals("stock-out")){
            cartListCall = service.getCart("Bearer " + tokenManager.getToken().getAccessToken());
            cartListCall.enqueue(new Callback<CartList>() {
                @Override
                public void onResponse(Call<CartList> call, Response<CartList> response) {
                    generateData(response.body().getCarts());
                }

                @Override
                public void onFailure(Call<CartList> call, Throwable t) {
                    Toast.makeText(CartActivity.this,"OnFailure: " +  t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }else{
            cartInListCall = service.getCartIn("Bearer " + tokenManager.getToken().getAccessToken());
            cartInListCall.enqueue(new Callback<CartList>() {
                @Override
                public void onResponse(Call<CartList> call, Response<CartList> response) {
                    generateData(response.body().getCarts());
                }

                @Override
                public void onFailure(Call<CartList> call, Throwable t) {
                    Toast.makeText(CartActivity.this, "OnFailure: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positions = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "Position: " + positions);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getBaseContext(), "Please Select Position", Toast.LENGTH_LONG).show();
            }
        });

        Log.d(TAG, "Position: " + positions);

        staffLayout.setOnClickListener(v -> {
            staffListCall = service.getStaff("Bearer " + tokenManager.getToken().getAccessToken(), positions);
            Log.d(TAG, "onCreate: " + staffListCall);
            staffListCall.enqueue(new Callback<StaffList>() {
                @Override
                public void onResponse(Call<StaffList> call, Response<StaffList> response) {
                    final ArrayList<String> staffsList = new ArrayList<>();
                    final ArrayList<Integer> staffsID = new ArrayList<>();
                    for(Staff staff: response.body().getStaffList()){
                        staffsList.add(staff.getStaffName());
                        staffsID.add(staff.getStaffId());
                    }
                    String staffsName [] = new String[staffsList.size()];
                    staffsName = staffsList.toArray(staffsName);
                    AlertDialog.Builder staffNameBuilder = new AlertDialog.Builder(CartActivity.this);
                    staffNameBuilder.setTitle("Choose staff");
                    staffNameBuilder.setSingleChoiceItems(staffsName, -1, (dialog, which) -> {
                        staffsTitle.setText(staffsList.get(which));
                        staffsId = staffsID.get(which);
                        dialog.dismiss();
                    });

                    staffNameBuilder.setNeutralButton("Cancel", (dialog, which) -> {

                    });

                    dialog = staffNameBuilder.create();
                    dialog.show();
                }

                @Override
                public void onFailure(Call<StaffList> call, Throwable t) {
                    Toast.makeText(CartActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            });
        });

        Log.d(TAG, "StaffId: " + staffsId);

        btnCheckout.setOnClickListener(v -> {
            if(prefs.getString("stock-type", null).equals("stock-out")){
                stockOutCall = stockOutService.createStockOut("Bearer " + tokenManager.getToken().getAccessToken(), staffsId);
                stockOutCall.enqueue(new Callback<StockOut>() {
                    @Override
                    public void onResponse(Call<StockOut> call, Response<StockOut> response) {
                        if(response.body().getCode() == 200){
                            Toast.makeText(CartActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            Intent i = new Intent(CartActivity.this, MenuActivity.class);
                            i.setFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(CartActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StockOut> call, Throwable t) {
                        Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                stockInCall = stockInService.createStockIn("Bearer " + tokenManager.getToken().getAccessToken(), staffsId);
                stockInCall.enqueue(new Callback<StockIn>() {
                    @Override
                    public void onResponse(Call<StockIn> call, Response<StockIn> response) {
                        Log.d(TAG, "onResponse: " + response.body());
                        if(response.body().getCode() == 200){
                            Toast.makeText(CartActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            Intent i = new Intent(CartActivity.this, MenuActivity.class);
                            i.setFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(CartActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StockIn> call, Throwable t) {
                        Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        if(prefs.getString("stock-type", null).equals("stock-in")){
            title.setText("Cart Stock In");
        }else{
            title.setText("Cart Stock Out");
        }

    }
        public void generateData(ArrayList<Cart> carts){
            recyclerView = findViewById(R.id.cart_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new CartAdapter(carts);
            recyclerView.setAdapter(adapter);
        }
}
