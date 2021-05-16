package com.deepanshu.mymovieapp.database;

public class DBTables {
    // column names
    public static final String COLUMN_A_ID = "aId";
    public static final String COLUMN_KEY = "key";
    public static final String COLUMN_VALUE = "value";
    public static final String SORT_ORDER = "sort_order";
    // table names
    public static final String TABLE_NAME_GENDER = "gender_table";


    public static String createTable(String tableNameGender) {
        String query  = "CREATE TABLE IF NOT EXISTS "+ tableNameGender +
                " ( " + "" +
                COLUMN_A_ID + "" + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_KEY + " TEXT ," +
                SORT_ORDER +" INTEGER , "+
                COLUMN_VALUE + " TEXT " +
                " ) " ;
        return  query;
    }

}
