package com.deepanshu.retrofit.modules.reqestModule.login;


import com.deepanshu.retrofit.modules.BaseRequest;

import java.io.Serializable;

public class LoginRequest extends BaseRequest<LoginRequestObject> implements Serializable {

    private LoginRequestObject request;

    public LoginRequestObject getLoginRequestObject() {
        return request;
    }

    public void setLoginRequestObject(LoginRequestObject loginRequestObject) {
        this.request = loginRequestObject;
    }
}
