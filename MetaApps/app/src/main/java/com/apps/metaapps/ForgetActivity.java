package com.apps.metaapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ForgetActivity extends AppCompatActivity {

    Button btnResendSms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        btnResendSms=findViewById(R.id.btn_resendSms);
        btnResendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ForgetActivity.this, "the mail was sent", Toast.LENGTH_SHORT).show();
                 Intent goTOhome=new Intent(ForgetActivity.this,MainActivity.class);
                 startActivity(goTOhome);
            }
        });
    }
}