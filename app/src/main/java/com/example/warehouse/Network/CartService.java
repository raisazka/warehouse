package com.example.warehouse.Network;

import com.example.warehouse.Model.Cart;
import com.example.warehouse.Model.CartList;
import com.example.warehouse.Model.StaffList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface CartService {

    @POST("cart")
    @FormUrlEncoded
    Call<Cart> createCart(@Header("Authorization") String token,
                          @Field("item_id") String item_id,
                          @Field("qty") int qty,
                          @Field("remarks") String remarks);

    @POST("cart-in")
    @FormUrlEncoded
    Call<Cart> createCartIn(@Header("Authorization") String token,
                          @Field("item_id") String item_id,
                          @Field("qty") int qty,
                          @Field("remarks") String remarks);

    @GET("cart")
    Call<CartList> getCart(@Header("Authorization") String token);

    @GET("cart-in")
    Call<CartList> getCartIn(@Header("Authorization") String token);

    @DELETE("cart/{id}")
    Call<Cart> deleteCart(@Header("Authorization") String token, @Path("id") int id);

    @DELETE("cart-in/{id}")
    Call<Cart> deleteCartIn(@Header("Authorization") String token, @Path("id") int id);

    @GET("staff")
    Call<StaffList> getStaff(@Header("Authorization") String token, @Query("position") String position);
}
