package com.example.androidpreferenceexample;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button spillKnapp;
    Button statistikkKnapp;
    Button preferanserKnapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //spill knapp
        spillKnapp = findViewById(R.id.startspill);
        spillKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_startspill = new Intent (MainActivity.this,SpillActivity.class);
                startActivity(intent_startspill);
            }
        });

        //statistikk knapp
        statistikkKnapp = findViewById(R.id.statistikk);
        statistikkKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_statistikk = new Intent (MainActivity.this,StatistikkActivity.class);
                startActivity(intent_statistikk);
            }
        });

        //preferanser knapp
        preferanserKnapp = findViewById(R.id.preferanser);
        preferanserKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_preferanser = new Intent (MainActivity.this,PreferanserActivity.class);
                startActivity(intent_preferanser);
            }
        });
    }

}
