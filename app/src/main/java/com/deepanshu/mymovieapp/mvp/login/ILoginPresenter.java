package com.deepanshu.mymovieapp.mvp.login;

import com.deepanshu.retrofit.modules.responseModule.login.LoginResponse;

public interface ILoginPresenter {
    public String hitLoginApi();
    public String updateLoginResponse();
    public void clearDB();
    public void insertIntoDb(LoginResponse loginResponse);


}
