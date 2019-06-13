package com.example.warehouse.Network;
import com.example.warehouse.Model.Item;
import com.example.warehouse.Model.ItemList;
import com.example.warehouse.Model.ItemTypeList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ItemService {

    @GET("items/warehouse/{warehouseId}")
    Call<ItemList> getItems(@Header("Authorization") String token, @Path("warehouseId") int warehouseId);

    @GET("items/{id}")
    Call<Item> showItem(@Header("Authorization") String token, @Path("id") String id);

    @PATCH("items/{id}")
    @FormUrlEncoded
    Call<Item> updateStock(@Header("Authorization") String token, @Path("id") String id,
                           @Field("qty") int qty);

    @GET("item-types")
    Call<ItemTypeList> getItemTypes(@Header("Authorization") String token);

    @GET("item/{warehouseId}")
    Call<ItemList> getTypesItem(@Header("Authorization") String token, @Path("warehouseId") int warehouseId, @Query("typeId") int typeId);

}
