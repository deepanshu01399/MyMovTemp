package com.deepanshu.mymovieapp.ui.module;

import android.os.Parcel;
import android.os.Parcelable;

public class CommentModule implements Parcelable {
    private String txtComment;
    private String txtCommentTime;


    public String getTxtComment() {
        return txtComment;
    }

    public void setTxtComment(String txtComment) {
        this.txtComment = txtComment;
    }

    public String getTxtCommentTime() {
        return txtCommentTime;
    }

    public void setTxtCommentTime(String txtCommentTime) {
        this.txtCommentTime = txtCommentTime;
    }

    public CommentModule(String txtComment, String txtCommentTime) {
        this.txtComment = txtComment;
        this.txtCommentTime = txtCommentTime;
    }

    protected CommentModule(Parcel in) {
        txtComment = in.readString();
        txtCommentTime = in.readString();
    }

    public static final Creator<CommentModule> CREATOR = new Creator<CommentModule>() {
        @Override
        public CommentModule createFromParcel(Parcel in) {
            return new CommentModule(in);
        }

        @Override
        public CommentModule[] newArray(int size) {
            return new CommentModule[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(txtComment);
        parcel.writeString(txtCommentTime);
    }
}
