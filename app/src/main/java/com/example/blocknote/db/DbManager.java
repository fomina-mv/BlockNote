package com.example.blocknote.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.example.blocknote.adapter.Note;

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
    public long insertDb(String title, String description, String uri) {
        ContentValues values = new ContentValues();
        values.put(DbConstants.TABLE_TITLE, title);
        values.put(DbConstants.TABLE_DESCRIPTION, description);
        values.put(DbConstants.TABLE_URI, uri);

        return database.insert(DbConstants.TABLE_NAME, null, values);
    }

    //Прочитать заголовки из таблицы TABLE_TITLE
    public List<Note> readDbValues(String titleFilter) {
        List<Note> dbList = new ArrayList<>();

        String[] projection = {
                DbConstants._ID,
                DbConstants.TABLE_TITLE,
                DbConstants.TABLE_DESCRIPTION,
                DbConstants.TABLE_URI
        };

        // Filter results WHERE "title" = titleFilter
        String selection = DbConstants.TABLE_TITLE + " like ?";
        String[] selectionArgs = {"%" + titleFilter + "%"};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                DbConstants.TABLE_TITLE + " DESC";

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
            Note note = new Note();
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DbConstants._ID));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(DbConstants.TABLE_TITLE));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(DbConstants.TABLE_DESCRIPTION));
            @SuppressLint("Range") String uri = cursor.getString(cursor.getColumnIndex(DbConstants.TABLE_URI));
            note.setId(id);
            note.setTitle(title);
            note.setDescription(description);
            note.setUri(uri);
            dbList.add(note);
        }

        cursor.close();
        return dbList;
    }

    public void deleteValues(int id) {
        String selection = DbConstants._ID + " = " + id;
        database.delete(DbConstants.TABLE_NAME, selection, null);
    }

    public void updateValues(int id, String title, String description, String uri) {
        String selection = DbConstants._ID + " = " + id;
        ContentValues values = new ContentValues();
        values.put(DbConstants.TABLE_TITLE, title);
        values.put(DbConstants.TABLE_DESCRIPTION, description);
        values.put(DbConstants.TABLE_URI, uri);
        database.update(DbConstants.TABLE_NAME,values, selection, null);
    }
}
