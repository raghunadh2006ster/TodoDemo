package com.brigosha.tododemoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Data> {

    private final Context context;
    private ArrayList<Data> tasks;
    DeleteTask activity;

    public interface DeleteTask{
        void onDeleteTask(int index);
        void dismissKeyBoard();
    }


    public ListAdapter(Context context, ArrayList<Data> tasks) {
        super(context, R.layout.row_layout,tasks);
        this.context = context;
        activity = (DeleteTask) context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout,parent,false);
        EditText etTask = (EditText) rowView.findViewById(R.id.etTaskDetails);
        ImageView ivStatus = (ImageView) rowView.findViewById(R.id.ivStatus);
        Button btDone = (Button) rowView.findViewById(R.id.btDone);
        etTask.setText(tasks.get(position).getDescription());
        btDone.setVisibility(View.GONE);
        // edit task
        etTask.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                btDone.setVisibility(View.VISIBLE);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskStr = etTask.getText().toString().trim();
                Data task = tasks.get(position);
                task.setDescription(taskStr);
                activity.dismissKeyBoard();
            }
        });
        // delete task
        etTask.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage(R.string.delete_msg);
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        R.string.alert_yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                activity.onDeleteTask(position);
                            }
                        });

                builder1.setNegativeButton(
                        R.string.alert_no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                return false;
            }
        });
        // change task status
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tasks.get(position).getCompleted()) {
                    tasks.get(position).setCompleted(false);
                } else {
                    tasks.get(position).setCompleted(true);
                }
                int imageSource = tasks.get(position).getCompleted() ? android.R.drawable.checkbox_on_background : android.R.drawable.checkbox_off_background;
                ivStatus.setImageResource(imageSource);
            }
        });
        return rowView;
    }


}