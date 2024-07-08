package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<String> {

    private ArrayList<String> tasks;
    private Context context;

    public TaskAdapter(Context context, ArrayList<String> tasks) {
        super(context, 0, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.card, parent, false);
        }

        String task = tasks.get(position);

        TextView todoTask = convertView.findViewById(R.id.todoTask);
        ImageView editTask = convertView.findViewById(R.id.editTask);
        ImageView deleteTask = convertView.findViewById(R.id.deleteTask);

        todoTask.setText(task);

        editTask.setOnClickListener(v -> {
            if (context instanceof MainActivity) {
                ((MainActivity) context).showEditDialog(position);
            }
        });

        deleteTask.setOnClickListener(v -> {
            if (context instanceof MainActivity) {
                ((MainActivity) context).showDeleteDialog(position);
            }
        });

        return convertView;
    }
}
