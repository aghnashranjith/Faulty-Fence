package com.example.fencefaultver2;
//ABOUT
//This app currently reads 4 lines of message from the hardcoded number +918078725250 and logs it into the database

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
//TODO
//Multiple Notification and its sound

public class MainActivity extends AppCompatActivity {

    //creating variables for buttons, database handlers, etc
    private Button logsButton, SettingsButton;
    private ArrayList<NodeModal> nodeModalArrayList, nodeModalArrayListOfDistinctArea;
    private DBHandler dbHandler;
    private AreaRVAdapter areaRVAdapter;
    private RecyclerView areaRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nodeModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(MainActivity.this);

        nodeModalArrayList = dbHandler.readNodes2();
        nodeModalArrayListOfDistinctArea = FilterDistinctArea(nodeModalArrayList);
//        nodeModalArrayListOfDistinctArea = dbHandler.readNodes2();

        areaRVAdapter = new AreaRVAdapter(nodeModalArrayListOfDistinctArea, MainActivity.this);
        areaRV = findViewById(R.id.OneAreaRV);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        areaRV.setLayoutManager(linearLayoutManager);

        areaRV.setAdapter(areaRVAdapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 1000);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
        }

        logsButton = findViewById(R.id.logsButton);
        SettingsButton = findViewById(R.id.Settings);

        logsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //opening new activity
                Intent intent = new Intent(MainActivity.this, ViewLogs.class);
                startActivity(intent);
            }
        });

        SettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                        startActivity(intent);
            }
        });

    }

    // A method to find all the distinct areas and log it into database 2 FenceAreaData so as to display current statuses of the areas and their nodes
    private ArrayList<NodeModal> FilterDistinctArea(ArrayList<NodeModal> nodeModalArrayList) {

        nodeModalArrayListOfDistinctArea = new ArrayList<NodeModal>();

//        int sizeOfNodeModalArrayListOfDistinctArea = 0;
        for (int i = 0; i < nodeModalArrayList.size(); i++) {
            NodeModal modal = nodeModalArrayList.get(i);
            int flag = 0;
            for (int j = 0; j < nodeModalArrayListOfDistinctArea.size(); j++) {
                NodeModal modalOfDistinctArea = nodeModalArrayListOfDistinctArea.get(j);
                if(modal.getArea().equals(modalOfDistinctArea.getArea())){
                    flag = 1;
                    break;                }
            }
            if(flag == 0) {
                nodeModalArrayListOfDistinctArea.add(modal);
            }
        }
        return nodeModalArrayListOfDistinctArea;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS Permission granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        if (requestCode == 1001) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Permission granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}