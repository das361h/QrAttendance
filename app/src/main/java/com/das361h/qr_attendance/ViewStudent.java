package com.das361h.qr_attendance;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
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

    EditText editSID, editSNAME, classCount, searchSID;
    TextView weekAttended, online, total;
    Button ssi, ui, gp;
    SQLiteDBHelper SQLDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        editSID = findViewById(R.id.editSID);
        editSNAME = findViewById(R.id.editSNAME);
        classCount = findViewById(R.id.classCount);
        searchSID = findViewById(R.id.searchSID);

        weekAttended = findViewById(R.id.weekAttended);
        online = findViewById(R.id.online);
        total = findViewById(R.id.total);

        ssi = findViewById(R.id.button1);
        ui = findViewById(R.id.button2);
        gp = findViewById(R.id.button3);

        SQLDB = new SQLiteDBHelper(this);

        ssi.setOnClickListener(v -> {
            String sid = searchSID.getText().toString();
            if (sid.isEmpty()) {
                Toast.makeText(this, "Enter student ID in the text box", Toast.LENGTH_SHORT).show();
                return;
            }
            Cursor c = SQLDB.getStudentInfo(sid);
            if (c.moveToFirst()) {
                editSID.setText(c.getString(0));
                editSNAME.setText(c.getString(1));

                List<String> attweeks = new ArrayList<>();
                for (int i = 2; i <= 53; i++) {
                    String weeks = c.getString(i);
                    if (weeks.equals("O") || weeks.equals("P")) {
                        attweeks.add(c.getColumnName(i));
                    }
                }
                weekAttended.setText(TextUtils.join(", ", attweeks));
            }
            c.close();
        });
    }
}