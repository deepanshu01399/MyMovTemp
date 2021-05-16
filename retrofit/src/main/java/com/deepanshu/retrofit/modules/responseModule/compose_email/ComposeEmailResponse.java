package com.deepanshu.retrofit.modules.responseModule.compose_email;

import com.deepanshu.retrofit.modules.BaseNetworkResponse;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ComposeEmailResponse  extends BaseNetworkResponse<EmailModule> implements Serializable {
    @SerializedName("module")
    private EmailModule emailModule;

    public EmailModule getEmailModule() {
        return emailModule;
    }

    public void setEmailModule(EmailModule emailModule) {
        this.emailModule = emailModule;
    }
}
