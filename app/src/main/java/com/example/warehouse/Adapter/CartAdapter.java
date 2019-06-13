package com.example.warehouse.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.warehouse.CartActivity;
import com.example.warehouse.Model.Cart;
import com.example.warehouse.Network.CartService;
import com.example.warehouse.Network.RetrofitBuilder;
import com.example.warehouse.R;
import com.example.warehouse.TokenManager.TokenManager;
import com.example.warehouse.ViewHolder.CartViewHolder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    ArrayList<Cart> carts;
    Call<Cart> call;
    CartService service;
    TokenManager tokenManager;
    private Context context;
    SharedPreferences prefs;

    public CartAdapter(ArrayList<Cart> carts) {
        this.carts = carts;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_cart, viewGroup,false);
        context = viewGroup.getContext();
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder cartViewHolder, final int i) {
        cartViewHolder.name.setText(carts.get(i).getName());
        cartViewHolder.qty.setText("Qty: " + carts.get(i).getQty());
        cartViewHolder.remarks.setText(carts.get(i).getRemarks());
        service = RetrofitBuilder.getRetrofit().create(CartService.class);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Log.d("CartAdapter", "onBindViewHolder: " + prefs.getString("stock-type", null));
        tokenManager = TokenManager.getInstance(context.getSharedPreferences("preferences", Context.MODE_PRIVATE));
        cartViewHolder.btnDelete.setOnClickListener(v -> {
            if(prefs.getString("stock-type", null).equals("stock-out")){
                call = service.deleteCart("Bearer " + tokenManager.getToken().getAccessToken(), carts.get(i).getId());
                call.enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        ((CartActivity)context).finish();
                        context.startActivity(((CartActivity) context).getIntent());
                    }

                    @Override
                    public void onFailure(Call<Cart> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                call = service.deleteCartIn("Bearer " + tokenManager.getToken().getAccessToken(), carts.get(i).getId());
                call.enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        ((CartActivity)context).finish();
                        context.startActivity(((CartActivity) context).getIntent());
                    }

                    @Override
                    public void onFailure(Call<Cart> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }
}
