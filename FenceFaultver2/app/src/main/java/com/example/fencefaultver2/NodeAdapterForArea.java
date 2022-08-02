package com.example.fencefaultver2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NodeAdapterForArea extends RecyclerView.Adapter<NodeAdapterForArea.ViewHolder> {

    // variable for our array list and context
    private ArrayList<NodeModal> NodeModalArrayList;
    private Context context;
    private String AreaName;

    // constructor
    public NodeAdapterForArea(ArrayList<NodeModal> NodeModalArrayList, Context context, String Area) {
        this.NodeModalArrayList = NodeModalArrayList;
        this.context = context;
        this.AreaName = Area;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.node_item_for_area, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.

        NodeModal modal = NodeModalArrayList.get(position);
//        holder.AreaTV.setText("Area: "+ modal.getArea());
        if(modal.getArea().equalsIgnoreCase(AreaName)) {
        holder.NodeTV.setText("Node: "+modal.getNode());
        holder.StatusTV.setText("Status: "+modal.getStatus());
        holder.TimeTV.setText("Time: "+modal.getTime());
        holder.BatteryTV.setText("Battery: "+modal.getBattery());
        holder.VoltageTV.setText("Voltage: "+modal.getVoltage()); }

    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return NodeModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView NodeTV, StatusTV, TimeTV, BatteryTV, VoltageTV, AreaTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
//            AreaTV = itemView.findViewById(R.id.AreaTV);
            NodeTV = itemView.findViewById(R.id.NodeTV);
            StatusTV = itemView.findViewById(R.id.StatusTV);
            TimeTV = itemView.findViewById(R.id.TimeTV);
            BatteryTV = itemView.findViewById(R.id.BatteryTV);
            VoltageTV = itemView.findViewById(R.id.VoltageTV);
        }
    }
}
