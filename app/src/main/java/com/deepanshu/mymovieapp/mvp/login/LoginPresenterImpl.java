package com.deepanshu.mymovieapp.mvp.login;

import android.util.Log;
import android.widget.Toast;

import com.deepanshu.mymovieapp.util.AppUtil;
import com.deepanshu.mymovieapp.util.RequestUtil;
import com.deepanshu.retrofit.api.RetrofitFactory;
import com.deepanshu.retrofit.errors.ApiError;
import com.deepanshu.retrofit.interfaces.IRetrofitContract;
import com.deepanshu.retrofit.interfaces.IRetrofitResponseCallback;
import com.deepanshu.retrofit.modules.BaseNetworkResponse;
import com.deepanshu.retrofit.modules.reqestModule.login.LoginRequest;
import com.deepanshu.retrofit.modules.reqestModule.login.LoginRequestObject;
import com.deepanshu.retrofit.modules.responseModule.login.LoginResponse;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;

import retrofit2.Call;

import static com.deepanshu.mymovieapp.util.AppUtil.BASE_URL;
import static com.deepanshu.mymovieapp.util.AppUtil.client_secret;


public class LoginPresenterImpl implements ILoginPresenter {
    private static final String TAG = "LoginActivity";
    WeakReference<ILoginView> iLoginView;
    private RetrofitFactory<BaseNetworkResponse> mRetrofitFactory;


    public LoginPresenterImpl(ILoginView iLoginView) {
        //this.iLoginView = iLoginView;
        this.iLoginView = new WeakReference<ILoginView>(iLoginView);

    }


    @Override
    public String hitLoginApi() {
        if (iLoginView.get() != null) {
            ILoginView currentScreenView = iLoginView.get();
            String userName = currentScreenView.getUserName();
            String userPass = currentScreenView.getPassword();
            String client_id = AppUtil.client_id;
            String granttype = AppUtil.grant_type;
            LoginRequestObject loginRequestObject = new LoginRequestObject();
            loginRequestObject.setClientId(client_id);
            loginRequestObject.setGrantType(granttype);
            loginRequestObject.setClientSecret(AppUtil.client_secret);
            loginRequestObject.setUsername(userName);
            loginRequestObject.setPassword(userPass);
            String appVersionHeader = currentScreenView.getAppVersion();
            String deviceType = currentScreenView.getDeviceType();
            String fireBaseToken = currentScreenView.getFirebaseID();
            Log.e(TAG, "appVersion " + appVersionHeader + " deviceToken " + fireBaseToken + " deviceType : " + deviceType);

            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setLoginRequestObject(loginRequestObject);

            Gson gson = new Gson();
            Log.e(TAG, "LoginRequest is: " + gson.toJson(loginRequest));

            currentScreenView.showProgressBar();

            mRetrofitFactory = RetrofitFactory.getInstance();
            mRetrofitFactory.setRetrofitCallback(new IRetrofitResponseCallback<LoginResponse>() {

                @Override
                public void onResponseReceived(int REQUEST_CODE, LoginResponse loginResponse) {
                    currentScreenView.hideProgressBar();
                    currentScreenView.onSuccessApi(loginResponse);

                }

                @Override
                public void onErrorReceived(ApiError apiError) {
                    currentScreenView.hideProgressBar();
                    currentScreenView.OnGettingError(apiError);

                }
            });
            IRetrofitContract iRetrofitContract = mRetrofitFactory.getRetrofitContract(BASE_URL);
            Call<LoginResponse> call =iRetrofitContract.login(appVersionHeader,granttype,client_id,client_secret,userName,userPass,fireBaseToken,deviceType);
            mRetrofitFactory.executeRequest(call, RequestUtil.LOGIN_API_CODE);

        }
        return null;

    }



        @Override
        public String updateLoginResponse () {

            return null;
        }
    }
