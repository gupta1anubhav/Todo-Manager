package com.example.android.todomanager.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.todomanager.models.Todo;

import java.util.ArrayList;

import static android.R.attr.x;
import static android.content.ContentValues.TAG;
import static com.example.android.todomanager.db.tables.DbConsts.CMD_ADD_COLUMN;
import static com.example.android.todomanager.db.tables.DbConsts.CMD_ALTER_TABLE;
import static com.example.android.todomanager.db.tables.DbConsts.CMD_CREATE_TABLE_INE;
import static com.example.android.todomanager.db.tables.DbConsts.COMMA;
import static com.example.android.todomanager.db.tables.DbConsts.LBR;
import static com.example.android.todomanager.db.tables.DbConsts.RBR;
import static com.example.android.todomanager.db.tables.DbConsts.SEMI;
import static com.example.android.todomanager.db.tables.DbConsts.TYPE_AI;
import static com.example.android.todomanager.db.tables.DbConsts.TYPE_BOOL;
import static com.example.android.todomanager.db.tables.DbConsts.TYPE_INT;
import static com.example.android.todomanager.db.tables.DbConsts.TYPE_PK;
import static com.example.android.todomanager.db.tables.DbConsts.TYPE_TEXT;

/**
 * Created by Sushila on 10/2/2017.
 */

public class TodoTable {
    public static final String TABLE_NAME = "todos";
    public interface Columns{
        String ID = "id";
        String TASK = "task";
        String DONE = "done";
    }
    public static final String CMD_CREATE =
            CMD_CREATE_TABLE_INE + TABLE_NAME +
                    LBR +
                    Columns.ID + TYPE_INT + TYPE_PK + TYPE_AI + COMMA +
                    Columns.TASK + TYPE_TEXT + COMMA +
                    Columns.DONE + TYPE_BOOL +
                    RBR +
                    SEMI;

    public static ArrayList<Todo> getAllTodos (SQLiteDatabase db) {
        ArrayList<Todo> todos = new ArrayList<>();

        Cursor c = db.query(
                TABLE_NAME,
                new String[]{
                        Columns.ID,Columns.TASK,Columns.DONE
                },
                null,
                null,
                null,
                null,
                null
        );
        int colForId = c.getColumnIndex(Columns.ID);
        int colForTask = c.getColumnIndex(Columns.TASK);
        int colForDone = c.getColumnIndex(Columns.DONE);
        while (c.moveToNext()) {
            todos.add(
                    new Todo(
                            c.getInt(colForId),
                            c.getString(colForTask),
                            c.getInt(colForDone) != 0
                    )
            );
        }
        return  todos;
    }

    public static long insertTodo (SQLiteDatabase db, Todo todo) {
        ContentValues todoData = new ContentValues();
        todoData.put(Columns.TASK, todo.getTask());
        todoData.put(Columns.DONE, todo.isDone());
        return db.insert(
                TABLE_NAME,
                null,
                todoData
        );
    }
    public static int deleteTodo (SQLiteDatabase db){
        int x=db.delete(TABLE_NAME,Columns.DONE+"=?", new String[]{"1" });
       return x;
    }
    public static int updateTodo (SQLiteDatabase db, Todo todo,int position) {
        ContentValues cv = new ContentValues();
        cv.put(Columns.DONE,todo.isDone());
        cv.put(Columns.TASK,todo.getTask());
        //Log.d(TAG, "updateTodo: "+todo.getId());
        return db.update(TABLE_NAME,cv,Columns.ID+"=?",new String[]{ String.valueOf(position)});
    }

    public static void DeleteTable(SQLiteDatabase db, int todoId) {

    }

}
