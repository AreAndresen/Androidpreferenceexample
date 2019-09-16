package com.example.androidpreferenceexample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class SpillActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;


    //lagringskode til preferanse
    private static final String NOKKEL_PREFERANSESPILL = "preferanseSpill_nokkel";
    private static final String NOKKEL_SPRAAKKODE = "spraakKode_nokkel";

    //til spillet
    TextView tellerSpr, antallRiktige, antallFeil, antalletTotal, sporsmaalet, fasit;
    Button svarKnapp, avbrytKnapp;

    //ny design
    Button knapp0, knapp1, knapp2, knapp3, knapp4, knapp5,knapp6,knapp7,knapp8,knapp9, knappNullstill, knappMinus;
    TextView svarFr;
    private int svarFrInt;
    private final char minus = '-';
    private double svarVerdi;
    private char ACTION;


    //matte arrayene fra resources
    String[] matteSpr, matteSvar;
    int antTeller;
    int antFeilInt;
    int antRiktigInt;
    int antallFraPref;

    int indeksR=0; //autogenerert

    //spesifiserte nøkler for savestat
    //strenger
    private static final String NOKKEL_ANTTELLER = "antTeller_nokkel";
    private static final String NOKKEL_ANTRIKTIG = "antRiktig_nokkel";
    private static final String NOKKEL_ANTFEIL = "antFeil_nokkel";
    private static final String NOKKEL_SPORSMAAL = "sporsmaal_nokkel";
    private static final String NOKKEL_FASIT = "fasit_nokkel";
    //tall
    private static final String NOKKEL_INDEKSR= "indeksR_nokkel";
    private static final String NOKKEL_ANTTELLERINT = "antTellerINT_nokkel";
    //brukes til overføring av statistikk
    private static final String NOKKEL_ANTRIKTIGINT = "antRiktigINT_nokkel";
    private static final String NOKKEL_ANTFEILINT = "antFeilINT_nokkel";

    //private static final String NOKKEL_ANTTOTALINT = "antTotalINT_nokkel";
    //private static final String NOKKEL_SVAR = "svarForsok_nokkel";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_spill);


        ////--------VARIABLER -----
        sporsmaalet = (TextView) findViewById(R.id.sporsmaal);
        fasit = (TextView) findViewById(R.id.fasit);
        tellerSpr = (TextView) findViewById(R.id.antallspr);
        antallRiktige = (TextView) findViewById(R.id.antRiktig);
        antallFeil = (TextView) findViewById(R.id.antFeil);
        antalletTotal = (TextView) findViewById(R.id.totalAntall);
        //--------SLUTT VARIABLER


        //-------array svar----
        matteSpr = getResources().getStringArray(R.array.matteSpr);
        matteSvar = getResources().getStringArray(R.array.matteSvar);



        //--------KNAPPER-----
        knapp0 = (Button)findViewById(R.id.knapp0);
        knapp1 = (Button)findViewById(R.id.knapp1);
        knapp2 = (Button)findViewById(R.id.knapp2);
        knapp3 = (Button)findViewById(R.id.knapp3);
        knapp4 = (Button)findViewById(R.id.knapp4);
        knapp5 = (Button)findViewById(R.id.knapp5);
        knapp6 = (Button)findViewById(R.id.knapp6);
        knapp7 = (Button)findViewById(R.id.knapp7);
        knapp8 = (Button)findViewById(R.id.knapp8);
        knapp9 = (Button)findViewById(R.id.knapp9);
        knappNullstill = (Button)findViewById(R.id.knappNullstill);
        knappMinus = (Button)findViewById(R.id.knappMinus);
        svarFr = (TextView)findViewById(R.id.svarFr);
        //---slutt design knapper

        knapp0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                svarFr.setText(svarFr.getText().toString() + "0");
            }
        });
        knapp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                svarFr.setText(svarFr.getText().toString() + "1");
            }
        });
        knapp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                svarFr.setText(svarFr.getText().toString() + "2");
            }
        });
        knapp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                svarFr.setText(svarFr.getText().toString() + "3");
            }
        });
        knapp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                svarFr.setText(svarFr.getText().toString() + "4");
            }
        });
        knapp5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                svarFr.setText(svarFr.getText().toString() + "5");
            }
        });
        knapp6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                svarFr.setText(svarFr.getText().toString() + "6");
            }
        });
        knapp7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                svarFr.setText(svarFr.getText().toString() + "7");
            }
        });
        knapp8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                svarFr.setText(svarFr.getText().toString() + "8");
            }
        });
        knapp9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                svarFr.setText(svarFr.getText().toString() + "9");
            }
        });
        knappMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(svarFr == null || svarFr.getText().toString().isEmpty()) { //motvirker bug ved minus etter tall

                    svarFr.setText(svarFr.getText().toString() + minus);
                }
            }
        });
        knappNullstill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                svarFr.setText(null);
            }
        });



        //avbryt
        avbrytKnapp = (Button)findViewById(R.id.avbrytKnapp);
        //ved klipp på avbryt - egen popup metode
        avbrytKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visAvbrytDialog();
            }
        });

        //svar
        svarKnapp = (Button)findViewById(R.id.svarKnapp);
        //ved klikk svar
        svarKnapp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //gir alert /avslutt spill ved antall regnestykker
                if(antTeller < antallFraPref){
                    svarKnapp();
                }
                else {
                    //oppdaterer siste svar og gir melding om ferdig spill
                    svarKnapp();
                    visFullførtDialog();
                }
            }
        });
        //-------- SLUTT KNAPPER-----


        //egen metode - MÅ HA EN IF SOM FUNGERER HER ved rotasjon
        if(indeksR == 0 || indeksR > antallFraPref) {
            setNewNumbers();
        }


    }//utenfor create

    //popup advarsel ved avbryt
    private void visFullførtDialog() {
        AlertDialog.Builder fBuilder = new AlertDialog.Builder(SpillActivity.this);
        fBuilder.setTitle(R.string.spillFullfort);

        fBuilder.setMessage(R.string.fullfortMsg);

        fBuilder.setNegativeButton(R.string.tilbake, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //lagrer statistikk -inneholder intent
                lagreResultat();

                Intent intent_spill = new Intent (SpillActivity.this,MainActivity.class);
                startActivity(intent_spill);
            }
        });
        AlertDialog aDialog = fBuilder.create();
        //show alert dialog
        aDialog.show();
    }

    //popup advarsel ved avbryt
    private void visAvbrytDialog() {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(SpillActivity.this);
        aBuilder.setTitle(R.string.avbryt);

        aBuilder.setMessage(R.string.avbrytMsg);

        aBuilder.setNegativeButton(R.string.nei, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),R.string.fortsett,Toast.LENGTH_LONG).show();
            }
        });

        aBuilder.setPositiveButton(R.string.ja, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent_spill = new Intent (SpillActivity.this,MainActivity.class);
                startActivity(intent_spill);
            }
        });

        AlertDialog aDialog = aBuilder.create();
        aDialog.show();
    }

    //advarsel ved tilbake trykk
    @Override
    public void onBackPressed() {
        visAvbrytDialog();
    }

    //svar metode
    public void svarKnapp() {
        if (svarFr.getText().toString().equals("")) { //kontrollerer at svar ikke er tom
            Toast.makeText(SpillActivity.this, R.string.feilInput, Toast.LENGTH_SHORT).show();
        }
        else {
            //svar fra array
            int riktigSvar = Integer.parseInt(matteSvar[indeksR]);
            //svar fra bruker
            int brukerSvar = Integer.parseInt(svarFr.getText().toString());

            if (brukerSvar == riktigSvar) {
                fasit.setText(R.string.riktigSvar);
                antRiktigInt++;
            }
            else {
                String msg = " "+riktigSvar;
                fasit.setText(R.string.feilSvar);
                fasit.append(msg); //legger til antall feil
                antFeilInt++;
            }
            setNewNumbers();
        }
    }

    //METODE FOR Å LAGRE RESTULTAT---------
    private void lagreResultat() {
        //MINNELAGRING
        //henter fra minne
        int antRiktigIntStatistikk = getSharedPreferences("APP_INFO",MODE_PRIVATE).getInt(NOKKEL_ANTRIKTIGINT,0);
        int antFeilIntStatistikk = getSharedPreferences("APP_INFO",MODE_PRIVATE).getInt(NOKKEL_ANTFEILINT,0);

        //plusser gammel lagring fra minne med nye resultater
        antFeilInt += antRiktigIntStatistikk;
        antRiktigInt += antFeilIntStatistikk;

        //Lagring til minne
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putInt(NOKKEL_ANTRIKTIGINT, antRiktigInt).apply();
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putInt(NOKKEL_ANTFEILINT, antFeilInt).apply();
        //SLUTT MINNELAGRING
    }


    private void setNewNumbers () {
        //randomisert spørsmål
        Random r = new Random();
        indeksR = r.nextInt(25);

        //Spørsmålet som kommer - henter fra spørsmål-array
        sporsmaalet.setText(matteSpr[indeksR]);


        //henter fra disk fra preferansr fragement - fra preference fragment
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        antallFraPref = Integer.parseInt(sharedPreferences.getString("spill",""));


        antalletTotal.setText(String.valueOf(antallFraPref));
        tellerSpr.setText(String.valueOf(antTeller++));
        antallRiktige.setText(String.valueOf(antRiktigInt));
        antallFeil.setText(String.valueOf(antFeilInt));
        svarFr.setText(null);
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

        //husker strenger
        outState.putString(NOKKEL_ANTTELLER, tellerSpr.getText().toString());
        outState.putString(NOKKEL_ANTRIKTIG, antallRiktige.getText().toString());
        outState.putString(NOKKEL_ANTFEIL, antallFeil.getText().toString());
        outState.putString(NOKKEL_SPORSMAAL, sporsmaalet.getText().toString());
        outState.putString(NOKKEL_FASIT, fasit.getText().toString());

        //husker tall
        outState.putInt(NOKKEL_ANTTELLERINT, antTeller);
        outState.putInt(NOKKEL_ANTRIKTIGINT, antRiktigInt);
        outState.putInt(NOKKEL_ANTFEILINT, antFeilInt);
        outState.putInt(NOKKEL_PREFERANSESPILL , antallFraPref);

        outState.putInt("SvarFr",svarFrInt);

        outState.putInt(NOKKEL_INDEKSR, indeksR);

        //outState.putString(NOKKEL_SPRAAKKODE, spraakKode);


        super.onSaveInstanceState(outState);
    }

    //henter den lagrede informasjonen
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        //restor strenger
        tellerSpr.setText(savedInstanceState.getString(NOKKEL_ANTTELLER));
        antallRiktige.setText(savedInstanceState.getString(NOKKEL_ANTRIKTIG));
        antallFeil.setText(savedInstanceState.getString(NOKKEL_ANTFEIL));
        sporsmaalet.setText(savedInstanceState.getString(NOKKEL_SPORSMAAL));
        fasit.setText(savedInstanceState.getString(NOKKEL_FASIT));

        //restor tall
        antTeller = savedInstanceState.getInt(NOKKEL_ANTTELLERINT);
        antRiktigInt = savedInstanceState.getInt(NOKKEL_ANTRIKTIGINT);
        antFeilInt = savedInstanceState.getInt(NOKKEL_ANTFEILINT);
        antallFraPref = savedInstanceState.getInt(NOKKEL_PREFERANSESPILL);
        indeksR = savedInstanceState.getInt(NOKKEL_INDEKSR);

        svarFr.setText((savedInstanceState.getInt("SvarFr")));

        //spraakKode = savedInstanceState.getString(NOKKEL_SPRAAKKODE);

        super.onRestoreInstanceState(savedInstanceState);
    }



}
