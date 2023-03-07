package com.apps.metaapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnCalApp,btnRandomHighestMountain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnCalApp=findViewById(R.id.btn_calApp);
        btnRandomHighestMountain=findViewById(R.id.btn_RandomHighestMountain);

        btnRandomHighestMountain.setOnClickListener(this);
        btnCalApp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_calApp:
                Intent goToCal=new Intent(HomeActivity.this,CalculatorActivity.class);
                startActivity(goToCal);break;
            case R.id.btn_RandomHighestMountain:
                Intent goToMoutain=new Intent(HomeActivity.this,RandomHighestMountains.class);
               startActivity(goToMoutain);break;
        }
    }
}