package com.example.warehouse.Model;

import com.google.gson.annotations.SerializedName;

public class Worker {

    @SerializedName("id")
    private int workerId;
    @SerializedName("worker_name")
    private String workerName;
    @SerializedName("worker_pst")
    private String workerPst;

    public int getWorkerId() {
        return workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public String getWorkerPst() {
        return workerPst;
    }
}
