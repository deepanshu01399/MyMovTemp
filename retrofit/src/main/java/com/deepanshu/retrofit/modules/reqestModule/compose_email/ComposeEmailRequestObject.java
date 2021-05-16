package com.deepanshu.retrofit.modules.reqestModule.compose_email;

import com.deepanshu.retrofit.modules.responseModule.compose_email.EmailModule;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ComposeEmailRequestObject implements Serializable {

    @SerializedName("user")
    private EmailUser emailUser;
    @SerializedName("module")
    private EmailModule emailModule;
    @SerializedName("from")
    private EmailFrom emailFrom;
    @SerializedName("to")
    private List<EmailTo> emailToList;
    @SerializedName("cc")
    private List<String> emailCcList;
    @SerializedName("bcc")
    private List<String> emailBccList;
    @SerializedName("email_text")
    private EmailText emailText;


    public EmailText getEmailText() {
        return emailText;
    }

    public void setEmailText(EmailText emailText) {
        this.emailText = emailText;
    }


    public EmailUser getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(EmailUser emailUser) {
        this.emailUser = emailUser;
    }


    public EmailFrom getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(EmailFrom emailFrom) {
        this.emailFrom = emailFrom;
    }

    public List<EmailTo> getEmailToList() {
        return emailToList;
    }

    public void setEmailToList(List<EmailTo> emailToList) {
        this.emailToList = emailToList;
    }

    public List<String> getEmailCcList() {
        return emailCcList;
    }

    public void setEmailCcList(List<String> emailCcList) {
        this.emailCcList = emailCcList;
    }

    public List<String> getEmailBccList() {
        return emailBccList;
    }

    public void setEmailBccList(List<String> emailBccList) {
        this.emailBccList = emailBccList;
    }

    public EmailModule getEmailModule() {
        return emailModule;
    }

    public void setEmailModule(EmailModule emailModule) {
        this.emailModule = emailModule;
    }


}
