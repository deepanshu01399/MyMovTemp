package com.deepanshu.mymovieapp.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deepanshu.mymovieapp.BuildConfig;
import com.deepanshu.mymovieapp.R;
import com.deepanshu.mymovieapp.interfaces.FragmentChangeListener;
import com.deepanshu.mymovieapp.ui.custom.ColoredSnackbar;
import com.deepanshu.mymovieapp.ui.fragment.BaseFragment;
import com.deepanshu.mymovieapp.ui.fragment.DashBoardFragment;
import com.deepanshu.mymovieapp.ui.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public  class MainDashBoardActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, FragmentChangeListener, FragmentManager.OnBackStackChangedListener, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private BottomNavigationView bottomNavigationView;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TextView mToolbarTitle, txtUserName;
    private Menu menuItems;
    private RelativeLayout mainLayProfile,mainLayShareApk;
    private final DashBoardFragment dashboardFragment = new DashBoardFragment();
    private BaseFragment currentFragment = dashboardFragment;
    public FragmentManager mFragmentManager;
    public static String TAG = "MainDashBoard";
    boolean doubleTabBackButton = false;
    private Boolean isAbleToAddOnStack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//to remove autofill suggestion
            getWindow()
                    .getDecorView()
                    .setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS);
        }

    }

    public MainDashBoardActivity() {

    }

    @Override
    public String getHeaderTitle() {
        return "DashBoard";
    }

    @Override
    public int getLayoutByID() {
        return R.layout.activity_main;
    }

    @Override
    public void getViewById() {
        mFragmentManager = getSupportFragmentManager();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mainLayProfile = findViewById(R.id.mainLayProfile);
        mainLayShareApk = findViewById(R.id.mainLayShareApk);
        txtUserName = findViewById(R.id.txtUserName);

        setonClickListner();

        updateHederProfileInfo();
        //checkAndSetCurrentFragment();
        NavigationView navigationView = findViewById(R.id.nav_view);

        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        setDrawerToggleState();
        initClickListner();
        replaceFragment(currentFragment, getSupportFragmentManager(), true);


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
    public void hadleToolBarNextValue(TextView textView) {

    }

    @Override
    public void handleToolBarBackValue(TextView textView) {

    }

    private void initClickListner() {
    }

    private void updateHederProfileInfo() {
        String firstName = "Deepanshu";
        String lastname = "tyagi";

        if (firstName != null && lastname != null)
            txtUserName.setText(firstName + " " + lastname);
//        if (profileImageUrl != null) {
//            setUserProfileImage(profileImageUrl);
    }

    private void setonClickListner() {
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        mainLayProfile.setOnClickListener(this);
        mainLayShareApk.setOnClickListener(this);

    }

    @Override
    public void manageToolBar() {
        View app_bar = findViewById(R.id.app_bar_toolbar);
        toolbar = app_bar.findViewById(R.id.includeToolbar);
        setSupportActionBar(toolbar);
        toolbar.findViewById(R.id.txtBack).setVisibility(View.GONE);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.light_sky_blue)));
        mToolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        mToolbarTitle.setTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.findViewById(R.id.txtSave).setVisibility(View.INVISIBLE);
        toolbar.findViewById(R.id.txtSave).setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.contact_icon);
        MenuItem menuSearch = menu.findItem(R.id.search);
        menuSearch.setVisible(false);
        menuItem.setVisible(false);
        menuItems = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search:
                /*Bundle bundle = new Bundle();
                bundle.putString(MODULE_TYPE, KEY_CALLS_MODULE);
                Intent intent = new Intent(this, GlobalDetail.class);
                intent.putExtras(bundle);
                */
                // Intent intent = new Intent(MainDashBoardActivity.this, GlobalSearchActivity.class);
                //startActivity(intent);
                //return true;

            case R.id.contact_icon:
                // Toast.makeText(this, "Option item click", Toast.LENGTH_LONG).show();
                View view = findViewById(R.id.contact_icon);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setActionBarMenuItemVisibility(boolean setSearchItemVisibility, boolean setContactItemVisibility) {
        if (menuItems != null) {
            MenuItem menuSearchItem = menuItems.findItem(R.id.search);
            MenuItem menuContactItem = menuItems.findItem(R.id.contact_icon);
            if (menuSearchItem != null && menuContactItem != null) {
                menuSearchItem.setVisible(setSearchItemVisibility);
                menuContactItem.setVisible(setContactItemVisibility);
            }
        }
    }

    private void checkAndSetCurrentFragment() {
        BaseFragment f1 = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (f1 instanceof DashBoardFragment) {
            bottomNavigationView.setSelectedItemId(R.id.dashboard);
        } else if (f1 instanceof ProfileFragment)
            bottomNavigationView.setSelectedItemId(R.id.profile);
        else
            bottomNavigationView.setSelectedItemId(R.id.dashboard);
    }


    @Override
    public void onNetworkChangeStatus(boolean networkStatus, String msg) {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressbar() {

    }

    @Override
    public void hideToolbarNext() {

    }

    @Override
    public void showToolbarNext() {

    }

    @Override
    public void onNetworkChange(boolean networkStatus, String msg) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dashboard:
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                currentFragment = new DashBoardFragment();
                break;
            case R.id.profile:
                currentFragment = new ProfileFragment();
                break;
            default:
                currentFragment = new DashBoardFragment();
        }
