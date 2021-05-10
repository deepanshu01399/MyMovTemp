package com.deepanshu.retrofit.utils;

public class RetroUtils {

    public static final int SUCCESS = 1000;
    public static final int SUCCESS_REQUEST=7000;
    public static final int SUCCESS_NO_DATA_FOUND=7001;
    public static final int SUCCESS_INVALID_REQUEST = 8061;
    public static final int SUCCESS_BUT_OTP_NOT_SENT = 8041;
    public static final int SUCCESS_BUT_INVALID_EMAIL = 8042;
    public static final int SUCCESS_BUT_SOMTING_MISSING = 8020;
    public static final int SUCCESS_BUT_RECORD_ALREADY_EXISTS = 8016;
    public static final int SUCCESS_BUT_RECORD_DELETE = 8061 ;
    public static final int TOKEN_EXPIRE_IN_MILISEC = 86400;
    public static final int UNAUTHORISE = 401;
    public static final String CONTENT_TYPE = "application/json";
    public static final String HEADER_TOKEN = "TOKEN";

    public static final String CRM_DEV = "https://stg1mayberrycrm.milnp.net/Api/index.php/";
    public static String APP_ENV = RetroUtils.CRM_DEV;

}
