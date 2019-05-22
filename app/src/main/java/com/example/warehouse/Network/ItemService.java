package com.example.warehouse.Network;
import com.example.warehouse.Model.ItemList;
import com.example.warehouse.Model.ItemTypeList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ItemService {

    @GET("items")
    Call<ItemList> getItems(@Header("Authorization") String token);

    @GET("item-types")
    Call<ItemTypeList> getItemTypes(@Header("Authorization") String token);

    @GET("item/{id}")
    Call<ItemList> getTypesItem(@Header("Authorization") String token, @Path("id") int id);

}
