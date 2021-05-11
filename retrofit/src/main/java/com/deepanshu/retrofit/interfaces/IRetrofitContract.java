package com.deepanshu.retrofit.interfaces;

import com.deepanshu.retrofit.modules.responseModule.login.LoginResponse;

import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface IRetrofitContract {

    /*

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("auth")
    Call<LoginResponse> signIn(@Header("AppVersion") String token, @Field("grant_type")String grant_type,@Field("username")String username,
                               @Field("password")String password,@Field("device_os")String device_os,
                               @Field("device_token")String firebaseID);
    Call<LoginResponse> signIn(@Header("ClientVersion") String clientVerson, @Field("grant_type")String grant_type, @Field("username")String username,
                               @Field("password")String password, @Field("device_os")String device_os,
                               @Field("device_token")String firebaseID);

    Observable<LoginResponse> doLogin(@Header("ClientVersion") String clientVerson, @Field("grant_type")String grant_type, @Field("username")String username,
                               @Field("password")String password, @Field("device_os")String device_os,
                               @Field("device_token")String firebaseID);

    @Headers("Content-Type:application/json")
    @POST("register")
    Call<SignUpResponse> register(@Header("Accept") String accept, @Header("ClientVersion") String clientVersion, @Header("AcceptLanguage") String language, @Body SignUpRequest signUpRequest);

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("access_token")
    Call<LoginResponse> login(@Header("ClientVersion") String clientVersion, @Field("grant_type") String grant_type, @Field("client_id") String clientId, @Field("client_secret") int clent_secret
            , @Field("username") String username, @Field("password") String password, @Field("device_token") String devicetoken, @Field("device_type") String deviceType);

    @Multipart
    @POST("V8/update-profile-image")
    Call<UpdateImageResponse> editProfileImageRequest(@Header("Authorization") String token, @Part("id") RequestBody id,
                                                      @Part("type") RequestBody type, @Part MultipartBody.Part image);
    @Multipart
    @POST("image")
    Observable<UploadProfileImageResponse> uploadImage(@Header("ClientVersion") String clientVerson, @Header("Authorization") String token, @Part MultipartBody.Part file);

    @Headers("Content-Type:application/json")
    @HTTP(method = "DELETE", path = "V8/module", hasBody = true)
    Call<DeleteModuleResponse> getDeleteModuleResponse(@Header("Authorization") String token, @Body DeleteModuleRequest requestObject);

    @Multipart
    @POST("V8/update-attachment")
    Call<UpladDocResponse> hitUploadDoc(@Header("Authorization") String token, @PartMap() Map<String, RequestBody> map, @Part MultipartBody.Part doc);

    @Headers("Content-Type:application/json")
    @GET("V8/module/relationships")
    Call<ActivitiesResponse> getActivitiesListData(@Header("Authorization") String token, @Query("moduleName") String moduleName, @Query("id") String id, @Query("relationship_name") String relationName, @Query("page_number") int pageNo, @Query("query_string") String searchKey);

*/
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("access_token")
    Call<LoginResponse> login(@Header("ClientVersion") String clientVersion, @Field("grant_type") String grant_type, @Field("client_id") String clientId, @Field("client_secret") int clent_secret
            , @Field("username") String username, @Field("password") String password, @Field("device_token") String devicetoken, @Field("device_type") String deviceType);


}
