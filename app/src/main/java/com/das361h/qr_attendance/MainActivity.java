package com.das361h.qr_attendance;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button studentLogin,teacherLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentLogin=findViewById(R.id.studentLogin);
        teacherLogin=findViewById(R.id.teacherLogin);

        //teachers auth page
        teacherLogin.setOnClickListener(v -> {
            Intent t=new Intent(MainActivity.this,teacherAuth.class);
            startActivity(t);
        });

        //students page for qr generation
        studentLogin.setOnClickListener(v -> {
            Intent s=new Intent(MainActivity.this,studentPage.class);
            startActivity(s);
        });

    }
}