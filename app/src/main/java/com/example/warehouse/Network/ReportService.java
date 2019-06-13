package com.example.warehouse.Network;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ReportService {

    @GET
    @Streaming
    Call<ResponseBody> getReport(@Url String url);

    @GET("adjustment-report")
    @Streaming
    Call<ResponseBody> getAdjustmentReport();

}
