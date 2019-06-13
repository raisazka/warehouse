package com.example.warehouse.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehouse.InventoryActivity;
import com.example.warehouse.MenuActivity;
import com.example.warehouse.Model.Item;
import com.example.warehouse.Network.ItemService;
import com.example.warehouse.Network.RetrofitBuilder;
import com.example.warehouse.R;
import com.example.warehouse.ShowItemActivity;
import com.example.warehouse.TokenManager.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdjustDialogFragment extends DialogFragment {

    TextView qtyTitle;
    ImageView add, substract;
    int qty = 0;
    Call<Item> call;
    ItemService service;
    TokenManager tokenManager;
    ShowItemActivity activity, act;
    Context context;
    private static final String TAG = "AdjustDialogFragment";


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_adjust_stock, null);
        add = v.findViewById(R.id.add_adjust);
        substract = v.findViewById(R.id.substract_adjust);
        qtyTitle = v.findViewById(R.id.qty_title_adjust);
        activity = new ShowItemActivity();
        context = getActivity().getApplicationContext();
        act = (ShowItemActivity) getActivity();
        tokenManager = TokenManager.getInstance(getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE));
        service = RetrofitBuilder.getRetrofit().create(ItemService.class);
        substract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qty <= 1){
                    Toast.makeText(getActivity(), "Minimal Quantity " + qty, Toast.LENGTH_LONG).show();
                }else{
                    qty--;
                    Toast.makeText(getActivity(), "Qty: " + qty, Toast.LENGTH_LONG).show();
                }
                qtyTitle.setText(Integer.toString(qty));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty++;
                Toast.makeText(getActivity(), "Qty: " + qty, Toast.LENGTH_LONG).show();
                qtyTitle.setText(Integer.toString(qty));
            }
        });

        Log.d(TAG, "onCreateDialog: " + activity.id);

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                call = service.updateStock("Bearer " + tokenManager.getToken().getAccessToken(), activity.id, qty);
                call.enqueue(new Callback<Item>() {
                    @Override
                    public void onResponse(Call<Item> call, Response<Item> response) {
                        if(response.body().getCode() == 400){
                            Toast.makeText(act, "Error: " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(act, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context, MenuActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            act.finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Item> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.setView(v);
        return builder.create();
    }
}
