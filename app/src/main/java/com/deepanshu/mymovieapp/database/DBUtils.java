package com.deepanshu.mymovieapp.database;

import android.database.Cursor;

import com.deepanshu.mymovieapp.ui.module.SpinnerDataHolder;

import java.util.ArrayList;
import java.util.List;

import static com.deepanshu.mymovieapp.database.DBTables.COLUMN_A_ID;
import static com.deepanshu.mymovieapp.database.DBTables.COLUMN_KEY;
import static com.deepanshu.mymovieapp.database.DBTables.COLUMN_VALUE;
import static com.deepanshu.mymovieapp.database.DBTables.SORT_ORDER;

public class DBUtils {

    public static List<SpinnerDataHolder> getSpinnerList(Cursor cursor) {

        List<SpinnerDataHolder> dataHolderList = new ArrayList<>();
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    SpinnerDataHolder model = new SpinnerDataHolder();
                    model.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_A_ID)));
                    model.setSortOrder(cursor.getInt(cursor.getColumnIndex(SORT_ORDER)));
                    model.setKey(cursor.getString(cursor.getColumnIndex(COLUMN_KEY)));
                    model.setValue(cursor.getString(cursor.getColumnIndex(COLUMN_VALUE)));

                    dataHolderList.add(model);
                }
            }
            cursor.close();
        }
        return dataHolderList;
    }

    public static SpinnerDataHolder getSelectedSpinnerItemObj(Cursor cursor) {

        SpinnerDataHolder spinnerDataHolder = new SpinnerDataHolder();
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    spinnerDataHolder.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_A_ID)));
                    spinnerDataHolder.setSortOrder(cursor.getInt(cursor.getColumnIndex(SORT_ORDER)));
                    spinnerDataHolder.setKey(cursor.getString(cursor.getColumnIndex(COLUMN_KEY)));
                    spinnerDataHolder.setValue(cursor.getString(cursor.getColumnIndex(COLUMN_VALUE)));
                    return spinnerDataHolder;
                }
            }
            cursor.close();
        }
        // to avoid null pointer
        spinnerDataHolder.setValue("");
        spinnerDataHolder.setKey("");
        spinnerDataHolder.setSortOrder(0);
        return spinnerDataHolder;
    }


    public static List<SpinnerDataHolder> addNoneAtZeroIndex(List<SpinnerDataHolder> list) {
        List<SpinnerDataHolder> tempList = new ArrayList<>();
        SpinnerDataHolder spinnerDataHolder = new SpinnerDataHolder();
        spinnerDataHolder.setKey("");
        spinnerDataHolder.setValue("--None--");
        tempList.add(spinnerDataHolder);
        tempList.addAll(list);
        list.clear();
        list.addAll(tempList);
        return list;
    }
}
