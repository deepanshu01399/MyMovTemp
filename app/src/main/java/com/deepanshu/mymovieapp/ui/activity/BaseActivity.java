package com.deepanshu.mymovieapp.ui.activity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.deepanshu.mymovieapp.R;
import com.deepanshu.mymovieapp.interfaces.IEssentialFeatures;
import com.deepanshu.mymovieapp.ui.custom.ColoredSnackbar;
import com.google.android.material.snackbar.Snackbar;

public abstract class BaseActivity extends AppCompatActivity implements IEssentialFeatures {
    private ComponentName componentName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.light_sky_blue));
        }

        ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            componentName = am.getAppTasks().get(0).getTaskInfo().topActivity;
            Log.e("ComponentName ",componentName.getShortClassName());
        } else {
            //noinspection deprecation
            componentName = am.getRunningTasks(1).get(0).topActivity;
            Log.e("ComponentName1 ",componentName.getShortClassName());

        }

        if (getLayoutByID() != 0)
            setContentView(getLayoutByID());
        manageToolBar();
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

    public void startActivity(Class<?> className, Bundle bundle, String action) {
        Intent i = new Intent(this, className);
        if (bundle != null)
            i.putExtras(bundle);

        if (action != null)
            i.setAction(action);
        startActivity(i);
    }

    protected void startActivity(Class<?> className, Bundle bundle) {
        Intent i = new Intent(this, className);
        if (bundle != null)
            i.putExtras(bundle);
        startActivity(i);
    }



    public void openDashboardScreenFromBaseActivity(Bundle bundle) {
        // TODO Here we will write a code to navigate the control to the Dashboard screen.
        startActivityClearTop(MainDashBoardActivity.class, null, null);
    }

    public void toolbarTitleAnimation(TextView title) {
        Animation myAnimation = AnimationUtils.loadAnimation(this, R.anim.toolbar_title_animation);
        title.startAnimation(myAnimation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setHeaderTitle(getHeaderTitle());
    }

    }
