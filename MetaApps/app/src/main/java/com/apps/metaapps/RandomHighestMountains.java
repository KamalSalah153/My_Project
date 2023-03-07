package com.apps.metaapps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class RandomHighestMountains extends AppCompatActivity {

    TextView txRandomMountain;
    Button btnGenrateRandommountain;
    ImageView imgMountain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_highest_mountains);

        txRandomMountain=findViewById(R.id.txt_Randommountain);
        btnGenrateRandommountain=findViewById(R.id.btn_GenrateRandomMountain);
        imgMountain=findViewById(R.id.img_mountain);

        btnGenrateRandommountain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mountainNames[]={"Alps","Everest","colorado","mo2tam","Gapl Tarek"};
                Random random=new Random();
                int index=random.nextInt(mountainNames.length);
                txRandomMountain.setText(mountainNames[index]);

                if (index==0)
                 imgMountain.setImageDrawable(getDrawable(R.drawable.m1));
                else if(index==1)
                 imgMountain.setImageDrawable(getDrawable(R.drawable.m2));
                else if(index==2)
                    imgMountain.setImageDrawable(getDrawable(R.drawable.m3));
                else if(index==3)
                    imgMountain.setImageDrawable(getDrawable(R.drawable.mountain));
                else if(index==4)
                    imgMountain.setImageDrawable(getDrawable(R.drawable.m5));
            }
        });

    }
}