package com.example.androidpreferenceexample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StatistikkActivity extends AppCompatActivity {

    //brukes til overføring av statistikk
    private static final String NOKKEL_ANTRIKTIGINT = "antRiktigINT_nokkel";
    private static final String NOKKEL_ANTFEILINT = "antFeilINT_nokkel";

    int antFeilInt;
    int antRiktigInt;

    Button tilbakeKnapp, slettStatistikkKnapp;
    TextView totalAntR, totalAntF;
    int antFeilFraSpill,antRiktigFraSpill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        aBuilder.setTitle("Slett statistikk");

        aBuilder.setMessage("Vil du slette all lagret statistikk?");

        aBuilder.setNegativeButton("Nei", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),"Du avbrøt",Toast.LENGTH_LONG).show();
                //dialogInterface.cancel();
            }
        });

        aBuilder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),"Statistikk slettet",Toast.LENGTH_LONG).show();

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

    //lagrer innholdet i teksten - for å beholde til rotasjon av skjermen
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //husker tall
        outState.putInt(NOKKEL_ANTRIKTIGINT, antRiktigInt);
        outState.putInt(NOKKEL_ANTFEILINT, antFeilInt);

        super.onSaveInstanceState(outState);
    }

    //henter den lagrede informasjonen
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        //restor tall
        antRiktigInt = savedInstanceState.getInt(NOKKEL_ANTRIKTIGINT);
        antFeilInt = savedInstanceState.getInt(NOKKEL_ANTFEILINT);

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

