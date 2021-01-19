package com.brigosha.tododemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListAdapter.DeleteTask {
    
    EditText etTask;
    ArrayList<Data> tasks;
    ListView taskListView;
    ListAdapter adapter;
    TextView tvNoTasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etTask = findViewById(R.id.etTask);
        taskListView = findViewById(R.id.taskList);
        tvNoTasks = findViewById(R.id.tvNoTasks);
        tasks = new ArrayList<Data>();
        Button submitBtn = findViewById(R.id.btSubmit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyBoard();
                String task = etTask.getText().toString();
                if (task.length() > 0) {
                    tvNoTasks.setVisibility(View.GONE);
                    tasks.add(0,new Data(task,false));
                    etTask.setText("");
                    taskListView.deferNotifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, R.string.enter_task, Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapter = new ListAdapter(this,tasks);
        taskListView.setAdapter(adapter);
    }

    // dismiss keyboard
    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if(imm.isAcceptingText()) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onDeleteTask(int index) {
        tasks.remove(index);
        adapter.notifyDataSetChanged();
        if (tasks.size() == 0) {
            tvNoTasks.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void dismissKeyBoard() {
        hideSoftKeyBoard();
    }
}