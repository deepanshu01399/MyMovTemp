package com.deepanshu.mymovieapp.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.deepanshu.mymovieapp.ui.activity.BaseActivity;

public abstract class BaseFragment extends Fragment implements IFragment {
    public LayoutInflater mInflater;
    private static final String TAG = "BaseFragment";


    public BaseFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //public LayoutInflater mInflater = LayoutInflater.from(getActivity()); // 1
        mInflater = getActivity().getLayoutInflater();
        //View theInflatedView = inflater.inflate(R.layout.your_layout, null); // 2 and 3


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_base, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void hideSoftKeyboard(Context context, View view) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            Log.d(TAG, "hideSoftKeyboard: ");
        }

    }

    @Override
    public void updateToolbarBackButtonText(String backTextValue) {
        if (!TextUtils.isEmpty(backTextValue)) {
            ((BaseActivity) getActivity()).updateToolBarBackValue(backTextValue);
        }
    }

    @Override
    public void updateToolbarNextButtonText(String nextTextValue) {
        if (!TextUtils.isEmpty(nextTextValue)) {
            ((BaseActivity) getActivity()).updateToolBarNextValue(nextTextValue);
        }
    }

    @Override
    public void hideToolbarNext() {
        ((BaseActivity) getActivity()).hideToolbarNext();
    }

    @Override
    public void showToolbarNext() {
        ((BaseActivity) getActivity()).showToolbarNext();
    }

    @Override
    public void manageToolbar() {
        ((BaseActivity) getActivity()).manageToolBar();
    }

    @Override
    public void showSnackBarMessage(String message) {
        ((BaseActivity) getActivity()).showSnackBarMessage(message);
    }

    @Override
    public void showSnackbarInfo(String message) {
        ((BaseActivity) getActivity()).showSnackbarInfo(message);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("Inside: ", getClass().getSimpleName() + " onActivityResult method.");
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onFragmentBackPress() {
        getFragmentManager().popBackStackImmediate();
//        getActivity().onBackPressed();
    }

    protected void startActivity(Class<?> className) {
        Intent i = new Intent(getContext(), className);
        startActivity(i);
    }

    protected void startActivity(Class<?> className, String action) {
        Intent i = new Intent(getContext(), className);
        if (action != null)
            i.setAction(action);
        startActivity(i);
    }

    protected void startActivity(Class<?> className, Bundle bundle) {
        Intent i = new Intent(getContext(), className);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (bundle != null)
            i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    public void openHomeFromBaseFragment(Bundle bundle) {
        ((BaseActivity) getActivity()).openDashboardScreenFromBaseActivity(bundle);
//        ((BaseActivity)getActivity()).finish();
    }


}