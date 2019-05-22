package com.example.warehouse.Network;

import com.example.warehouse.Model.Cart;
import com.example.warehouse.Model.CartList;
import com.example.warehouse.Model.InstallerList;
import com.example.warehouse.Model.WorkerList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CartService {

    @POST("cart")
    @FormUrlEncoded
    Call<Cart> createCart(@Header("Authorization") String token,
                          @Field("item_id") int item_id,
                          @Field("qty") int qty,
                          @Field("remarks") String remarks);

    @GET("cart")
    Call<CartList> getCart(@Header("Authorization") String token);

    @GET("workers")
    Call<WorkerList> getWorkers(@Header("Authorization") String token);

    @GET("installers")
    Call<InstallerList> getInstallers(@Header("Authorization") String token);

}
