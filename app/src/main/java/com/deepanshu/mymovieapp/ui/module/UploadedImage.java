package com.deepanshu.mymovieapp.ui.module;

import android.net.Uri;

public class UploadedImage {
    private Uri uri;
    private int requestCode ;

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }
}
