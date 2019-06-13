package com.example.warehouse.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StaffList {

    @SerializedName("staffs")
    private ArrayList<Staff> staffList;

    public StaffList(ArrayList<Staff> staffList) {
        this.staffList = staffList;
    }

    public ArrayList<Staff> getStaffList() {
        return staffList;
    }
}
