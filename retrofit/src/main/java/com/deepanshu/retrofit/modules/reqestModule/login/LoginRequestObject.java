package com.deepanshu.retrofit.modules.reqestModule.login;

import com.google.gson.annotations.SerializedName;

public class LoginRequestObject {
    @SerializedName("grant_type")
    private String grantType;
    @SerializedName("client_id")
    private String clientId;
    @SerializedName("client_secret")
    private int clientSecret;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(int clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}