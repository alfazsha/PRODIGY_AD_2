package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button addBtn;
    Dialog dialog;
    ImageView nxt,close;
    ListView listView;
    ArrayList<String> tasks;
    TaskAdapter adapter;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBtn = findViewById(R.id.addbtn);
        listView = findViewById(R.id.listView);

        sharedPreferences = getSharedPreferences("todolist", Context.MODE_PRIVATE);

        //loads our todo list
        tasks = loadTodoList();
        adapter = new TaskAdapter(this,tasks);
        listView.setAdapter(adapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addTaskDialog();
            }
        });
    }


    private ArrayList<String> loadTodoList()
    {
        Set<String> taskSet = sharedPreferences.getStringSet("tasks", new HashSet<>());
        return new ArrayList<>(taskSet);
    }


    private void saveTasks()
    {
        Set<String> taskSet = new HashSet<>(tasks);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("tasks", taskSet);
        editor.apply();
    }


    public void addTaskDialog()
    {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        //edittext where user enter task
        EditText taskNameEt = dialog.findViewById(R.id.todoEt);
        nxt = dialog.findViewById(R.id.todoNextBtn);
        close = dialog.findViewById(R.id.todoClose);

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = taskNameEt.getText().toString();
                if (!taskName.isEmpty())
                {
                    tasks.add(taskName);
                    adapter.notifyDataSetChanged();
                    saveTasks();
                    dialog.dismiss();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please, Enter Task", Toast.LENGTH_SHORT).show();
                }

            }
        });

        close.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public void showEditDialog(int position)
    {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogedit);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        //edittext where user enter task
        EditText taskNameEt = dialog.findViewById(R.id.todoEt);
        nxt = dialog.findViewById(R.id.todoNextBtn);
        close = dialog.findViewById(R.id.todoClose);

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = taskNameEt.getText().toString();
                if (!taskName.isEmpty())
                {
                    tasks.set(position,taskName);
                    adapter.notifyDataSetChanged();
                    saveTasks();
                    dialog.dismiss();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please, Enter Task", Toast.LENGTH_SHORT).show();
                }

            }
        });

        close.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public void showDeleteDialog(int position)
    {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogdelete);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        nxt = dialog.findViewById(R.id.todoNextBtn);
        close = dialog.findViewById(R.id.todoClose);

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tasks.remove(position);
                adapter.notifyDataSetChanged();
                saveTasks();
                dialog.dismiss();

            }
        });

        close.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
               dialog.dismiss();
            }
        });

        dialog.show();

    }

}
