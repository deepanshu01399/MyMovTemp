package com.deepanshu.retrofit.modules.responseModule.login;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginUser implements Serializable {

    @SerializedName("timezone")
    private String timeZone;
    @SerializedName("datef")
    private String dateFormat;
    @SerializedName("timef")
    private String timeFormat;
    @SerializedName("currency")
    private String currency;
    @SerializedName("type")
    private String type;
    @SerializedName("id")
    private String id;
    @SerializedName("factor_auth")
    private String factorAuth;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("expires_in")
    private int expiresIn;
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("refresh_token")
    private String refreshToken;
    @SerializedName("profile_image")
    private String profileImage;
    @SerializedName("is_superadmin_c")
    private String isSuperAdmin;
    @SerializedName("marketing_user_c")
    private String marketingUser;
    @SerializedName("UserType")
    private String UserType;
    @SerializedName("user_level_c")
    private String userLevel;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("title")
    private String title;
    @SerializedName("status")
    private String activeOrNotStatus;
    @SerializedName("department")
    private String department;
    @SerializedName("email")
    private String email;
    @SerializedName("primary_address")
    private String primaryAddress;
    @SerializedName("reply_to_address")
    private String replyToAddress;
    @SerializedName("phone_work")
    private String phoneWork;
    @SerializedName("phone_mobile")
    private String phoneMobile;
    @SerializedName("address_country")
    private String addressCountry;
    @SerializedName("address_state")
    private String addressState;
    @SerializedName("address_city")
    private String address_city;
    @SerializedName("isset_profile_image")
    private Boolean isSetProfileImage;
    @SerializedName("role")
    private ArrayList<String> roles;

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

    public Boolean getSetProfileImage() {
        return isSetProfileImage;
    }

    public void setSetProfileImage(Boolean setProfileImage) {
        isSetProfileImage = setProfileImage;
    }


    @SerializedName("use_real_names")
    private String showFullName;

    public String getShowFullName() {
        return showFullName;
    }

    public void setShowFullName(String showFullName) {
        this.showFullName = showFullName;
    }

    public Boolean getIsSetProfileImage() {
        return isSetProfileImage;
    }

    public void setIsSetProfileImage(Boolean isSetProfileImage) {
        this.isSetProfileImage = isSetProfileImage;
    }

    public String getActiveOrNotStatus() {
        return activeOrNotStatus;
    }

    public void setActiveOrNotStatus(String active_or_not_status) {
        this.activeOrNotStatus = active_or_not_status;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getFactorAuth() {
        return factorAuth;
    }

    public void setFactorAuth(String  factorAuth) {
        this.factorAuth = factorAuth;
    }
    public String getIsSuperAdmin() {
        return isSuperAdmin;
    }

    public void setIsSuperAdmin(String isSuperAdmin) {
        this.isSuperAdmin = isSuperAdmin;
    }

    public String getMarketingUser() {
        return marketingUser;
    }

    public String getPhoneWork() {
        return phoneWork;
    }

    public void setPhoneWork(String phoneWork) {
        this.phoneWork = phoneWork;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public void setMarketingUser(String marketingUser) {
        this.marketingUser = marketingUser;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(String primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public String getReplyToAddress() {
        return replyToAddress;
    }

    public void setReplyToAddress(String replyToAddress) {
        this.replyToAddress = replyToAddress;
    }

    public String getPhoneMobile() {
        return phoneMobile;
    }

    public void setPhoneMobile(String phoneMobile) {
        this.phoneMobile = phoneMobile;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }


    public String getAddress_city() {
        return address_city;
    }

    public void setAddress_city(String address_city) {
        this.address_city = address_city;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }




}
