package com.deepanshu.mymovieapp.ui.activity;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.deepanshu.mymovieapp.R;
import com.deepanshu.mymovieapp.interfaces.IEssentialFeatures;
import com.deepanshu.mymovieapp.ui.custom.ColoredSnackbar;
import com.google.android.material.snackbar.Snackbar;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity implements IEssentialFeatures {
    private ComponentName componentName;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.lightGreenblue));
        }

        ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            componentName = am.getAppTasks().get(0).getTaskInfo().topActivity;
            Log.e("ComponentName ",componentName.getShortClassName());
        } else {
            //noinspection deprecation
            componentName = am.getRunningTasks(1).get(0).topActivity;
            Log.e("ComponentName1 ",componentName.getShortClassName());

        }

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (getLayoutByID() != 0)
            setContentView(getLayoutByID());
        manageToolBar();
        getViewById();

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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void showSnackBarMessage(String message) {
        if (message != null && message.trim().length() > 0) {
            ColoredSnackbar.alert(Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)).show();
//                ColoredSnackbar.info(Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)).show();
//            ColoredSnackbar.dark(Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)).show();
        }
    }

    @Override
    public void showSnackbarAlert(String message) {
        if (message != null && message.trim().length() > 0) {
            ColoredSnackbar.alert(Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)).show();
//                ColoredSnackbar.info(Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)).show();
//            ColoredSnackbar.dark(Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)).show();
        }
    }

    @Override
    public void showSnackbarInfo(String message) {
        if (message != null && message.trim().length() > 0) {
            ColoredSnackbar.info(Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)).show();
        }
    }


    protected void startActivity(Class<?> className, String KEY, Bundle bundle) {
        Intent i = new Intent(this, className);
        if (KEY != null && bundle != null)
            i.putExtra(KEY, bundle);
        startActivity(i);
    }

    /**
     * Start an Activity with clearing all activities on top of it
     *
     * @param className
     */
    protected void startActivityClearTop(Class<?> className, String KEY, Bundle bundle) {
        Intent i = new Intent(this, className);
        if (bundle != null)
            i.putExtra(KEY, bundle);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    /**
     * Clearing current task and starts activity in new task
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

    protected void startActivityClearAll(Class<?> className, Bundle bundle) {
        Intent i = new Intent(this, className);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }


    private void showProgressDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(BaseActivity.this, R.layout.progress_layout);
        } else {
            builder = new AlertDialog.Builder(BaseActivity.this);
        }
//        RelativeLayout layout = new RelativeLayout(this);
//        progressBar = new ProgressBar(BaseActivity1.this,null,android.R.attr.progressBarStyleSmall);
//        progressBar.setIndeterminate(true);
//        progressBar.setVisibility(View.VISIBLE);
//        layout.setBackgroundColor(Color.parseColor("#000000"));
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
//        params.addRule(RelativeLayout.CENTER_IN_PARENT);
//        layout.addView(progressBar,params);
//        setContentView(layout);
        builder.show();
    }

    public void showProgress() {
//        progressDialog.setProgress(percentage);
        if (!isFinishing()) {
            //progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgress() {
//        progressDialog.setProgress(0);
        if (!isFinishing()) {
            //progressBar.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        //LogOutTimerUtil.startLogoutTimer(this, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        hideProgress();
        setHeaderTitle(getHeaderTitle());
    }
    public void hideSoftInputKeypad() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showSoftInputKeypad(EditText mEdtField) {
        InputMethodManager keyboard = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.showSoftInput(mEdtField, 0);
    }




    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }


    public String getAppVersion() {

        /*String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        int version = Build.VERSION.SDK_INT;
        String versionRelease = Build.VERSION.RELEASE;
        Log.i("ManuFacturer :", Build.MANUFACTURER);
        Log.i("Board : ", Build.BOARD);
        Log.i("Display : ", Build.DISPLAY);

        Log.i("TAG", "SERIAL: " + Build.SERIAL);
        Log.i("TAG","MODEL: " + Build.MODEL);
        Log.i("TAG","ID: " + Build.ID);
        Log.i("TAG","Manufacture: " + Build.MANUFACTURER);
        Log.i("TAG","brand: " + Build.BRAND);
        Log.i("TAG","type: " + Build.TYPE);
        Log.i("TAG","user: " + Build.USER);
        Log.i("TAG","BASE: " + Build.VERSION_CODES.BASE);
        Log.i("TAG","INCREMENTAL " + Build.VERSION.INCREMENTAL);
        Log.i("TAG","SDK  " + Build.VERSION.SDK);
        Log.i("TAG","BOARD: " + Build.BOARD);
        Log.i("TAG","BRAND " + Build.BRAND);
        Log.i("TAG","HOST " + Build.HOST);
        Log.i("TAG","FINGERPRINT: "+Build.FINGERPRINT);
        Log.i("TAG","Version Code: " + Build.VERSION.RELEASE);


        Log.e("MyActivity", "manufacturer " + manufacturer
                + " \n model " + model
                + " \n version " + version
                + " \n versionRelease " + versionRelease
        );*/

        /*<appversion>:<OS>:<device name / device description>
        ios1.0:ios:iphoneX
        android1.0:android:s9*/

//        9:4.9.59-16162958:9:Samsung SM-G960F:SM-G960F:samsung


        return getOS() + ":" + getReleaseVersion();

       /* return getReleaseVersion() +":"
                + getOS()+":"
                //+getAndroidVersion()+":"
                +getDeviceName();*//*+":"
                +getDeviceDescription();*/
    }

    public final static String KClientVersion = "1.0";

    public String getReleaseVersion() {
        //return BuildConfig.VERSION_NAME;
        return "1.0";
    }

    private String getAndroidVersion() {
        return Build.VERSION.RELEASE;
    }

    private String getOS() {
        //       return System.getProperty("os.version");
        return "ANDROID";
    }

    public String getDeviceName() {

        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;

        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private String getDeviceModel() {
        return Build.MODEL;
    }

    private String getDeviceDescription() {
        return Build.MANUFACTURER;
    }

    /*private String getDeviceId() {
        String deviceId = "";
        final TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return TODO;
        }
        if (mTelephony.getDeviceId() != null) {
            deviceId = mTelephony.getDeviceId();
        } else {
            deviceId = Settings.Secure.getString(getApplicationContext()
                    .getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }*/

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    private static final Handler disconnectHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            // todo
            return true;
        }
    });

    public void changeBackgroundDrawable(View layout, int drawableId) {
        final int sdk = Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            layout.setBackgroundDrawable(ContextCompat.getDrawable(this, drawableId));
        } else {
            layout.setBackground(ContextCompat.getDrawable(this, drawableId));
        }
    }

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
        } catch (Exception e) {

        }
    }
    /**
     * Get IP address from first non-localhost interface
     *
     * @param useIPv4 true=return ipv4, false=return ipv6
     * @return address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }

    public void showInProgressMessage() {
        ColoredSnackbar.info(Snackbar.make(findViewById(android.R.id.content), "In process...", Snackbar.LENGTH_LONG)).show();
    }




    public void toolbarTitleAnimation(TextView title) {
        Animation myAnimation = AnimationUtils.loadAnimation(this, R.anim.toolbar_title_animation);
        title.startAnimation(myAnimation);
    }

 /*   @Override
    public void onClientVersionUpdate(BaseNetworkResponse baseNetworkResponse) {
        if (baseNetworkResponse.getMessage() != null) {
            String convertedString = baseNetworkResponse.getMessage().replace("[#SPLIT]", ",");
            final String[] clientVersionData = convertedString.split(",");
            if (clientVersionData != null) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(BaseActivity1.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(BaseActivity1.this);
                }
//        builder.setIcon(R.drawable.app_icon);
                builder.setTitle("Info")
                        .setMessage(clientVersionData[0])
                        .setCancelable(false)
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Exit from app
                                dialog.dismiss();
                                if (!TextUtils.isEmpty(clientVersionData[1])) {
                                    *//*Intent playstoreIntent = new Intent(BaseActivity1.this, AppUpdateWebActivity.class);
                                    playstoreIntent.putExtra(ExtrasUtil.CLIENT_VERSION_UPDATE_URL, clientVersionData[1]);
                                    startActivity(playstoreIntent);
                                    finish();*//*
                                }
                            }
                        })
                        *//*.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Cancel
                                dialog.dismiss();
                            }
                        })*//*
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        } else {

        }
    }
*/
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

    @Override
    protected void onPause() {
        super.onPause();

    }

    private static final Runnable disconnectCallback = new Runnable() {
        @Override
        public void run() {
            // Perform any required operation on disconnect
        }
    };
    @Override
    public void onStop() {
        super.onStop();
        //stopDisconnectTimer();

    }
    public void openDashboardScreenFromBaseActivity(Bundle bundle) {
        // TODO Here we will write a code to navigate the control to the Dashboard screen.
        startActivityClearTop(MainDashBoardActivity.class, null, null);
    }


}
