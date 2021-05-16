package com.deepanshu.mymovieapp.ui.activity.ui.splashScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.BoringLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.deepanshu.mymovieapp.R;
import com.deepanshu.mymovieapp.mvp.login.ILoginView;
import com.deepanshu.mymovieapp.mvp.login.LoginPresenterImpl;
import com.deepanshu.mymovieapp.prefs.SharedPreferencesFactory;
import com.deepanshu.mymovieapp.ui.activity.BaseActivity;
import com.deepanshu.mymovieapp.ui.activity.MainDashBoardActivity;
import com.deepanshu.mymovieapp.ui.activity.ui.composeEmail.ComposeEmailActivity;
import com.deepanshu.mymovieapp.ui.activity.ui.login.LoginActivity;
import com.deepanshu.mymovieapp.util.AppUtil;
import com.deepanshu.mymovieapp.util.PrefUtil;
import com.deepanshu.retrofit.errors.ApiError;
import com.deepanshu.retrofit.modules.responseModule.login.LoginResponse;

import static com.deepanshu.mymovieapp.util.AppUtil.SUCESS_RESPONSE;

public class SplashScreen extends BaseActivity {
    private ProgressBar progressBar;
    private EditText edtUserName,edtUserPassword;
    private Button loginBtn;
    private LoginPresenterImpl loginPresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getHeaderTitle() {
        return "";
    }

    @Override
    public int getLayoutByID() {
        return  R.layout.splash_screen_activity;
    }

    @Override
    public void getViewById() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Boolean isuserAlreadyLoggedIn =  chekcUserIsAlreadyLoggedInorNot();
                if(isuserAlreadyLoggedIn){
                    startActivityClearTop(MainDashBoardActivity.class, null, null);
                    finish();
                }else{
                    startActivityClearAll(LoginActivity.class,null,null);
                    finish();
                }

            }
        },2000);


    }

    private Boolean chekcUserIsAlreadyLoggedInorNot() {
        SharedPreferencesFactory sharedPreferencesFactory = SharedPreferencesFactory.getInstance(this);
        SharedPreferences prefs = sharedPreferencesFactory.getSharedPreferences(MODE_PRIVATE);
        String accesstoken = sharedPreferencesFactory.getPreferenceValue(PrefUtil.PREFS_ACCESS_TOKEN);
        if(accesstoken!=null && !accesstoken.trim().equalsIgnoreCase("")) {
            return true;
        }
        else{
            return  false;
        }
    }


    @Override
    public void hideToolBarnextValue() {

    }

    @Override
    public void updateToolBarNextValue(String nextValue) {

    }

    @Override
    public void updateToolBarBackValue(String backTxtValue) {

    }

    @Override
    public void hadleToolBarNextValue(TextView textView) {

    }

    @Override
    public void handleToolBarBackValue(TextView textView) {

    }

    @Override
    public void onNetworkChangeStatus(boolean networkStatus, String msg) {

    }

    @Override
    public void showProgressBar() {

    }


    @Override
    public void hideProgressbar() {

    }

    @Override
    public void hideToolbarNext() {

    }

    @Override
    public void showToolbarNext() {

    }

    @Override
    public void manageToolBar() {

    }

    @Override
    public void onNetworkChange(boolean networkStatus, String msg) {

    }


}