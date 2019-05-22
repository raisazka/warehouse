package com.example.warehouse;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehouse.Adapter.CartAdapter;
import com.example.warehouse.Model.Cart;
import com.example.warehouse.Model.CartList;
import com.example.warehouse.Model.Installer;
import com.example.warehouse.Model.InstallerList;
import com.example.warehouse.Model.Worker;
import com.example.warehouse.Model.WorkerList;
import com.example.warehouse.Network.CartService;
import com.example.warehouse.Network.RetrofitBuilder;
import com.example.warehouse.TokenManager.TokenManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    CartService service;
    Call<WorkerList> workerListCall;
    Call<InstallerList> installerListCall;
    Call<CartList> cartListCall;
    RelativeLayout installerLayout, workerLayout;
    TokenManager tokenManager;
    AlertDialog dialog, dialog2;
    TextView workersTitle, installerTitle;
    RecyclerView recyclerView;
    CartAdapter adapter;
    int workersId, installerId;
    private static final String TAG = "CartActivity";

    void init(){
        workersTitle = findViewById(R.id.workers_title);
        installerTitle = findViewById(R.id.installer_title);
        installerLayout = findViewById(R.id.installer_layout);
        workerLayout = findViewById(R.id.workers_layout);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();
        tokenManager = TokenManager.getInstance(getSharedPreferences("preferences", MODE_PRIVATE));
        service = RetrofitBuilder.getRetrofit().create(CartService.class);
        cartListCall = service.getCart("Bearer " + tokenManager.getToken().getAccessToken());
        cartListCall.enqueue(new Callback<CartList>() {
            @Override
            public void onResponse(Call<CartList> call, Response<CartList> response) {
                generateData(response.body().getCarts());
            }

            @Override
            public void onFailure(Call<CartList> call, Throwable t) {
                Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        workerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workerListCall = service.getWorkers("Bearer " + tokenManager.getToken().getAccessToken());
                workerListCall.enqueue(new Callback<WorkerList>() {
                    @Override
                    public void onResponse(Call<WorkerList> call, Response<WorkerList> response) {
                        final ArrayList<String> workersList = new ArrayList<>();
                        final ArrayList<Integer> workersID = new ArrayList<>();
                        for(Worker worker: response.body().getWorkers()){
                            workersList.add(worker.getWorkerName());
                            workersID.add(worker.getWorkerId());
                        }
                        String workersName [] = new String[workersList.size()];
                        workersName = workersList.toArray(workersName);
                        AlertDialog.Builder workerNameBuilder = new AlertDialog.Builder(CartActivity.this);
                        workerNameBuilder.setTitle("Choose Worker");
                        workerNameBuilder.setSingleChoiceItems(workersName, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                workersTitle.setText(workersList.get(which));
                                workersId = workersID.get(which);
                                dialog.dismiss();
                            }
                        });

                        workerNameBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        dialog = workerNameBuilder.create();
                        dialog.show();
                    }

                    @Override
                    public void onFailure(Call<WorkerList> call, Throwable t) {
                        Toast.makeText(CartActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        });
        installerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                installerListCall = service.getInstallers("Bearer " + tokenManager.getToken().getAccessToken());
                installerListCall.enqueue(new Callback<InstallerList>() {
                    @Override
                    public void onResponse(Call<InstallerList> call, Response<InstallerList> response) {
                        final ArrayList<String> installerList = new ArrayList<>();
                        final ArrayList<Integer> installerID = new ArrayList<>();
                        for(Installer installer: response.body().getInstallers()){
                            installerList.add(installer.getInstallerName());
                            installerID.add(installer.getId());
                        }
                        String installersName [] = new String[installerList.size()];
                        installersName = installerList.toArray(installersName);
                        AlertDialog.Builder workerNameBuilder = new AlertDialog.Builder(CartActivity.this);
                        workerNameBuilder.setTitle("Choose Worker");
                        workerNameBuilder.setSingleChoiceItems(installersName, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                installerTitle.setText(installerList.get(which));
                                installerId = installerID.get(which);
                                dialog.dismiss();
                            }
                        });

                        workerNameBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        dialog2 = workerNameBuilder.create();
                        dialog2.show();
                    }

                    @Override
                    public void onFailure(Call<InstallerList> call, Throwable t) {
                        Toast.makeText(CartActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        });

    }
        public void generateData(ArrayList<Cart> carts){
            recyclerView = findViewById(R.id.cart_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new CartAdapter(carts);
            recyclerView.setAdapter(adapter);
        }
}
