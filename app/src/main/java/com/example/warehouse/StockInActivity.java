package com.example.warehouse;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehouse.Model.Item;
import com.example.warehouse.Model.ItemList;
import com.example.warehouse.Model.ItemType;
import com.example.warehouse.Model.ItemTypeList;
import com.example.warehouse.Model.StockIn;
import com.example.warehouse.Network.ItemService;
import com.example.warehouse.Network.RetrofitBuilder;
import com.example.warehouse.Network.StockInService;
import com.example.warehouse.TokenManager.TokenManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockInActivity extends AppCompatActivity {

    TokenManager tokenManger;
    Call<ItemTypeList> call;
    Call<ItemList> itemListCall;
    Call<StockIn> stockInCall;
    ImageView subcstract , add;
    ItemService service;
    StockInService stockInService;
    RelativeLayout layoutType, layoutName;
    TextView itemTypeTitle, itemNameTitle, qtyTitle;
    int itemTypeID = 0, qty = 0, itemID;
    Button btnStockIn;
    EditText remarks;
    private static final String TAG = "InventoryActivity";

    private void init(){
        layoutType = findViewById(R.id.layout_type);
        qtyTitle = findViewById(R.id.qty_title);
        itemNameTitle = findViewById(R.id.item_name_title);
        itemTypeTitle = findViewById(R.id.item_type_title);
        layoutName = findViewById(R.id.layout_name);
        btnStockIn = findViewById(R.id.btn_stockin);
        subcstract = findViewById(R.id.substract);
        add = findViewById(R.id.add);
        remarks = findViewById(R.id.remarks);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockin);
        init();
        tokenManger = TokenManager.getInstance(getSharedPreferences("preferences", MODE_PRIVATE));
        service = RetrofitBuilder.getRetrofit().create(ItemService.class);
        stockInService = RetrofitBuilder.getRetrofit().create(StockInService.class);

        layoutType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call = service.getItemTypes("Bearer " + tokenManger.getToken().getAccessToken());
                call.enqueue(new Callback<ItemTypeList>() {
                    @Override
                    public void onResponse(Call<ItemTypeList> call, Response<ItemTypeList> response) {
                        final ArrayList<String> typeName = new ArrayList<>();
                        final ArrayList<Integer> typeId = new ArrayList<>();
                        for (ItemType item : response.body().getItemTypes()){
                            typeName.add(item.getName());
                            typeId.add(item.getId());
                        }
                        String [] items = new String[typeName.size()];
                        items = typeName.toArray(items);
                        AlertDialog.Builder builder = new AlertDialog.Builder(StockInActivity.this);
                        builder.setTitle("Set Item Type");
                        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                itemTypeTitle.setText(typeName.get(which));
                                itemTypeID = typeId.get(which);
                                dialog.dismiss();
                            }
                        });
                        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                    @Override
                    public void onFailure(Call<ItemTypeList> call, Throwable t) {
                        Toast.makeText(StockInActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        layoutName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Item Type ID: " + itemTypeID);
                if(itemTypeID == 0){
                    Toast.makeText(StockInActivity.this, "Please Select Item Type First", Toast.LENGTH_LONG).show();
                }else{
                    itemListCall = service.getTypesItem("Bearer " + tokenManger.getToken().getAccessToken(), itemTypeID);
                    itemListCall.enqueue(new Callback<ItemList>() {
                        @Override
                        public void onResponse(Call<ItemList> call, Response<ItemList> response) {
                            final ArrayList<String> itemNameList = new ArrayList<>();
                            final ArrayList<Integer> itemIdList = new ArrayList<>();
                            for (Item item : response.body().getItems()){
                                itemNameList.add(item.getName());
                                itemIdList.add(item.getId());
                            }

                            String itemName [] = new String[itemNameList.size()];
                            itemName = itemNameList.toArray(itemName);
                            AlertDialog.Builder itemNameBuilder = new AlertDialog.Builder(StockInActivity.this);
                            itemNameBuilder.setTitle("Choose Item");
                            itemNameBuilder.setSingleChoiceItems(itemName, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    itemNameTitle.setText(itemNameList.get(which));
                                    itemID = itemIdList.get(which);
                                    dialog.dismiss();
                                }
                            });

                            itemNameBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            AlertDialog dialog2 = itemNameBuilder.create();
                            dialog2.show();

                        }

                        @Override
                        public void onFailure(Call<ItemList> call, Throwable t) {
                            Toast.makeText(StockInActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                            Log.d(TAG, "onFailure: " + t.getMessage());
                        }
                    });
                }

            }
        });

        subcstract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qty <= 1){
                    Toast.makeText(StockInActivity.this, "Minimal Quantity " + qty, Toast.LENGTH_LONG).show();
                }else{
                    qty--;
                    Toast.makeText(StockInActivity.this, "Qty: " + qty, Toast.LENGTH_LONG).show();
                }
                qtyTitle.setText(Integer.toString(qty));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty++;
                Toast.makeText(StockInActivity.this, "Qty: " + qty, Toast.LENGTH_LONG).show();
                qtyTitle.setText(Integer.toString(qty));
            }
        });


        btnStockIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stockInCall = stockInService.createStockIn("Bearer " + tokenManger.getToken().getAccessToken(),itemID, qty, remarks.getText().toString());
                stockInCall.enqueue(new Callback<StockIn>() {
                    @Override
                    public void onResponse(Call<StockIn> call, Response<StockIn> response) {
                        if(response.body().getCode() == 400){
                            Toast.makeText(StockInActivity.this, "Error: " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(StockInActivity.this, "Success", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(StockInActivity.this, MenuActivity.class);
                            i.setFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<StockIn> call, Throwable t) {
                        Toast.makeText(StockInActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}
