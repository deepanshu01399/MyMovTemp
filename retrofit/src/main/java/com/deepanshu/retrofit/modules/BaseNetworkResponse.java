package com.deepanshu.retrofit.modules;

import com.google.gson.annotations.SerializedName;

/** A base response class for every response received from the network query
*/

public class BaseNetworkResponse<T> {

    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private T data;



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BaseNetworkResponse() {
    }
}
