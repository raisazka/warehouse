package com.example.warehouse.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InstallerList {

    @SerializedName("installers")
    private ArrayList<Installer> installers;

    public InstallerList(ArrayList<Installer> installers) {
        this.installers = installers;
    }

    public ArrayList<Installer> getInstallers() {
        return installers;
    }
}
