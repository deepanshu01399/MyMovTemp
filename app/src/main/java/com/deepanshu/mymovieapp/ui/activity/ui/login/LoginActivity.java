package com.deepanshu.mymovieapp.ui.activity.ui.login;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.deepanshu.mymovieapp.util.AppUtil;
import com.deepanshu.mymovieapp.util.PrefUtil;
import com.deepanshu.retrofit.errors.ApiError;
import com.deepanshu.retrofit.modules.responseModule.login.LoginResponse;

public class LoginActivity extends BaseActivity implements ILoginView, View.OnClickListener {
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
        return "LoginActivity";
    }

    @Override
    public int getLayoutByID() {
        return  R.layout.login_activity;
    }

    @Override
    public void getViewById() {

        progressBar = findViewById(R.id.progress_bar);
        edtUserName = findViewById(R.id.edtUsername);
        edtUserPassword = findViewById(R.id.edtPassword);
        loginBtn = findViewById(R.id.loginbtn);
        loginPresenterImpl = new LoginPresenterImpl(this);
        initClickListner();
        Boolean isuserAlreadyLoggedIn =  chekcUserIsAlreadyLoggedInorNot();
        if(isuserAlreadyLoggedIn){
            startActivityClearTop(MainDashBoardActivity.class, null, null);
            finish();
        }

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

    private void initClickListner() {
        loginBtn.setOnClickListener(this);
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
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);

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

    @Override
    public String getUserName() {
        return edtUserName.getText().toString();
    }

    @Override
    public String getPassword() {
        return edtUserPassword.getText().toString();
    }

    @Override
    public String getDeviceType() {
        return AppUtil.DEVICE_TYPE;
    }

    @Override
    public String getFirebaseID() {
        return "12345";//dummy
    }

    @Override
    public void onSuccessApi(LoginResponse loginResponse) {
        SharedPreferencesFactory sharedPreferencesFactory = SharedPreferencesFactory.getInstance(this);
        SharedPreferences prefs = sharedPreferencesFactory.getSharedPreferences(MODE_PRIVATE);
        sharedPreferencesFactory.writePreferenceValue(PrefUtil.PREFS_ACCESS_TOKEN,(loginResponse.getData().getLoginUser().getTokenType())+" "+(loginResponse.getData().getLoginUser().getAccessToken()));

        Toast.makeText(this,loginResponse.getStatus()+" "+ loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
        startActivityClearTop(MainDashBoardActivity.class, null, null);
        finish();

    }

    @Override
    public void OnGettingError(ApiError error) {
        Toast.makeText(this, error.getStatusCode()+" "+error.getMessage(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void updateSharedPrefValues() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginbtn:
                loginPresenterImpl.hitLoginApi();
                break;
        }
    }
}