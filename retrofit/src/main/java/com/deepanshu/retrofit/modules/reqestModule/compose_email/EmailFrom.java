package com.deepanshu.retrofit.modules.reqestModule.compose_email;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EmailFrom implements Serializable {
    @SerializedName("email")
    private String  fromEmail;

    @SerializedName("id")
    private String fromId;

    public EmailFrom(String fromEmail, String fromId) {
        this.fromEmail = fromEmail;
        this.fromId = fromId;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }
}
