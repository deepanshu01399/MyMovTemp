package com.deepanshu.retrofit.modules.reqestModule.compose_email;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EmailTo implements Serializable {
    @SerializedName("email")
    private String  toEmail;
    @SerializedName("name")
    private String toName;

    public EmailTo(String toName, String toEmail) {
        this.toEmail = toEmail;
        this.toName = toName;
    }

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

}
