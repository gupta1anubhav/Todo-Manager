package com.example.android.todomanager.models;

/**
 * Created by Sushila on 10/2/2017.
 */

public class Todo {
    public Todo(int id, String task, boolean done) {
        this.id = id;
        this.task = task;
        this.done = done;
    }
    public Todo(String task, boolean done) {
        this.task = task;
        this.done = done;
    }

    int id;
    String task;
    boolean done;

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public boolean isDone() {
        return done;
    }
}
