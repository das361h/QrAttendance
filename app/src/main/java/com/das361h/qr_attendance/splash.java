package com.das361h.qr_attendance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


public class splash extends AppCompatActivity {

    Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent sp=new Intent(splash.this,MainActivity.class);
                startActivity(sp);
                finish();
            }
        },3000);
    }
}