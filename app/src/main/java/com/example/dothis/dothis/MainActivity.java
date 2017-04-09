package com.example.dothis.dothis;
/*
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.dothis.dothis.Adapter.TaskInformationAdapter;
import com.example.dothis.dothis.BaseClases.TaskInformations;
import com.example.dothis.dothis.db.TaskContract;
import com.example.dothis.dothis.db.TaskDbHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
*/

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.example.dothis.dothis.stab.SlidingTabLayout;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Layout manager that allows the user to flip through the pages
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // getSupportFragmentManager allows use to interact with the fragments
        // MyFragmentPagerAdapter will return a fragment based on an index that is passed
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Initialize the Sliding Tab Layout
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);

        // Connect the viewPager with the sliding tab layout
        slidingTabLayout.setViewPager(viewPager);
    }




        /*=extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TaskDbHelper mHelper;
    private ListView mTaskListView;
    private ArrayAdapter<TaskInformations> taskInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTaskListView = (ListView) findViewById(R.id.list_todo);

        mHelper = new TaskDbHelper(this);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE,TaskContract.TaskEntry.DUE_DATE},
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            Log.d(TAG, "Task: " + cursor.getString(idx));
        }
        updateUI();
        cursor.close();
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                final EditText taskEditText = new EditText(this);
                final TextView chooseDate = new TextView(this);
                chooseDate.setText("Set due date:");

                //final Text setNumberPoints = new Text(this);
                chooseDate.setText("Select how many points does this task requires: ");

                final TextView emptyRow = new TextView(this);
                emptyRow.setText("");
                final DatePicker datePicker = new DatePicker(this);
                final NumberPicker numberPicker = new NumberPicker(this);
                numberPicker.setMinValue(1);
                numberPicker.setMaxValue(100);

                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);

                layout.addView(taskEditText);
                layout.addView(emptyRow);
                layout.addView(chooseDate);
                layout.addView(datePicker);
                layout.addView(numberPicker);

                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new task")
                        .setMessage("What do you want to do next?")
                        .setView(layout)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                Calendar calendar = Calendar.getInstance();

                                int day = datePicker.getDayOfMonth();
                                int month = datePicker.getMonth();
                                int year =  datePicker.getYear();
                                calendar.set(year, month, day);

                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                                values.put(TaskContract.TaskEntry.DUE_DATE, calendar.getTime().toString());
                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        ArrayList<TaskInformations> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE, TaskContract.TaskEntry.DUE_DATE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx_TaskTitle = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            int idx_TaskDueDate = cursor.getColumnIndex(TaskContract.TaskEntry.DUE_DATE);
            taskList.add(new TaskInformations(cursor.getString(idx_TaskTitle),cursor.getString(idx_TaskDueDate)));
        }

        if (taskInfoAdapter == null) {
//            taskInfoAdapter = new ArrayAdapter<>(this,
//                    R.layout.item_todo,
//                    R.id.task_title,
//                    taskList);
            taskInfoAdapter = new TaskInformationAdapter(this,
                    R.layout.item_todo,
                    R.id.task_title,
                    taskList);
            mTaskListView.setAdapter(taskInfoAdapter);
        } else {
            taskInfoAdapter.clear();
            taskInfoAdapter.addAll(taskList);
            taskInfoAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry.COL_TASK_TITLE + " = ?",
                new String[]{task});
        db.close();
        updateUI();
    }
*/
}
