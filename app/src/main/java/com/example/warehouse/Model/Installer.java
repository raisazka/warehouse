package com.example.warehouse.Model;

import com.google.gson.annotations.SerializedName;

public class Installer {

    @SerializedName("id")
    private int id;
    @SerializedName("installer_name")
    private String installerName;
    @SerializedName("installer_pst")
    private String installerPst;

    public int getId() {
        return id;
    }

    public String getInstallerName() {
        return installerName;
    }

    public String getInstallerPst() {
        return installerPst;
    }

}
