package com.example.dutyplanner.presentation.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dutyplanner.R;

public class EnterChooseActivity extends AppCompatActivity {

    //компоненты
    private Button userEnterButton;
    private Button adminEnterButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_choose);

        userEnterButton = findViewById(R.id.userEnterButton);
        adminEnterButton = findViewById(R.id.adminEnterButton);

        Intent intent = new Intent(EnterChooseActivity.this, EnterActivity.class);

        userEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("EnterType", "User");
                startActivity(intent);
            }
        });
        adminEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("EnterType", "Admin");
                startActivity(intent);
            }
        });

    }
}