package com.example.fencefaultver2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
    CardView cardView;
    Switch NotiSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        cardView = findViewById(R.id.AddGSMCard);
        NotiSwitch = findViewById(R.id.NotiSwitch);
        SharedPreferences pref = getSharedPreferences("MySharedPref", MODE_PRIVATE);
//        boolean getAllNoti = sh.getBoolean("A",false);
        NotiSwitch.setChecked(pref.getBoolean("A", false));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, AddOrChange.class);
                startActivity(intent);
            }
        });

        NotiSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getSharedPreferences("MySharedPref", MODE_PRIVATE);
//
                SharedPreferences.Editor myEdit = pref.edit();
//
//                boolean ischeck = false;
//                myEdit.putBoolean("AllNoti", !ischeck );
////                myEdit.commit();
//                NotiSwitch.setChecked(!ischeck);
//
                if(NotiSwitch.isChecked())
                {
                    Toast.makeText(Settings.this, "You will receive all notifications", Toast.LENGTH_SHORT).show();
                    myEdit.putBoolean("A", true);
//                    receiveAll = true;
                }
                else
                {
                    Toast.makeText(Settings.this, "You will only receive not working notifications", Toast.LENGTH_SHORT).show();
                    myEdit.putBoolean("A", false);
                }
                myEdit.apply();
            }

        });

    }
}