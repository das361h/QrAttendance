package com.das361h.qr_attendance;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class ViewStudent extends AppCompatActivity {

    EditText editSID, editSNAME, numClass, searchSID;
    TextView weekAttended, online, total;
    Button ssi, ui, gp;
    SQLiteDBHelper SQLDB;

    @SuppressLint({"SetTextI18n", "Range", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        editSID = findViewById(R.id.editSID);
        editSNAME = findViewById(R.id.editSNAME);
        numClass = findViewById(R.id.numClass);
        searchSID = findViewById(R.id.searchSID);

        weekAttended = findViewById(R.id.weekAttended);
        online = findViewById(R.id.online);
        total = findViewById(R.id.total);

        ssi = findViewById(R.id.ssi);
        ui = findViewById(R.id.ui);
        gp = findViewById(R.id.gp);

        SQLDB = new SQLiteDBHelper(this);

        ssi.setOnClickListener(v -> {
            String sid = searchSID.getText().toString().trim();
            if (sid.isEmpty()) {
                Toast.makeText(this, "Enter student ID in the text box", Toast.LENGTH_SHORT).show();
                return;
            }
            Cursor c = SQLDB.getStudentInfo(sid);
            if (c.moveToFirst()) {
                editSID.setText(c.getString(0));
                //c.getColumnIndex("SNAME")
                editSNAME.setText(c.getString(1));

                StringBuilder attendanceList = new StringBuilder();
                for (int i = 2; i <= 53; i++) {
                    String status = c.getString(i);
                    if ("O".equals(status)){
                        attendanceList.append(c.getColumnName(i)).append(" - ").append(status).append(", ");
                    }
                    if ("P".equals(status)){
                        attendanceList.append(c.getColumnName(i)).append(" - ").append(status).append(", ");
                    }
                }
                if (attendanceList.length() > 0) {
                    attendanceList.setLength(attendanceList.length() - 2);
                }
                weekAttended.setText(attendanceList.toString());
            }
            c.close();
        });

        ui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean update = SQLDB.updateStudentInfo(searchSID.getText().toString(), editSNAME.getText().toString(), editSNAME.getText().toString());
                if (update) {
                    Toast.makeText(ViewStudent.this, "Student details updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ViewStudent.this, "Student details not updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        gp.setOnClickListener(v -> {
            String sid = editSID.getText().toString();
            String classCount = numClass.getText().toString();

            // If textbox empty
            if (classCount.isEmpty()){
                Toast.makeText(this, "Enter a value", Toast.LENGTH_SHORT).show();
                return;
            }
            // If textbox is not numerical
            if (!TextUtils.isDigitsOnly(classCount)){
                Toast.makeText(this, "Enter a number", Toast.LENGTH_SHORT).show();
                return;
            }
            int weekCount = Integer.parseInt(classCount);
            Cursor c = SQLDB.getStudentInfo(sid);
            if (c.moveToFirst()) {
                int onlineAttC = 0, pAttC = 0;
                for (int i = 2; i <= 53; i++) {
                    String attended = c.getString(i);
                    if ("O".equals(attended)){
                        onlineAttC++;
                    }
                    if ("P".equals(attended)){
                        pAttC++;
                    }
                }
                int totalAttc = onlineAttC + pAttC;
                double onlinePer = (double) onlineAttC / weekCount * 100;
                double allAttPer = (double) totalAttc / weekCount * 100;
                online.setText("Online Attendance Count - " + onlineAttC +" , " + "Percentage = "+ String.format("%.2f", onlinePer) + "%");
                total.setText("Total Attendance Count - " + totalAttc +" , " + "Percentage = "+ String.format("%.2f", allAttPer) + "%");
            }
            c.close();
        });
    }
}