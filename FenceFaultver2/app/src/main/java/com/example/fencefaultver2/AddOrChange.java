package com.example.fencefaultver2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddOrChange extends AppCompatActivity {
    private EditText PhoneEditText;
    private Button addPhoneButton;
    private TextView ClickToAddTV;
    private DBHandler dbHandler;
    private RecyclerView phoneNumbersRV;
    private phoneNumberRVAdapter PhoneNumberRVAdapter;
    private ArrayList<PhoneModal> phoneNumbersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_change);

        PhoneEditText = findViewById(R.id.AddPhoneET);
        addPhoneButton = findViewById(R.id.AddPhone);
        ClickToAddTV = findViewById(R.id.ClickToAddTV);
        phoneNumbersList = new ArrayList<>();
        dbHandler = new DBHandler(AddOrChange.this);

        phoneNumbersList = dbHandler.readPhones();
        if(phoneNumbersList.size()>0)
            ClickToAddTV.setText("Click on Number to Change or Delete");
        else
            ClickToAddTV.setText("");

        PhoneNumberRVAdapter = new phoneNumberRVAdapter(phoneNumbersList,AddOrChange.this);
        phoneNumbersRV = findViewById(R.id.AddedPhonesRV);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddOrChange.this, RecyclerView.VERTICAL, false);
        phoneNumbersRV.setLayoutManager(linearLayoutManager);

        phoneNumbersRV.setAdapter(PhoneNumberRVAdapter);




        addPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String phoneNumber = PhoneEditText.getText().toString();
                //Checking if country code (eg:+91) was entered along with the number
                if(!phoneNumber.startsWith("+91"))
                {
                    Toast.makeText(AddOrChange.this, "Please enter country code (eg: +91)", Toast.LENGTH_SHORT).show();
                    return;
                }

                long error = dbHandler.addPhone(phoneNumber);
                if(error == -1)
                    Toast.makeText(AddOrChange.this, "shit", Toast.LENGTH_SHORT).show();
                Toast.makeText(AddOrChange.this, "Phone number " + phoneNumber + " succesfully added", Toast.LENGTH_SHORT).show();
                PhoneEditText.setText("");
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });

    }

//    private ArrayList<PhoneModal> getAreas(ArrayList<PhoneModal> phoneNumbersList) {
//        ArrayList<String> phoneNumbers = new ArrayList<>();
//
//        for (int i = 0; i < phoneNumbersList.size(); i++) {
//            phoneNumbers.add(phoneNumbersList.get(i).getPhoneNumber());
//        }
//        return phoneNumbers;
//    }
}