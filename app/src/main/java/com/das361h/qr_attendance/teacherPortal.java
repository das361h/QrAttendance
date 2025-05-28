package com.das361h.qr_attendance;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class teacherPortal extends AppCompatActivity {

    Button takeAtt,viewWeekly,viewStudent,logout,exit;

    //wipe
    CheckBox checkBox;
    Button wipe;
    EditText retype;
    SQLiteDBHelper SQLDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_portal);


        takeAtt=findViewById(R.id.takeAtt);
        takeAtt.setOnClickListener(v -> {
            startActivity(new Intent(teacherPortal.this, TakeAttendance.class));
        });


        viewWeekly=findViewById(R.id.viewWeekly);
        viewWeekly.setOnClickListener(v -> {
            startActivity(new Intent(teacherPortal.this, viewWeekly.class));
        });


        viewStudent=findViewById(R.id.viewStudent);
        viewStudent.setOnClickListener(v -> {
            startActivity(new Intent(teacherPortal.this, ViewStudent.class));
        });


        wipe=findViewById(R.id.wipe);
        checkBox=findViewById(R.id.checkBox);
        retype=findViewById(R.id.retype);
        SQLDB = new SQLiteDBHelper(this);
        wipe.setOnClickListener(v -> {
            if(checkBox.isChecked() && retype.getText().toString().equals("I am sure")){
                SQLDB.deleteAllRecord();
                checkBox.setChecked(false);
                retype.setText("");
                Toast.makeText(teacherPortal.this, "Database wiped", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(teacherPortal.this, "Read the instructions carefully", Toast.LENGTH_SHORT).show();
            }
        });

    }
}