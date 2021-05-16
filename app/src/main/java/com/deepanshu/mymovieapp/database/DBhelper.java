package com.deepanshu.mymovieapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.deepanshu.mymovieapp.ui.module.SpinnerDataHolder;

import java.util.List;

import static com.deepanshu.mymovieapp.database.DBTables.COLUMN_A_ID;
import static com.deepanshu.mymovieapp.database.DBTables.COLUMN_KEY;
import static com.deepanshu.mymovieapp.database.DBTables.COLUMN_VALUE;
import static com.deepanshu.mymovieapp.database.DBTables.SORT_ORDER;
import static com.deepanshu.mymovieapp.database.DBTables.TABLE_NAME_GENDER;

public class DBhelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "my_movie";
    private static final int DB_VERSION = 1;
    private static DBhelper dbHelper;

    public DBhelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static void insertAll(Context  context, String tableName, List<SpinnerDataHolder> contentValue){
        SQLiteDatabase database = getSQLiteInstance(context);
        database.beginTransaction();
        try{
            for(int i= 0;i<contentValue.size();i++){
                ContentValues values = new ContentValues();
                values.put(SORT_ORDER,i);
                values.put(COLUMN_KEY,contentValue.get(i).getKey().trim());
                values.put(COLUMN_VALUE, contentValue.get(i).getValue().trim());
                database.insert(tableName,null ,values);
            }
        database.setTransactionSuccessful();
        }finally{

            database.endTransaction();
        }

    }


    public static SQLiteDatabase getSQLiteInstance(Context context){
        if(dbHelper==null){
            dbHelper = new DBhelper(context);
        }
        return dbHelper.getWritableDatabase();
    }

    public static SQLiteDatabase getSQLiteInstanceRead(Context pContext) {
        if (dbHelper == null)
            dbHelper = new DBhelper(pContext);
        return dbHelper.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("SchedulerTracker", "create db");
        try {
            sqLiteDatabase.execSQL(DBTables.createTable(TABLE_NAME_GENDER));

        } catch (Exception e) {

        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME_GENDER);
    }

    public static void clearDB(Context context){
    SQLiteDatabase database = getSQLiteInstance(context);
    try{
        database.delete(TABLE_NAME_GENDER,null,null);
    }catch (Exception e){
        e.printStackTrace();
    }
    }

    public static Cursor fetchRow(Context context, String table_Name) {
        return getSQLiteInstance(context).rawQuery("Select * from " + table_Name, null);
    }

    public static Cursor fetchRow(Context pContext, String pTableName, String[] columns, String
            where, String[] whereArgs, String orderBy) {
        return getSQLiteInstance(pContext).query(true, pTableName, columns, where, whereArgs, null,
                null, orderBy, null);
    }

    public static Cursor fetchRow(Context pContext, String pTableName, String[] columns, String
            where, String[] whereArgs, String orderBy, String limit) {
        return getSQLiteInstance(pContext).query(true, pTableName, columns, where, whereArgs, null,
                null, orderBy, limit);
    }

    public static int deleteRow(Context pContext, String pTableName, String where, String[] whereArgs) {
        return getSQLiteInstance(pContext).delete(pTableName, where, whereArgs);
    }

    public static void clearTable(Context pContext, String pTableName) {
        SQLiteDatabase db = getSQLiteInstance(pContext);
        db.delete(pTableName, null, null);
    }


}
