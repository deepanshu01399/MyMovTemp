package com.deepanshu.retrofit.errors;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ErrorUtil {
    // This is a utility class for API error handling.
    public static ApiError parseError(Retrofit retrofit, Response<?> response) {

        Converter<ResponseBody, ApiError> converter = retrofit.responseBodyConverter(ApiError.class, new Annotation[0]);
        ApiError apiError;

        try {
            apiError = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ApiError();
        }
        return apiError;
    }


}
