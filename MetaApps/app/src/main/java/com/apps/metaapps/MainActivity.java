package com.apps.metaapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txforgetpassword,txRegister;
    Button btnLogin;
    boolean pressedBack=false;


    @Override
    public void onBackPressed() {
        if (pressedBack) {
            super.onBackPressed();
        } else {
            pressedBack = true;
            Toast.makeText(this, "Press Again TO Exit", Toast.LENGTH_SHORT).show();
 
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                pressedBack=false;
                }
            },3000);
            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pressedBack = false;
                }
            }).start();*/
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txforgetpassword=findViewById(R.id.txt_forgetpassword);
        txRegister=findViewById(R.id.txt_register);
        btnLogin=findViewById(R.id.btnLogin);

        txforgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToForgetPassword=new Intent(MainActivity.this,ForgetActivity.class);
                startActivity(goToForgetPassword);
            }
        });

        txRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRgister=new Intent(MainActivity.this,Register.class);
                startActivity(goToRgister);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Welcome to The Meta App", Toast.LENGTH_SHORT).show();
                Intent goToHome=new Intent(MainActivity.this,HomeActivity.class);
                startActivity(goToHome);
            }
        });

    }
}