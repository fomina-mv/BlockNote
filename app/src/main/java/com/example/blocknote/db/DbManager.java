package com.example.blocknote.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/*
 * Класс для управления вызовами к БД
 */
public class DbManager {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase database;

    public DbManager(Context context) {
        this.context = context;
        this.dbHelper = new DbHelper(context);
    }

    //Открыть БД для записи
    public void openWriteDb() {
        database = dbHelper.getWritableDatabase();
    }

    //Открыть БД для чтения
    public void openReadDb() {
        database = dbHelper.getReadableDatabase();
    }

    //Закрыть сессию к БД
    public void closeDb() {
        dbHelper.close();
    }

    //Вставить значения в таблицу TABLE_TITLE
    public long insertDb(String title, String description) {
        ContentValues values = new ContentValues();
        values.put(DbConstants.TABLE_TITLE, title);
        values.put(DbConstants.TABLE_DISCRIPTION, description);

        return database.insert(DbConstants.TABLE_NAME, null, values);
    }

    //Прочитать строки из таблицы TABLE_TITLE
    public List<String> readDbValues() {
        List<String> dbList = new ArrayList<>();

        String[] projection = {
                DbConstants._ID,
                DbConstants.TABLE_TITLE,
                DbConstants.TABLE_DISCRIPTION
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = DbConstants.TABLE_TITLE + " = ?";
        String[] selectionArgs = {"My Title"};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                DbConstants.TABLE_DISCRIPTION + " DESC";

        Cursor cursor = database.query(
                DbConstants.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        while (cursor.moveToNext()) {
            String itemId = cursor.getString(cursor.getColumnIndex(DbConstants.TABLE_TITLE));
            dbList.add(itemId);
        }

        cursor.close();
        return dbList;
    }


}
