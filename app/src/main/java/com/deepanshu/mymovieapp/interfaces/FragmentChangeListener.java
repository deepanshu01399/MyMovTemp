package com.deepanshu.mymovieapp.interfaces;

import androidx.fragment.app.FragmentManager;

import com.deepanshu.mymovieapp.ui.fragment.BaseFragment;

public interface FragmentChangeListener {
    public void replaceFragment(BaseFragment fragment, FragmentManager fragmentManager, boolean isAddedToBackStack);
    public void addFragmentWithoutReplace(BaseFragment fragment, FragmentManager fragmentManager, boolean isAddedToBackStack);
    public void updateActiveFragment(String headername);
}
