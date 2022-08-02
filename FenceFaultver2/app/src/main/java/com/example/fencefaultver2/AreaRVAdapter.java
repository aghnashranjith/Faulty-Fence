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

public class AreaRVAdapter extends RecyclerView.Adapter<AreaRVAdapter.ViewHolder> {

    // variable for our array list and context
    private ArrayList<NodeModal> NodeModalArrayList;
    private Context context;

    // constructor
    public AreaRVAdapter(ArrayList<NodeModal> NodeModalArrayList, Context context) {
        this.NodeModalArrayList = NodeModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.area_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        NodeModal modal = NodeModalArrayList.get(position);
        holder.AreaTVMain.setText(modal.getArea());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AreaCurrentStatus.class);

                i.putExtra("AreaName", modal.getArea());
//                i.putExtra("totalNoOfAreaNodes", modal.getItem)

                context.startActivity(i);


            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return NodeModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView AreaTVMain;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            AreaTVMain = itemView.findViewById(R.id.AreaTVMain);
        }
    }
}
