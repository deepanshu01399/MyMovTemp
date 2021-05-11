package com.deepanshu.retrofit.modules.responseModule.login;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginResponseData  implements Serializable {

    @SerializedName("user")
    LoginUser loginUser;

    public LoginUser getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
    }
}
