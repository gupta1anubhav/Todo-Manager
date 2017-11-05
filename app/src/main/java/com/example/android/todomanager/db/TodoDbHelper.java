package com.example.android.todomanager.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.todomanager.db.tables.TodoTable;

/**
 * Created by Sushila on 10/2/2017.
 */

public class TodoDbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "todo.db";
    public static final int DB_VER = 1;
    public TodoDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TodoTable.CMD_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
