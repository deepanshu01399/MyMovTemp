package com.deepanshu.mymovieapp.mvp.login;

import com.deepanshu.retrofit.errors.ApiError;
import com.deepanshu.retrofit.errors.ErrorUtil;
import com.deepanshu.retrofit.modules.responseModule.login.LoginResponse;

public interface ILoginView {
    String getUserName();
    String getPassword();
    String getDeviceType();
    String getFirebaseID();
    String getAppVersion();
    void onSuccessApi(LoginResponse loginResponse);
    void OnGettingError(ApiError error);
    void updateSharedPrefValues();
    void showProgressBar();
    void hideProgressBar();

}
