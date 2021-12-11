package com.example.blocknote.db;

/*
* Константы для базы данных SQLite
 */
public class DbConstants {

    public static final String DB_NAME = "blocknote.db";
    public static final Integer DB_VERSION = 1;
    public static final String TABLE_NAME = "blocknote";
    public static final String _ID = "_id";
    public static final String TABLE_TITLE = "title";
    public static final String TABLE_DESCRIPTION = "description";
    public static final String TABLE_URI = "uri";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY, " +
            TABLE_TITLE + " TEXT, " +
            TABLE_DESCRIPTION + " TEXT, " +
            TABLE_URI + " TEXT" +
            ")";

    public static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
