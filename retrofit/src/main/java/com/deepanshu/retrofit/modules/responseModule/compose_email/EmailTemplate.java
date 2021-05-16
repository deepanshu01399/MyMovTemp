package com.deepanshu.retrofit.modules.responseModule.compose_email;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EmailTemplate implements Serializable {
    @SerializedName("date_entered")
    private String dateEntered;
    @SerializedName("date_modified")
    private String dateModified;
    @SerializedName("modified_user_id")
    private String modifiedUserID;
    @SerializedName("created_by")
    private String createdBy;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("subject")
    private String subject;
    @SerializedName("body_html")
    private String bodyHtml;
    @SerializedName("assigned_user_id")
    private String assignedUserID;
    @SerializedName("text_only")
    private String textOnly;
    @SerializedName("type")
    private String type;
    @SerializedName("isFavourite")
    private String isFavourite;

    public String getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(String dateEntered) {
        this.dateEntered = dateEntered;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getModifiedUserID() {
        return modifiedUserID;
    }

    public void setModifiedUserID(String modifiedUserID) {
        this.modifiedUserID = modifiedUserID;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    public String getAssignedUserID() {
        return assignedUserID;
    }

    public void setAssignedUserID(String assignedUserID) {
        this.assignedUserID = assignedUserID;
    }

    public String getTextOnly() {
        return textOnly;
    }

    public void setTextOnly(String textOnly) {
        this.textOnly = textOnly;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(String isFavourite) {
        this.isFavourite = isFavourite;
    }
}
