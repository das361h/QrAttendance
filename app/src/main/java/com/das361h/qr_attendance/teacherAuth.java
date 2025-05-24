package com.das361h.qr_attendance;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class teacherAuth extends AppCompatActivity {

    EditText emailAuth,passwordAuth,rePasswordAuth;
    Button loginBtn,regBtn,resetBtn,clearAuthField;
    private FirebaseAuth tAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_auth);

        emailAuth=findViewById(R.id.emailAuth);
        passwordAuth=findViewById(R.id.passwordAuth);
        rePasswordAuth=findViewById(R.id.rePasswordAuth);

        loginBtn=findViewById(R.id.loginBtn);
        regBtn=findViewById(R.id.regBtn);
        resetBtn=findViewById(R.id.resetBtn);
        clearAuthField=findViewById(R.id.clearAuthField);

        tAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(v -> {
            String em=emailAuth.getText().toString();
            String pw=passwordAuth.getText().toString();

            if(emailAuth.getText().toString().isEmpty() || passwordAuth.getText().toString().isEmpty()){
                Toast.makeText(this, "Please fill both email and password fields", Toast.LENGTH_SHORT).show();
            }
            else{
                tAuth.signInWithEmailAndPassword(em, pw).addOnCompleteListener(task ->{
                    if (task.isSuccessful()){
                        startActivity(new Intent(teacherAuth.this, teacherPortal.class));
                        Toast.makeText(teacherAuth.this, "Logged in", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(teacherAuth.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        regBtn.setOnClickListener(v -> {
            String em=emailAuth.getText().toString();
            String pw=passwordAuth.getText().toString();
            String rpw=rePasswordAuth.getText().toString();

            if(emailAuth.getText().toString().isEmpty() || passwordAuth.getText().toString().isEmpty() || rePasswordAuth.getText().toString().isEmpty()){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
            else if(!pw.equals(rpw)){
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
            else if(pw.length()<6){
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            }
            else{
                tAuth.createUserWithEmailAndPassword(em, pw).addOnCompleteListener(task ->{
                    if (task.isSuccessful()){
                        startActivity(new Intent(teacherAuth.this, teacherPortal.class));
                        Toast.makeText(teacherAuth.this, "Account created and logged in", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(teacherAuth.this, "Account creation failed because an account with similar email already exists. Please login with correct credentials or reset password", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        resetBtn.setOnClickListener(v -> {
            String em=emailAuth.getText().toString();
            if(emailAuth.getText().toString().isEmpty()){
                Toast.makeText(this, "Please enter your email associated with your account", Toast.LENGTH_SHORT).show();
            }
            else{
                tAuth.sendPasswordResetEmail(em).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(teacherAuth.this, "Password reset link sent to your email", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(teacherAuth.this, "Password reset link not sent", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        clearAuthField.setOnClickListener(v -> {
            emailAuth.setText("");
            passwordAuth.setText("");
            rePasswordAuth.setText("");
            Toast.makeText(this, "All fields cleared", Toast.LENGTH_SHORT).show();
        });
    }
}