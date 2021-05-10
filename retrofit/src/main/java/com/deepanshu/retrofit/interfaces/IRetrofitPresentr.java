package com.deepanshu.retrofit.interfaces;

import retrofit2.Call;
import retrofit2.Retrofit;

public interface IRetrofitPresentr<T> {

    /**
     * This method is used to get a Retrofit builder object
     * @param baseUrl is the base url {@IRetrofitCallback.
     */
    public Retrofit getRetrofitBuilder(String baseUrl);



    /**
     * A request queue reference is returned, if already created otherwise creates a new one by using this method.
     * @return
     */
//    public RequestQueue getVolleyRequestQueue();


    /** This method is helpful when filtering response data via API type search. You can cancel previous request if user request a new keyword or modified
     * @param tag is a String parameter to cancel all the requests from the request queue. This tag is associated at the time of request creation by using { @IVolleyNetworkPrentr.addRequestQueue() }.
     */
    public void cancelInProcessRequestsByTag(String tag);

    /**
     * This method is used to set { @IVolleyResponseCallback } object which will return response object.
     * @param callback is a response callback reference of class {@IVolleyResponseCallback}.
     */
    public void setRetrofitCallback(IRetrofitResponseCallback callback);

    /**
     * @param req is a request object
     * @param tag is the String identifier to identify a request. We can cancel a request by using this tag.
     * @param <T>
     */

    /**
     * This method will return IRetrofitContract instance. It will internally calling getRetrofitBuilder() and also using "http://192.168.0.232:8080/pineapple/" as base URL. So there is no need to get a Retrofit builder reference explicitly.
     */
    public IRetrofitContract getRetrofitContract(String baseUrl);


    public void executeRequest(Call<T> call, int REQUEST_CODE);
}
