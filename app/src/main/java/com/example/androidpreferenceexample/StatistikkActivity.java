package com.example.androidpreferenceexample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class StatistikkActivity extends AppCompatActivity {

    //brukes til overføring av statistikk
    private static final String NOKKEL_ANTRIKTIGINT = "antRiktigINT_nokkel";
    private static final String NOKKEL_ANTFEILINT = "antFeilINT_nokkel";
    private static final String NOKKEL_SPRAAKKODE = "spraakKode_nokkel";

    int antFeilInt;
    int antRiktigInt;

    Button tilbakeKnapp, slettStatistikkKnapp;
    TextView totalAntR, totalAntF;
    int antFeilFraSpill,antRiktigFraSpill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(); //forhindrer at språk går tilbake til standard ved rotasjon
        setContentView(R.layout.activity_statistikk);


        //riktig/feil output
        totalAntR = (TextView) findViewById(R.id.totalAntRiktig);
        totalAntF = (TextView) findViewById(R.id.totalAntFeil);

        //setter antallet i statistikk
        setNewNumbers();

        //avbryt
        tilbakeKnapp = (Button)findViewById(R.id.tilbake);
        //slett
        slettStatistikkKnapp = (Button)findViewById(R.id.slettStatistikk);

        //klikk tilbake til start etter valg
        tilbakeKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_spill = new Intent (StatistikkActivity.this,MainActivity.class);
                startActivity(intent_spill);
            }
        });

        //slett statistikk
        slettStatistikkKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slettDialog();
            }
        });


    }

    //popup advarsel ved avbryt
    private void slettDialog() {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(StatistikkActivity.this);
        aBuilder.setTitle(R.string.slettStatistikken);

        aBuilder.setMessage(R.string.slettMsg);

        aBuilder.setNegativeButton(R.string.nei, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),R.string.avbrotSlettMsg,Toast.LENGTH_LONG).show();
                //dialogInterface.cancel();
            }
        });

        aBuilder.setPositiveButton(R.string.ja, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),R.string.statistikkSlettet,Toast.LENGTH_LONG).show();
                //nullstiller statistikken
                nullStill();
            }
        });

        AlertDialog aDialog = aBuilder.create();
        //show alert dialog
        aDialog.show();
    }


    private void setNewNumbers () {
        //SETTER VANSKELIGHETSGRAD ANTALLLET

        //henter fra disk --- > DISSE VERDIENE SOM BLIR STANDARDVERIDER VED NY NEDLASTNING
        antRiktigInt = getSharedPreferences("APP_INFO",MODE_PRIVATE).getInt(NOKKEL_ANTRIKTIGINT,55);
        antFeilInt = getSharedPreferences("APP_INFO",MODE_PRIVATE).getInt(NOKKEL_ANTFEILINT,11);

        //setter textviewet
        totalAntR.setText(String.valueOf(antRiktigInt));
        totalAntF.setText(String.valueOf(antFeilInt));
    }

    //nullstiller statestikken
    public void nullStill() {
        antFeilInt = 0;
        antRiktigInt = 0;

        totalAntR.setText(String.valueOf(antRiktigInt));
        totalAntF.setText(String.valueOf(antFeilInt));
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
        outState.putInt(NOKKEL_ANTRIKTIGINT, antRiktigInt);
        outState.putInt(NOKKEL_ANTFEILINT, antFeilInt);

        //outState.putString(NOKKEL_SPRAAKKODE, spraakKode);

        super.onSaveInstanceState(outState);
    }

    //henter den lagrede informasjonen
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        //restor tall
        antRiktigInt = savedInstanceState.getInt(NOKKEL_ANTRIKTIGINT);
        antFeilInt = savedInstanceState.getInt(NOKKEL_ANTFEILINT);

        //spraakKode = savedInstanceState.getString(NOKKEL_SPRAAKKODE);

        super.onRestoreInstanceState(savedInstanceState);
    }


    //trenger disse slik at statestikken forblir slettet etter nullstill metoden
    @Override
    protected void onPause(){
        super.onPause();

        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putInt(NOKKEL_ANTRIKTIGINT, antRiktigInt).apply();
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putInt(NOKKEL_ANTFEILINT, antFeilInt).apply();
    }

    @Override
    protected void onResume(){
        super.onResume();

        antRiktigInt = getSharedPreferences("APP_INFO",MODE_PRIVATE).getInt(NOKKEL_ANTRIKTIGINT,6);
        antFeilInt = getSharedPreferences("APP_INFO",MODE_PRIVATE).getInt(NOKKEL_ANTFEILINT,6);
    }
}

