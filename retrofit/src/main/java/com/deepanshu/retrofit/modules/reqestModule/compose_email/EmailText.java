package com.deepanshu.retrofit.modules.reqestModule.compose_email;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EmailText implements Serializable {
    @SerializedName("subject")
    private String  emailSubject;
    @SerializedName("body")
    private String emailBody;

    public EmailText(String emailSubject, String emailBody) {
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }
}
