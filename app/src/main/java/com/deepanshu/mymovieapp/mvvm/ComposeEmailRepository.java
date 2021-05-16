package com.deepanshu.mymovieapp.mvvm;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.deepanshu.retrofit.api.RetrofitFactory;
import com.deepanshu.retrofit.errors.ApiError;
import com.deepanshu.retrofit.interfaces.IRetrofitContract;
import com.deepanshu.retrofit.interfaces.IRetrofitResponseCallback;
import com.deepanshu.retrofit.modules.reqestModule.compose_email.ComposeEmailRequest;
import com.deepanshu.retrofit.modules.responseModule.compose_email.ComposeEmailResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

import static com.deepanshu.mymovieapp.util.AppUtil.BASE_URL;

public class ComposeEmailRepository {
    private static final int COMPOSE_API_HIT = 12;
    private MutableLiveData<ComposeEmailResponse> composeEmailResponseMutableLiveData =new MutableLiveData<>();
    private RetrofitFactory mRetrofactory;
    private Application application;

    public ComposeEmailRepository(Application application) {
        this.application = application;
    }

    public LiveData<ComposeEmailResponse> hitComposeEmail(String token, RequestBody composeEmailRequestBody, List<MultipartBody.Part> mulitattachments, Boolean actualHitorNot){
    mRetrofactory =RetrofitFactory.getInstance();
    mRetrofactory.setRetrofitCallback(new IRetrofitResponseCallback() {
        @Override
        public void onResponseReceived(int REQUEST_CODE, Object object) {
            try {
                composeEmailResponseMutableLiveData.setValue((ComposeEmailResponse) object);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onErrorReceived(ApiError apiError) {
            if(apiError.getMessage()!=null){
                Log.e("error",apiError.getMessage());
            }


        }
    });

    if(actualHitorNot && composeEmailRequestBody!=null){
        IRetrofitContract iRetrofitContract = mRetrofactory.getRetrofitContract(BASE_URL);
        Call<ComposeEmailResponse> composeEmailResponseCall = iRetrofitContract.hitComposeEmailApi(token,composeEmailRequestBody,mulitattachments);
        mRetrofactory.executeRequest(composeEmailResponseCall,COMPOSE_API_HIT);
    }
        return composeEmailResponseMutableLiveData;
    }
}
