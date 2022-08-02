package com.example.fencefaultver2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class ViewLogs extends AppCompatActivity {

    // creating variables for our array list,
    // dbhandler, adapter and recycler view.
    private ArrayList<NodeModal> NodeModalArrayList;
    private DBHandler dbHandler;
    private Button exportButton, clearButton;
    private NodeRVAdapter nodeModalAdapter;
    private RecyclerView LogRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_logs);

        // initializing our all variables.
        NodeModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(ViewLogs.this);

        // getting our course array
        // list from db handler class.
        NodeModalArrayList = dbHandler.readNodes();

        // on below line passing our array lost to our adapter class.
        nodeModalAdapter = new NodeRVAdapter(NodeModalArrayList, ViewLogs.this);
        LogRV = findViewById(R.id.RVLogs);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewLogs.this, RecyclerView.VERTICAL, false);
        LogRV.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        LogRV.setAdapter(nodeModalAdapter);

        exportButton = findViewById(R.id.export);

        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                exportDB();
//                Toast.makeText(ViewLogs.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                DBHandler db = new DBHandler(ViewLogs.this);
                boolean export = db.exportDatabase();
                if(export)
                    Toast.makeText(ViewLogs.this, "Export Succesful!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(ViewLogs.this, "Export Failed!", Toast.LENGTH_SHORT).show();
                
            }
        });

        clearButton = findViewById(R.id.clear);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler db = new DBHandler(ViewLogs.this);
                db.clearLogs();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                Toast.makeText(ViewLogs.this, "Logs all cleared!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
