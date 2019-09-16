package com.example.androidpreferenceexample;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.Preference;
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
    String spraakKode;

    private static final String NOKKEL_SPRAAKKODE = "spraakKode_nokkel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
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




    //TRENGER DENNE HER FOR Å LA VALGT SPRÅK VÆRE MED FRA START
    public void setLocale(String lang) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration cf = res.getConfiguration();
        cf.setLocale(new Locale(lang));
        res.updateConfiguration(cf,dm);
    }

    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("APP_INFO", MODE_PRIVATE);
        String spraak = prefs.getString(NOKKEL_SPRAAKKODE,"");

        setLocale(spraak);
    }



    //lagrer innholdet i teksten - for å beholde til rotasjon av skjermen
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //husker tall
        outState.putString(NOKKEL_SPRAAKKODE, spraakKode);

        super.onSaveInstanceState(outState);
    }

    //henter den lagrede informasjonen
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        //restor tall
        spraakKode = savedInstanceState.getString(NOKKEL_SPRAAKKODE);

        super.onRestoreInstanceState(savedInstanceState);
    }





    //trenger disse slik at statestikken forblir slettet etter nullstill metoden
    @Override
    protected void onPause(){
        super.onPause();

        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putString(NOKKEL_SPRAAKKODE, spraakKode).apply();

    }

    @Override
    protected void onResume(){
        super.onResume();

        spraakKode = getSharedPreferences("APP_INFO",MODE_PRIVATE).getString(NOKKEL_SPRAAKKODE,"");
    }



}
