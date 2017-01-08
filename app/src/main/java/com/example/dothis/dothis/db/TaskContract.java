package com.example.dothis.dothis.db;

import android.provider.BaseColumns;

/**
 * Created by Frati on 12/3/2016.
 */

public class TaskContract {
    public static final String DB_NAME = "com.adrian.todolist.db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";
        public static final String COL_TASK_TITLE = "title";
        //public static final String START_DATE = "start_date";
        public static final String DUE_DATE = "due_date";
    }
}