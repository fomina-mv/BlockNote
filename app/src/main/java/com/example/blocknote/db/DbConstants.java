package com.example.blocknote.db;

/*
* Константы для базы данных SQLite
 */
public class DbConstants {

    public static final String DB_NAME = "blocknote.db";
    public static final Integer DB_VERSION = 1;
    public static final String TABLE_NAME = "table";
    public static final String _ID = "_id";
    public static final String TABLE_TITLE = "title";
    public static final String TABLE_DISCRIPTION = "discription";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXIST" +
            TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY," +
            TABLE_TITLE + " TEXT," +
            TABLE_DISCRIPTION + " TEXT)";

    public static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
