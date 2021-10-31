package com.example.findhospital.ui.list;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.findhospital.R;

public class PhoneActivity extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    private EditText mEditTextNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        mEditTextNumber = findViewById(R.id.edit_text_number);

        ImageView imageCall = findViewById(R.id.image_call);

        //imageCall.setOnClickListener(v -> makePhoneCall());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }
            else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT);
            }
        }
    }

    public void makePhoneCall() {
        String number = mEditTextNumber.getText().toString();
        if(number.trim().length() > 0){
            if(ContextCompat.checkSelfPermission(PhoneActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(PhoneActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            }
            else
            {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
        else
        {
            Toast.makeText(PhoneActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();

        }
    }

    public void makePhoneCall(View view) {
        String number = mEditTextNumber.getText().toString();
        if(number.trim().length() > 0){
            if(ContextCompat.checkSelfPermission(PhoneActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(PhoneActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            }
            else
            {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
        else
        {
            Toast.makeText(PhoneActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();

        }
    }
}
