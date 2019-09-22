package com.example.s304114mappe1;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import com.example.s304114mappe1.Fragementer.PreferanserFragment;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //--------KNAPPER-------
    Button spillKnapp, statistikkKnapp, preferanserKnapp;

    //--------LAGRINGSKODE--------
    private static final String NOKKEL_SPRAAKKODE = "spraakKode_nokkel";


    //-------CREATE STARTER---------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //--------OPPDATERER SPÅKLOCAL FØR INNHOLDET BLIR SATT--------
        getLocale();

        setContentView(R.layout.activity_main);


        //--------KNAPPER--------
        spillKnapp = findViewById(R.id.startspill);
        statistikkKnapp = findViewById(R.id.statistikk);
        preferanserKnapp = findViewById(R.id.preferanser);
        //--------SLUTT KNAPPER--------


        //--------LISTENERS--------
        spillKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_startspill = new Intent (MainActivity.this,SpillActivity.class);
                startActivity(intent_startspill);
            }
        });
        statistikkKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_statistikk = new Intent (MainActivity.this,StatistikkActivity.class);
                startActivity(intent_statistikk);
            }
        });
        preferanserKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_preferanser = new Intent (MainActivity.this, PreferanserFragment.class);
                startActivity(intent_preferanser);
                finish(); //bruker finish her fordi vi kommer tilbake med en ny intent fra preferanser for å oppdatere språk - unngå å legge på stack
            }
        });
        //--------SLUTT LISTENERS--------
    }
    //-------CREATE SLUTTER---------


    //-------SETTER SPRÅK LOCALE---------
    public void setLocale(String spraak) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration cf = res.getConfiguration();
        cf.setLocale(new Locale(spraak));
        res.updateConfiguration(cf,dm);
    }


    //-------GETTER SPRÅK LOCALE---------
    public void getLocale() {
        SharedPreferences preferanser = getSharedPreferences("APP_INFO", MODE_PRIVATE);
        String spraak = preferanser.getString(NOKKEL_SPRAAKKODE,"");

        setLocale(spraak);
    }
}
