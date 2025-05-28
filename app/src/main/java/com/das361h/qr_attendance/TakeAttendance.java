package com.das361h.qr_attendance;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class TakeAttendance extends AppCompatActivity {

    CheckBox checkBox;
    Button scanQR,finalize;
    EditText snameBox,sidBox;
    Spinner spinner;
    SQLiteDBHelper SQLHelp;


    //take data from qr code and puts them in a array
    public final ActivityResultLauncher<ScanOptions> qr = registerForActivityResult(new ScanContract(), result -> {
        String res = result.getContents();
        if (res.contains(",")) {
            String[] data = result.getContents().split(","); //takes qr data and splits it into an array where it is separated by comma
            sidBox.setText(data[0]);
            snameBox.setText(data[1]);
        }
        else {
            Toast.makeText(this, "QR code was invalid because it contained single value", Toast.LENGTH_SHORT).show();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);

        checkBox=findViewById(R.id.checkBox);
        scanQR=findViewById(R.id.scanQR);
        finalize=findViewById(R.id.finalize);
        snameBox=findViewById(R.id.snameBox);
        sidBox=findViewById(R.id.sidBox);
        spinner=findViewById(R.id.spinner);
        SQLHelp=new SQLiteDBHelper(this);

        //this will fill up the spinner with all 52 weeks
        ArrayAdapter<String> adapter=new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.week_array));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        scanQR.setOnClickListener(v -> {
            ScanOptions so = new ScanOptions();
            so.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
            so.setPrompt("Scan the QR code");
            so.setCameraId(0);
            so.setBeepEnabled(true);
            qr.launch(so);
        });

        finalize.setOnClickListener(v -> {
            String sid=sidBox.getText().toString();
            String sname=snameBox.getText().toString();
            String week=spinner.getSelectedItem().toString();
            String status;
            if(checkBox.isChecked()){
                status="O";
            }
            else{
                status="P";
            }
            SQLHelp.insertorupdate(sid,sname,week,status);
            Toast.makeText(this, "Attendance marked for "+ sname, Toast.LENGTH_SHORT).show();
            sidBox.setText("");
            snameBox.setText("");
            checkBox.setChecked(false);
        });

    }
}