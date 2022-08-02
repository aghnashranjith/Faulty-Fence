package com.example.fencefaultver2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class AreaCurrentStatus extends AppCompatActivity {

    // creating variables for our array list,
    // dbhandler, adapter and recycler view.
    private ArrayList<NodeModal> NodeModalArrayList, NodeModalArrayListOfArea;
    private DBHandler dbHandler;
    private NodeAdapterForArea nodeAdapterForArea;
    private RecyclerView AreaRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_current_status);

        // initializing our all variables.
        NodeModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(AreaCurrentStatus.this);

        String AreaName = getIntent().getStringExtra("AreaName");

//        NodeModalArrayListOfArea = dbHandler.readAreaNodes(this, AreaName);
        NodeModalArrayList = dbHandler.readNodes2();
        NodeModalArrayListOfArea = FilterAreaNodes(NodeModalArrayList, AreaName);

        // on below line passing our array lost to our adapter class.
        nodeAdapterForArea = new NodeAdapterForArea(NodeModalArrayListOfArea, AreaCurrentStatus.this, AreaName);
        AreaRV = findViewById(R.id.AreaRV);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AreaCurrentStatus.this, RecyclerView.VERTICAL, false);
        AreaRV.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        AreaRV.setAdapter(nodeAdapterForArea);
    }

    private ArrayList<NodeModal> FilterAreaNodes(ArrayList<NodeModal> nodeModalArrayList, String areaName) {
        NodeModalArrayListOfArea = new ArrayList<>();

        for(int position = 0; position < NodeModalArrayList.size(); position++)
        {
            NodeModal modal = NodeModalArrayList.get(position);
            if(modal.getArea().equalsIgnoreCase(areaName))
                NodeModalArrayListOfArea.add(modal);
        }

        return NodeModalArrayListOfArea;
    }
}
