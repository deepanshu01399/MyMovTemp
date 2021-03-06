package com.deepanshu.mymovieapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.deepanshu.mymovieapp.R;
import com.deepanshu.mymovieapp.interfaces.IEssentialFeatures;
import com.deepanshu.mymovieapp.ui.custom.ColoredSnackbar;
import com.google.android.material.snackbar.Snackbar;

public abstract class BaseActivity extends AppCompatActivity implements IEssentialFeatures {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        if (getLayoutByID() != 0)
            setContentView(getLayoutByID());

        mangerToolbar();
        getViewById();
        setHeaderTitle(getHeaderTitle());

    }

    @Override
    public void setHeaderTitle(String headerTitle) {
        if (!TextUtils.isEmpty(headerTitle)) {
            TextView txtHeader = findViewById(R.id.toolbar_title);
            if (txtHeader != null)
                txtHeader.setText(headerTitle);
        }
    }

    @Override
    public int getLayoutByID() {
        return 0;
    }

    @Override
    public void getViewById() {

    }

    @Override
    public void mangerToolbar() {

    }

    @Override
    public void hideToolBarnextValue() {

    }

    @Override
    public void updateToolBarNextValue(String nextValue) {

    }

    @Override
    public void updateToolBarBackValue(String backTxtValue) {

    }

    @Override
    public void hadleToolBarNextValue(TextView txtNext) {
        if (txtNext != null) {
            txtNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSnackBarMessage("You need to @override handleToolbarNextClick() in every Activity.");
                }
            });
        }

    }

    @Override
    public void handleToolBarBackValue(TextView txtBack) {
        if (txtBack != null) {
            txtBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSupportNavigateUp();
                }
            });
        }

    }

    @Override
    public void onNetworkChangeStatus(boolean networkStatus, String msg) {

    }

    @Override
    public void showSnackBarMessage(String message) {
        if (message != null && message.trim().length() > 0) {
            ColoredSnackbar.alert(Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)).show();
        }
    }

    @Override
    public void showSnackbarAlert(String message) {
        if (message != null && message.trim().length() > 0) {
            ColoredSnackbar.info(Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)).show();
        }
    }

    @Override
    public void showSnackbarInfo(String message) {
        if (message != null && message.trim().length() > 0) {
            ColoredSnackbar.dark(Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)).show();
        }
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressbar() {

    }

    protected void startActivityClearTop(Class<?> className, String KEY, Bundle bundle) {
        Intent i = new Intent(this, className);
        if (bundle != null)
            i.putExtra(KEY, bundle);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    /**
     * Clearing current task and starts activity in new task with some bundle values
     *
     * @param className
     */
    protected void startActivityClearAll(Class<?> className, String KEY, Bundle bundle) {
        Intent i = new Intent(this, className);
        if (bundle != null)
            i.putExtra(KEY, bundle);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    protected void startActivityClearAll(Class<?> className) {
        Intent i = new Intent(this, className);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

}
