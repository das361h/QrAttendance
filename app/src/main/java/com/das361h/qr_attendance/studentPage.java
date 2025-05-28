package com.das361h.qr_attendance;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class studentPage extends AppCompatActivity {

    EditText SID, SNAME;
    Button button;
    ImageView qrImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_page);

        SID = findViewById(R.id.SID);
        SNAME = findViewById(R.id.SNAME);
        button = findViewById(R.id.button);
        qrImg = findViewById(R.id.qrImg);

        button.setOnClickListener(v -> {
            String sid = SID.getText().toString();
            String sname = SNAME.getText().toString();

            if (sid.isEmpty() || sname.isEmpty()) {
                Toast.makeText(studentPage.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            String sQRData = sid + "," + sname;

            BarcodeEncoder be = new BarcodeEncoder();
            try {
                qrImg.setImageBitmap(be.encodeBitmap(sQRData, BarcodeFormat.QR_CODE, 400, 400));
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(studentPage.this, "Error generating QR code", Toast.LENGTH_SHORT).show();
            }
        });
    }
}