package com.example.warehouse.Network;

import com.example.warehouse.Model.StockIn;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface StockInService {

    @POST("stock-in")
    @FormUrlEncoded
    Call<StockIn> createStockIn(@Header("Authorization") String token,
                                @Field("staff_id") int staffsId);

}
