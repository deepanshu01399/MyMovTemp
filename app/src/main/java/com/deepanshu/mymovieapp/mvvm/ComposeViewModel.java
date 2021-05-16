package com.deepanshu.mymovieapp.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deepanshu.retrofit.modules.responseModule.compose_email.ComposeEmailResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ComposeViewModel extends AndroidViewModel {
    private ComposeEmailRepository composeEmailRepository;
    private Application application;


    public ComposeViewModel(@NonNull Application application) {
        super(application);
        composeEmailRepository = new ComposeEmailRepository(application);
    }

    public LiveData<ComposeEmailResponse> hitComposeEmailApi(String token, RequestBody composeEmailRequest, List<MultipartBody.Part> multiattachemnts, Boolean actualhit) {
        return composeEmailRepository.hitComposeEmail(token, composeEmailRequest, multiattachemnts, actualhit);

    }

}
