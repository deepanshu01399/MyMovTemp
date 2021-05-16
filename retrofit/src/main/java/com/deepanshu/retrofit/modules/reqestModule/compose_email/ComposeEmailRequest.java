package com.deepanshu.retrofit.modules.reqestModule.compose_email;

import com.deepanshu.retrofit.modules.BaseRequest;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ComposeEmailRequest extends BaseRequest<ComposeEmailRequestObject> implements Serializable {

    @SerializedName("request")
    private ComposeEmailRequestObject emailAddressRequestObject;

    public ComposeEmailRequestObject getEmailAddressRequestObject() {
        return emailAddressRequestObject;
    }

    public void setEmailAddressRequestObject(ComposeEmailRequestObject emailAddressRequestObject) {
        this.emailAddressRequestObject = emailAddressRequestObject;
    }
}
