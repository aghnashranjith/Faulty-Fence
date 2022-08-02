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

public class phoneNumberRVAdapter extends RecyclerView.Adapter<phoneNumberRVAdapter.ViewHolder> {

    // variable for our array list and context
    private ArrayList<PhoneModal> PhoneModalArrayList;
    private Context context;

    // constructor
    public phoneNumberRVAdapter(ArrayList<PhoneModal> PhoneModalArrayList, Context context) {
        this.PhoneModalArrayList = PhoneModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_number_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.

        PhoneModal modal = PhoneModalArrayList.get(position);
        holder.NumberTV.setText(modal.getPhoneNumber());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, UpdatePhoneNumberActivity.class);

                i.putExtra("number", modal.getPhoneNumber());

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return PhoneModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView NumberTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            NumberTV = itemView.findViewById(R.id.phoneNumberTV);
        }
    }
}
