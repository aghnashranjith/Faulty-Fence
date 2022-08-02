package com.example.fencefaultver2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdatePhoneNumberActivity extends AppCompatActivity {

    private EditText ChangePhoneET;
    private Button updatePhone, deleteButton;
    private DBHandler dbHandler;
    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone_number);

        ChangePhoneET = findViewById(R.id.ChangePhoneET);
        updatePhone = findViewById(R.id.UpdatePhone);
        deleteButton = findViewById(R.id.DeletePhone);

        dbHandler = new DBHandler(UpdatePhoneNumberActivity.this);

        phoneNumber = getIntent().getStringExtra("number");

        ChangePhoneET.setText(phoneNumber);

        updatePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler.updatePhoneNumber(phoneNumber, ChangePhoneET.getText().toString());

                Toast.makeText(UpdatePhoneNumberActivity.this, "Phone Number Updated", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(UpdatePhoneNumberActivity.this, AddOrChange.class);
                startActivity(i);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler.deletePhone(phoneNumber);
                Toast.makeText(UpdatePhoneNumberActivity.this, "Deleted Number", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UpdatePhoneNumberActivity.this, AddOrChange.class);
                startActivity(i);
            }
        });
    }
}