package com.deepanshu.retrofit.modules.reqestModule.compose_email;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EmailUser implements Serializable {
    @SerializedName("email")
    private String  toEmail;
    @SerializedName("name")
    private String toName;
    @SerializedName("id")
    private String EmailUserId;


    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getEmailUserId() {
        return EmailUserId;
    }

    public void setEmailUserId(String emailUserId) {
        EmailUserId = emailUserId;
    }

}
