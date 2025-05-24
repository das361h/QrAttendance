package com.das361h.qr_attendance;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class viewWeekly extends AppCompatActivity {

    Spinner spinner;
    Button loadWeekly,update;
    SQLiteDBHelper SQLDB;
    LinearLayout slc;
    List<StudentList> sl = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_weekly);

        spinner = findViewById(R.id.spinner);
        loadWeekly = findViewById(R.id.loadWeekly);
        update = findViewById(R.id.update);
        slc = findViewById(R.id.StudentListContainer);
        SQLDB = new SQLiteDBHelper(this);

        ArrayAdapter<String> adapter=new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.week_array));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        loadWeekly.setOnClickListener(v -> {
            slc.removeAllViews();
            String week = spinner.getSelectedItem().toString();
            List<StudentList> students = SQLDB.getStudentList(week);

            sl.clear();
            sl.addAll(students);

            for (StudentList s : students) {
                CheckBox cb = new CheckBox(this);
                String ent=s.sid + " - " + s.sname;
                cb.setText(ent);
                cb.setChecked(false);
                s.cb = cb;
                slc.addView(cb);
            }

        });

        update.setOnClickListener(v -> {
            String week = spinner.getSelectedItem().toString();
            for (StudentList s : sl) {
                if (s.cb.isChecked()) {
                    SQLDB.updateInWeekly(s.sid, week, "");
                }
            }
            Toast.makeText(this, "Checked entities removed", Toast.LENGTH_SHORT).show();
        });
    }

    static class StudentList {
        String sid;
        String sname;
        CheckBox cb;

        StudentList(String sid, String sname){
            this.sid = sid;
            this.sname = sname;
        }
    }
}