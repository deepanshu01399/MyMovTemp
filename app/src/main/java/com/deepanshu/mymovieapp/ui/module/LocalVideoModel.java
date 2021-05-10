package com.deepanshu.mymovieapp.ui.module;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class LocalVideoModel implements Parcelable {
    String videoTitle;

    public LocalVideoModel(String videoTitle, String videoDuration, String videoSize, Uri videoUri, int thumbnail) {
        this.videoTitle = videoTitle;
        this.videoDuration = videoDuration;
        this.videoSize = videoSize;
        this.videoUri = videoUri;
        this.thumbnail = thumbnail;
    }

    String videoDuration;
    String videoSize;
    Uri videoUri;
    int  thumbnail;

    protected LocalVideoModel(Parcel in) {
        videoTitle = in.readString();
        videoDuration = in.readString();
        videoSize = in.readString();
        videoUri = in.readParcelable(Uri.class.getClassLoader());
        thumbnail = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(videoTitle);
        dest.writeString(videoDuration);
        dest.writeString(videoSize);
        dest.writeParcelable(videoUri, flags);
        dest.writeInt(thumbnail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LocalVideoModel> CREATOR = new Creator<LocalVideoModel>() {
        @Override
        public LocalVideoModel createFromParcel(Parcel in) {
            return new LocalVideoModel(in);
        }

        @Override
        public LocalVideoModel[] newArray(int size) {
            return new LocalVideoModel[size];
        }
    };

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(String videoSize) {
        this.videoSize = videoSize;
    }

    public Uri getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(Uri videoUri) {
        this.videoUri = videoUri;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
