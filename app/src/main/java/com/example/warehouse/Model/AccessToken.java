package com.example.warehouse.Model;

import com.google.gson.annotations.SerializedName;

public class AccessToken {

    @SerializedName("code")
    private int code;
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("expired_at")
    private String expiresAt;
    @SerializedName("role")
    private String role;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getRole() {
        return role;
    }
}
