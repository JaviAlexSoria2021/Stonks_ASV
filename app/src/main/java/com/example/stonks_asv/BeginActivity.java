package com.example.stonks_asv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BeginActivity extends AppCompatActivity {
    private Button btnProfile;
    private Button btnGame1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);

        btnProfile = (Button) findViewById(R.id.btnProfile);
        btnGame1 = (Button) findViewById(R.id.btnGame1);

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BeginActivity.this, ProfileActivity.class));
            }
        });
        btnGame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BeginActivity.this, Game1.class));
            }
        });
    }


}