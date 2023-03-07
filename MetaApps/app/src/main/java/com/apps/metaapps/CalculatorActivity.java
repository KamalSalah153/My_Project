package com.apps.metaapps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends AppCompatActivity  implements View.OnClickListener{

        EditText etFirstnumber,etSecondnumber;
        TextView txResult;
        Button btnAdd,btnSub,btnMul,btnDiv,btnResetValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        etFirstnumber=findViewById(R.id.et_firstNumber);
        etSecondnumber=findViewById(R.id.et_SecondNumber);
        txResult=findViewById(R.id.tv_Result);
        btnAdd=findViewById(R.id.btnAddition);
        btnDiv=findViewById(R.id.btnDivision);
        btnMul=findViewById(R.id.btnMultiplcation);
        btnSub=findViewById(R.id.btnSubstraction);
        btnResetValues=findViewById(R.id.btn_ResetValues);

        btnSub.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnMul.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
        btnResetValues.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String fnum=etFirstnumber.getText().toString().trim();
        String snum=etSecondnumber.getText().toString().trim();
        String Res="The Result is : ";
        if(fnum.isEmpty() || snum.isEmpty())
            Toast.makeText(this, "Please Enter The Two Inputs", Toast.LENGTH_SHORT).show();
        else
            {
            double fnumdouble = Double.parseDouble(fnum);
            double snumdouble = Double.parseDouble(snum);

            switch (v.getId()) {
                case R.id.btnAddition:
                    Res += String.valueOf(fnumdouble + snumdouble);
                    txResult.setText(Res);
                    break;
                case R.id.btnDivision:
                    Res += String.valueOf(fnumdouble / snumdouble);
                    txResult.setText(Res);
                    break;
                case R.id.btnMultiplcation:
                    Res += String.valueOf(fnumdouble * snumdouble);
                    txResult.setText(Res);
                    break;
                case R.id.btnSubstraction:
                    Res += String.valueOf(fnumdouble - snumdouble);
                    txResult.setText(Res);
                    break;
                case R.id.btn_ResetValues:
                    etFirstnumber.setText("");
                    etSecondnumber.setText("");
                    break;
            }
        }
    }
}