//            if (currentFragment != null && item.getItemId()== R.id.profile) {
//                 replaceFragment(currentFragment, getSupportFragmentManager(), isAbleToAddOnStack);
//            }else
        replaceFragment(currentFragment, getSupportFragmentManager(), true);

        return true;
    }

    @Override
    public void replaceFragment(BaseFragment fragment, FragmentManager fragmentManager,
                                boolean isAddedToBackStack) {
        mFragmentManager.addOnBackStackChangedListener(this);
        FragmentTransaction fragTrans = mFragmentManager.beginTransaction();
        fragTrans.replace(R.id.fragment_container, fragment, fragment.toString());
        if (isAddedToBackStack)
            fragTrans.addToBackStack(fragment.toString());
        fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        currentFragment = fragment;
        // Don't try to call commitNow() instead of commit() in the next line.
        fragTrans.commit();

        // TODO Here transform drawer icon into back arrow in case of replaced fragment is not the fragment which can be changed from navigation drawer. i.e. If its a nested fragment screen for dashboard screen.
        /*if (currentFragment != null)
            toggleDrawerIcon();*/

        fragmentManager.executePendingTransactions();
    }

    @Override
    public void addFragmentWithoutReplace(BaseFragment fragmentToAdd, FragmentManager
            fragmentManager, boolean isAddedToBackStack) {
        mFragmentManager.addOnBackStackChangedListener(this);
        FragmentTransaction fragTrans = getSupportFragmentManager().beginTransaction();
        // TODO Here we need to set currentFragment value as we need to handle back press functionality either fragment open up(added) in overlay or replaced
        currentFragment = fragmentToAdd;
        fragTrans.add(R.id.fragment_container, fragmentToAdd, fragmentToAdd.toString());
        if (isAddedToBackStack)
            fragTrans.addToBackStack(fragmentToAdd.toString());
        fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragTrans.commit();
        fragmentManager.executePendingTransactions();

    }

    @Override
    public void updateActiveFragment(String toolbar_title) {
        currentFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (mToolbarTitle != null)
            mToolbarTitle.setText(toolbar_title);
        //toolbarTitleAnimation(mToolbarTitle);
    }

    @Override
    public void onBackStackChanged() {

    }

    private void animate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition((LinearLayout) findViewById(R.id.hamburgerLay)); // this line for expanding view
        }
    }

    private void setDrawerToggleState() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.d(TAG, "OPENED");
            }
        };
        drawer.addDrawerListener(toggle);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toggle.syncState();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        //revision: this don't works, use setOnChildClickListener() and setOnGroupClickListener() above instead
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawer.closeDrawers();
                        return true;
                    }
                });
    }

    private void closeDrawer() {
        try {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                //drawer is open
                drawer.closeDrawers();
            }
        } catch (Exception e) {

        }
    }


    @Override
    public void onClick(View view) {
        if (drawer.isDrawerOpen(GravityCompat.START))
            closeDrawer();
        switch (view.getId()) {
            case R.id.mainLayProfile:
                Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show();
                replaceFragment(new ProfileFragment(), getSupportFragmentManager(), false);
                break;
            case R.id.txtSave:
                Toast.makeText(this, "Save clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.mainLayShareApk:
                shareApplication();
                break;
        }
        checkAndSetCurrentFragment();

    }


    @Override
    public void onBackPressed() {
        if (this.drawer.isDrawerOpen(GravityCompat.START)) {
            this.drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            int backEntryCount = fragmentManager.getBackStackEntryCount() - 1;
            Log.e(TAG, "PagesOnBackStack :" + backEntryCount);
            if (backEntryCount == 0) {
                if (doubleTabBackButton) {
                    //super.onBackPressed();
                    finish();
                }

                if (this.drawer.isDrawerOpen(GravityCompat.START)) {
                    this.drawer.closeDrawer(GravityCompat.START);
                } else {
                    doubleTabBackButton = true;
                    ColoredSnackbar.alert(Snackbar.make(findViewById(android.R.id.content), R.string.click_back_wrng_msg, Snackbar.LENGTH_LONG)).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            doubleTabBackButton = false;
                        }
                    }, 2500);
                }
            } else {
                if (backEntryCount == 1) {
                    BaseFragment f = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    isAbleToAddOnStack = false;
                    bottomNavigationView.setSelectedItemId(R.id.dashboard);
                } else {
                    BaseFragment f = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                    super.onBackPressed();
                    //getFragmentManager().popBackStack();
                    isAbleToAddOnStack = false;
                    checkAndSetCurrentFragment();

                }
            }

        }
    }


    public void chekcAndRotateScreen() {
      /*  Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        final int orientation1 = display.getOrientation();
        int orientation2 = getRequestedOrientation(); // inside an Activity
        int orientation = this.getResources().getConfiguration().orientation;
        Toast.makeText(this, "orientation1:"+orientation+" orinetation2:"+orientation1+" orinetation:"+orientation, Toast.LENGTH_SHORT).show();
        switch(orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
        }

    }*/
        int orientation = MainDashBoardActivity.this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            //showMediaDescription();
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            // hideMediaDescription();
        }
        if (Settings.System.getInt(getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }
            }, 4000);
        }
    }

    public void visibleHideBottomNavigation(Boolean makeVisible){
        if(makeVisible)
        bottomNavigationView.setVisibility(View.VISIBLE);
        else{
            bottomNavigationView.setVisibility(View.GONE);
        }

    }
    private void shareApplication() {
        ApplicationInfo app = getApplicationContext().getApplicationInfo();
        String filePath = app.sourceDir;

        Intent intent = new Intent(Intent.ACTION_SEND);

        // MIME of .apk is "application/vnd.android.package-archive".
        // but Bluetooth does not accept this. Let's use "*/*" instead.
        intent.setType("*/*");

        // Append file and send Intent
        File originalApk = new File(filePath);

        try {
            //Make new directory in new location=
            File tempFile = new File(getExternalCacheDir() + "/ExtractedApk");
            //If directory doesn't exists create new
            if (!tempFile.isDirectory())
                if (!tempFile.mkdirs())
                    return;
            //Get application's name and convert to lowercase
            tempFile = new File(tempFile.getPath() + "/" + getString(app.labelRes).replace(" ","").toLowerCase() + ".apk");
            //If file doesn't exists create new
            if (!tempFile.exists()) {
                if (!tempFile.createNewFile()) {
                    return;
                }
            }
            //Copy file to new location
            InputStream in = new FileInputStream(originalApk);
            OutputStream out = new FileOutputStream(tempFile);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            System.out.println("File copied.");
            //Open share dialog
//          intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile));
            Uri photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", tempFile);
            //Uri photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", tempFile);
//          intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile));
            intent.putExtra(Intent.EXTRA_STREAM, photoURI);
            startActivity(Intent.createChooser(intent, "Share app via"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}