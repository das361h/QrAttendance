package com.das361h.qr_attendance;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class teacherPortal extends AppCompatActivity {

    Button takeAtt,viewWeekly,viewStudent,logout,exit;

    //wipe
    CheckBox checkBox;
    Button wipe;
    EditText retype;

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
    }
}