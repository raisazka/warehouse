package com.example.warehouse.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WorkerList {

    @SerializedName("workers")
    private ArrayList<Worker> workers;

    public WorkerList(ArrayList<Worker> workers) {
        this.workers = workers;
    }

    public ArrayList<Worker> getWorkers() {
        return workers;
    }
}
