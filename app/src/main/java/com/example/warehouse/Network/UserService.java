package com.example.warehouse.Network;

import com.example.warehouse.Model.AccessToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {

    @POST("login")
    @FormUrlEncoded
    Call<AccessToken> login(@Field("email") String email,
                            @Field("password") String password);

}
