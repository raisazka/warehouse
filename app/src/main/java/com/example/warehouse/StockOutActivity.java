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

import com.example.warehouse.Model.Cart;
import com.example.warehouse.Model.Item;
import com.example.warehouse.Model.ItemList;
import com.example.warehouse.Model.ItemType;
import com.example.warehouse.Model.ItemTypeList;
import com.example.warehouse.Network.CartService;
import com.example.warehouse.Network.ItemService;
import com.example.warehouse.Network.RetrofitBuilder;
import com.example.warehouse.TokenManager.TokenManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockOutActivity extends AppCompatActivity {


    RelativeLayout layoutItemType, layoutItemName;
    Button btnViewCart, btnAddCart;
    ImageView add, substract;
    TokenManager tokenManager;
    int itemTypeID = 0, qty = 0, itemID;
    CartService service;
    ItemService itemService;
    Call<Cart> callCart;
    Call<ItemTypeList> typeListCall;
    Call<ItemList> itemListCall;
    TextView itemTypeTitle, itemNameTitle, qtyTitle;
    private static final String TAG = "StockOutActivity";
    AlertDialog dialog, dialog2;
    EditText remarks;


    public void init(){
        layoutItemType = findViewById(R.id.type_layout_out);
        layoutItemName = findViewById(R.id.name_layout_out);
        btnAddCart = findViewById(R.id.btn_add_cart);
        btnViewCart = findViewById(R.id.btn_view_cart);
        add = findViewById(R.id.add_out);
        substract = findViewById(R.id.substract_out);
        itemTypeTitle = findViewById(R.id.item_type_title_out);
        itemNameTitle = findViewById(R.id.item_name_title_out);
        qtyTitle = findViewById(R.id.qty_title_out);
        remarks = findViewById(R.id.remarks_out);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockout);
        init();
        tokenManager = TokenManager.getInstance(getSharedPreferences("preferences", MODE_PRIVATE));
        service = RetrofitBuilder.getRetrofit().create(CartService.class);
        itemService = RetrofitBuilder.getRetrofit().create(ItemService.class);
        layoutItemType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeListCall = itemService.getItemTypes("Bearer " + tokenManager.getToken().getAccessToken());
                typeListCall.enqueue(new Callback<ItemTypeList>() {
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(StockOutActivity.this);
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
                        dialog = builder.create();
                        dialog.show();
                    }

                    @Override
                    public void onFailure(Call<ItemTypeList> call, Throwable t) {
                        Toast.makeText(StockOutActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        layoutItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Item Type ID: " + itemTypeID);
                if(itemTypeID == 0){
                    Toast.makeText(StockOutActivity.this, "Please Select Item Type First", Toast.LENGTH_LONG).show();
                }else{
                    itemListCall = itemService.getTypesItem("Bearer " + tokenManager.getToken().getAccessToken(), itemTypeID);
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
                            AlertDialog.Builder itemNameBuilder = new AlertDialog.Builder(StockOutActivity.this);
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

                            dialog2 = itemNameBuilder.create();
                            dialog2.show();

                        }

                        @Override
                        public void onFailure(Call<ItemList> call, Throwable t) {
                            Toast.makeText(StockOutActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                            Log.d(TAG, "onFailure: " + t.getMessage());
                        }
                    });
                }
            }
        });

        substract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qty <= 1){
                    Toast.makeText(StockOutActivity.this, "Minimal Quantity " + qty, Toast.LENGTH_LONG).show();
                }else{
                    qty--;
                    Toast.makeText(StockOutActivity.this, "Qty: " + qty, Toast.LENGTH_LONG).show();
                }
                qtyTitle.setText(Integer.toString(qty));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty++;
                Toast.makeText(StockOutActivity.this, "Qty: " + qty, Toast.LENGTH_LONG).show();
                qtyTitle.setText(Integer.toString(qty));
            }
        });

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCart = service.createCart("Bearer " + tokenManager.getToken().getAccessToken(), itemID, qty, remarks.getText().toString());
                callCart.enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {
                        Toast.makeText(StockOutActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(getIntent());
                    }

                    @Override
                    public void onFailure(Call<Cart> call, Throwable t) {
                        Toast.makeText(StockOutActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        });

        btnViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StockOutActivity.this, CartActivity.class);
                startActivity(i);
            }
        });
    }
}
