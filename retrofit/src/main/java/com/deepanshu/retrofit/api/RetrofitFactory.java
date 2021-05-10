package com.deepanshu.retrofit.api;


import com.deepanshu.retrofit.errors.ApiError;
import com.deepanshu.retrofit.errors.ErrorUtil;
import com.deepanshu.retrofit.interfaces.IRetrofitContract;
import com.deepanshu.retrofit.interfaces.IRetrofitPresentr;
import com.deepanshu.retrofit.interfaces.IRetrofitResponseCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This is a factory class which is responsible to provide most probably a singleton object of itself
 * and process a network API request.
 */

public class RetrofitFactory<T> implements IRetrofitPresentr<T> {

    private static RetrofitFactory mInstance;
    private IRetrofitResponseCallback mRetrofitCallback;
    public final String TAG = "Retrofactory";
    private static Retrofit mRetrofitBuilder;
    private int REQUEST_CODE = -1;
    private IRetrofitContract iRetrofitContract;
    private boolean BUILDER_RECREATION = false;

    private RetrofitFactory() {
        super();
    }

   /* Gson gson = new GsonBuilder()
            .setLenient()
            .create();*/
   // Gson gson = new GsonBuilder().setLenient().create();


    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {

                    Request request = chain.request();
                    okhttp3.Response response = chain.proceed(request);

                   /* Request.Builder request = chain.request()
                            .newBuilder()
                            .addHeader(RetroUtils.CLIENT_VERSION, RetroUtils.CLIENT_VERSION_VALUE);
                    okhttp3.Response response = chain.proceed(request.build());*/

                        //TODO handle it
                    if (response.code() == 401) {
                            //TODO
                        ApiError error = new ApiError();
                        error.setStatusCode(response.code());
                        error.setMessage(response.message());
                        // TODO parse error & navigate user readable error to respective implementation class like {@SignUpPresenterImpl.class}
                        mRetrofitCallback.onErrorReceived(error);
                        System.out.print("Unauthorized : " + response.body());
                        }
                        else if (response.code() == 500) {
                            //TODO
                            System.out.print("Retrofit server error : " + response.body());
                        } else if (response.code() == 404) {
                            //TODO
                            System.out.print("Retrofit not found error : " + response.body());
                        } else {
                            //TODO
                            System.out.print("Retrofit unknown error : " + response.body());
//                        startActivity(new Intent(this,UnknowErrorActivity.class)
                        }

                    return response;
                }
            })
            .build();

    public static synchronized RetrofitFactory getInstance() {
        //if (mInstance == null)
            mInstance = new RetrofitFactory();
        return mInstance;
    }

    //this is so you don't need to pass context each time
    /*public static synchronized RetrofitFactory getInstance()
    {
        if (null == mInstance)
        {
            throw new IllegalStateException(RetrofitFactory.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return mInstance;
    }*/

    @Override
    public Retrofit getRetrofitBuilder(String baseUrl) {
        if (mRetrofitBuilder == null) {
            mRetrofitBuilder = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mRetrofitBuilder;
    }

    @Override
    public IRetrofitContract getRetrofitContract(String baseUrl) {
        //iRetrofitContract =  getRetrofitBuilder(RetroUtils.BASE_URL_L).create(IRetrofitContract.class);
        iRetrofitContract = getRetrofitBuilder(baseUrl).create(IRetrofitContract.class);
        return iRetrofitContract;
    }

    @Override
    public void executeRequest(Call call, int request_code) {
        REQUEST_CODE = request_code;
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response != null && response.isSuccessful())
                {
                    System.out.print("Retrofit response: " + response.body());
                    mRetrofitCallback.onResponseReceived(REQUEST_CODE, response.body());
                }
                else
                {
                    // A 404, 500 will go here
                    System.out.print("Retrofit not successful: " + response.body());
                    // parse the response body …
                    ApiError error = ErrorUtil.parseError(mRetrofitBuilder,response);
                    // … and use it to show error information
                    // … or just log the issue like we’re doing :)
                    System.out.print("error message : "+error.getMessage());
                    // TODO parse error & navigate user readable error to respective implementation class like {@SignUpPresenterImpl.class}
                   mRetrofitCallback.onErrorReceived(error);
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                t.printStackTrace();
                    System.out.print("Retrofit onFailure()");
                if (call.isCanceled()) {
                    System.out.print("Retrofit request was cancelled");
                }



                /*try {
                    RetrofitErrorParse errorBundle = RetrofitErrorParse.adapt(t);
                    assertThat(errorBundle.getAppMessage(), is("Authorization exception"));
                } finally {
                    latch.countDown();
                }*/

                /*APIError error = new APIError();
                error.setStatusCode(-1);
                error.setMessage(t.getMessage());
                // TODO parse error & navigate user readable error to respective implementation class like {@SignUpPresenterImpl.class}
                mRetrofitCallback.onErrorReceived(error);*/

            }
        });
    }

    @Override
    public void cancelInProcessRequestsByTag(String tag) {
        // TODO Here we will cancel a request by its request tag
    }

    @Override
    public void setRetrofitCallback(IRetrofitResponseCallback callback)
    {
        mRetrofitCallback = callback;
    }
}
