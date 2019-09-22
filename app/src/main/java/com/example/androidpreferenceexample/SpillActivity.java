package com.example.androidpreferenceexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.androidpreferenceexample.Fragementer.AvbrytDialogFragment;
import com.example.androidpreferenceexample.Fragementer.FullfortSpillDialogFragment;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class SpillActivity extends AppCompatActivity implements FullfortSpillDialogFragment.DialogClickListener, AvbrytDialogFragment.DialogClickListener {


    //--------DIALOG KNAPPER TIL FULLFORTSPILLDIALOGFRAGMENT--------
    @Override
    public void fullfortSpillClick() {
        lagreResultat(); //lagrer ved klikk fullført
        finish(); //lukker activity
    }

    //--------DIALOG KNAPPER TIL AVBRYTDIALOGFRAGMENT--------
    @Override
    public void jaClick() {
        finish();
    }

    @Override
    public void neiClick() {
        Toast.makeText(getApplicationContext(),R.string.fortsett,Toast.LENGTH_LONG).show();
        return;
    }


    //--------VIWES--------
    private TextView tellerSpr, antallRiktige, antallFeil, antalletTotal, sporsmaalet, fasit,  svarFr;

    //--------KNAPPER--------
    private Button svarKnapp, avbrytKnapp, knapp0, knapp1, knapp2, knapp3, knapp4, knapp5,knapp6,knapp7,knapp8,knapp9, knappNullstill;

    //--------ARRAYS FRA RESOURCE--------
    private String[] matteSpr, matteSvar;

    //--------ARRAY TIL LAGRING AV BRUKTE INDEKSER--------
    ArrayList<Integer> brukteSpr = new ArrayList<Integer>();

    //--------DIV TALL OG INDEKSER--------
    int antTeller = 0, antFeilInt, antRiktigInt, antallFraPref, indeksR=0; //autogenereres senere


    //--------LAGRINGSKODER--------
    //strenger
    private static final String NOKKEL_ANTTELLER = "antTeller_nokkel";
    private static final String NOKKEL_ANTRIKTIG = "antRiktig_nokkel";
    private static final String NOKKEL_ANTFEIL = "antFeil_nokkel";
    private static final String NOKKEL_SPORSMAAL = "sporsmaal_nokkel";
    private static final String NOKKEL_FASIT = "fasit_nokkel";
    //tall
    private static final String NOKKEL_INDEKSR= "indeksR_nokkel";
    private static final String NOKKEL_ANTTELLERINT = "antTellerINT_nokkel";
    private static final String NOKKEL_ANTRIKTIGINT = "antRiktigINT_nokkel";
    private static final String NOKKEL_ANTFEILINT = "antFeilINT_nokkel";
    //svarforsøk og brukte nøkler-array
    private static final String NOKKEL_SVARFR = "svarForsok_nokkel";
    private static final String NOKKEL_BRUKTARRAY = "bruktArray_nokkel";
    //preferanser
    private static final String NOKKEL_PREFERANSESPILL = "preferanseSpill_nokkel";
    private static final String NOKKEL_SPRAAKKODE = "spraakKode_nokkel";


    //-------CREATE STARTER---------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //--------OPPDATERER SPÅKLOCAL FØR INNHOLDET BLIR SATT--------
        getLocale();

        setContentView(R.layout.activity_spill);


        //--------OUTPUT--------
        sporsmaalet = findViewById(R.id.sporsmaal);
        fasit = findViewById(R.id.fasit);
        tellerSpr = findViewById(R.id.antallspr);
        antallRiktige = findViewById(R.id.antRiktig);
        antallFeil = findViewById(R.id.antFeil);
        antalletTotal = findViewById(R.id.totalAntall);


        //--------ARRAY--------
        matteSpr = getResources().getStringArray(R.array.matteSpr);
        matteSvar = getResources().getStringArray(R.array.matteSvar);


        //--------KNAPPER--------
        avbrytKnapp = findViewById(R.id.avbrytKnapp);
        svarKnapp = findViewById(R.id.svarKnapp);

        knapp0 = findViewById(R.id.knapp0);
        knapp1 = findViewById(R.id.knapp1);
        knapp2 = findViewById(R.id.knapp2);
        knapp3 = findViewById(R.id.knapp3);
        knapp4 = findViewById(R.id.knapp4);
        knapp5 = findViewById(R.id.knapp5);
        knapp6 = findViewById(R.id.knapp6);
        knapp7 = findViewById(R.id.knapp7);
        knapp8 = findViewById(R.id.knapp8);
        knapp9 = findViewById(R.id.knapp9);
        knappNullstill = findViewById(R.id.knappNullstill);
        svarFr = findViewById(R.id.svarFr);
        //--------SLUTT KNAPPER--------


        //--------LISTENERS--------
        //Klikk på avbryt
        avbrytKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visAvbrytDialog();
            }
        });
        //Klikk på svar
        svarKnapp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //gir alert/avslutt spill ved antall regnestykker
                if(antTeller <= antallFraPref-1){
                    svarKnapp();
                }
                else {
                    //oppdaterer siste svar og gir melding om ferdig spill
                    svarKnapp();
                    visFullførtDialog();
                }
            }
        });

        //Tallknapper
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
        knappNullstill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                svarFr.setText(null);
            }
        });
        //--------SLUTT LISTENERS--------


        //--------HENTER LAGRET RESULTAT FRA PREFERANSER FRAGMENTET--------
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //setter totalantall
        antallFraPref = Integer.parseInt(sharedPreferences.getString("spill","5")); //5 er her som standard ved ny innstallering
        antalletTotal.setText(String.valueOf(antallFraPref));


        //--------SETTER FØRSTE OPPSETT AV SPILL-SKJERMBILDET--------
        if(antTeller == 0) { //første generering av spill
            setOppsett ();
        }

    }
    //-------CREATE SLUTTER---------



    //-------VISER EGENDEFINERT DIALOG VEL FULLFØRING AV SPILL---------
    private void visFullførtDialog() {
        DialogFragment dialog = new FullfortSpillDialogFragment();
        dialog.show(getFragmentManager(), "Avslutt");
    }


    //-------VISER DIALOG VED AVBRYT---------
    private void visAvbrytDialog() {
        DialogFragment dialog = new AvbrytDialogFragment();
        dialog.show(getFragmentManager(), "Avslutt");
    }


    //-------VISER DIALOG VED TILBAKEKNAPP---------
    @Override
    public void onBackPressed() {
        visAvbrytDialog();
    }


    //-------METODE FOR SVAR---------
    public void svarKnapp() {
        if (svarFr.getText().toString().equals("")) { //kontrollerer at svar ikke er tom
            Toast.makeText(SpillActivity.this, R.string.feilInput, Toast.LENGTH_SHORT).show();
        }
        else {
            try { //benytter en try her for å fange exceptions under hvis bruker taster inn for mange tall

                //svar fra array
                long riktigSvar = Integer.parseInt(matteSvar[indeksR]);
                //svar fra bruker
                long brukerSvar = Integer.parseInt(svarFr.getText().toString());
                if (brukerSvar == riktigSvar) {
                    fasit.setText(R.string.riktigSvar);
                    antRiktigInt++;
                }
                else {
                    String msg = " "+riktigSvar;
                    fasit.setText(R.string.feilSvar);
                    fasit.append(msg); //legger til hva riktig svar var
                    antFeilInt++;
                }
                //setter oppsettet på nytt
                setOppsett ();
            }
            //slår inn hvis bruker prøver å tase inn for mange tall
            catch (NumberFormatException e) {
                long riktigSvar = Integer.parseInt(matteSvar[indeksR]);
                String msg = " "+riktigSvar;
                fasit.setText(R.string.feilSvar);
                fasit.append(msg); //legger til hva riktig svar var
                antFeilInt++;

                setOppsett ();
            }
        }
    }


    //-------METODE FOR Å LAGRE RESTULTAT---------
    private void lagreResultat() {
        //henter fra disk
        int antRiktigIntStatistikk = getSharedPreferences("APP_INFO",MODE_PRIVATE).getInt(NOKKEL_ANTRIKTIGINT,0);
        int antFeilIntStatistikk = getSharedPreferences("APP_INFO",MODE_PRIVATE).getInt(NOKKEL_ANTFEILINT,0);

        //plusser gammel lagring fra disk med nye resultater
        antRiktigInt += antRiktigIntStatistikk;
        antFeilInt += antFeilIntStatistikk;

        //ny lagring til disk
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putInt(NOKKEL_ANTRIKTIGINT, antRiktigInt).apply();
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putInt(NOKKEL_ANTFEILINT, antFeilInt).apply();
    }


    //-------SJEKK OM TALL/INDEKS ER BRUKT TIDLIGERE (SPØRSMÅL)---------
    private boolean sjekkTall(int indeksR) {
        boolean sjekk = false;
        //går gjennom arrayet som er fylt med brukte indekser
        for (int i : brukteSpr) {
            if (i == indeksR) {
                sjekk = true; //finnes i arrayet
                break;
            }
        }
        return sjekk;
    }


    //-------GENERERER RANDOMISERT TALL MELLOM 0-24 (STR PÅ MATTEARRAYET)---------
    private int genererRandom() {
        Random r = new Random();
        indeksR = r.nextInt(24+1); //[min = 0, max = 24]

        return indeksR;
    }


    //-------OPPSETT AV SPILL-SKJERMBILDET---------
    private void setOppsett () {
        //randomiserer første indeks
        if(antTeller < antallFraPref) { //denne er er for å unngå at det genereres enda ett tall i arrayet. Unngår index outofbound

            if(indeksR == 0) {
                indeksR = genererRandom();
            }

            //kontrollerer at generert indeks ikke er i array fra tidligere
            Boolean funnet = true;
            while (funnet) {
                if(!sjekkTall(indeksR)) { //tallet finnes ikke og vi kan hoppe ut
                    funnet = false;
                }
                else {
                    genererRandom(); //genererer på nytt om det finnes
                }
            }

            if(!funnet) {
                //legger random indeks inn i bruktarray til samling av disse
                brukteSpr.add(indeksR);

                //Random spørsmålet som kommer - hentet fra spørsmål-array
                sporsmaalet.setText(matteSpr[indeksR]);

                tellerSpr.setText(String.valueOf(antTeller++));
                antallRiktige.setText(String.valueOf(antRiktigInt));
                antallFeil.setText(String.valueOf(antFeilInt));
                svarFr.setText(null);
            }
        }
        else{//oppdaterer siste svar/verdier når spill alert dukker opp, unngår at det dukker opp et ekstra mattestykke man ikke får svart på
            tellerSpr.setText(String.valueOf(antTeller));
            antallRiktige.setText(String.valueOf(antRiktigInt));
            antallFeil.setText(String.valueOf(antFeilInt));
            svarFr.setText(null);
        }
    }

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


    //-------LAGRING AV DATA VED ROTASJON---------
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //strenger
        outState.putString(NOKKEL_ANTTELLER, tellerSpr.getText().toString());
        outState.putString(NOKKEL_ANTRIKTIG, antallRiktige.getText().toString());
        outState.putString(NOKKEL_ANTFEIL, antallFeil.getText().toString());
        outState.putString(NOKKEL_SPORSMAAL, sporsmaalet.getText().toString());
        outState.putString(NOKKEL_FASIT, fasit.getText().toString());
        //tall
        outState.putInt(NOKKEL_ANTTELLERINT, antTeller);
        outState.putInt(NOKKEL_ANTRIKTIGINT, antRiktigInt);
        outState.putInt(NOKKEL_ANTFEILINT, antFeilInt);
        outState.putInt(NOKKEL_PREFERANSESPILL , antallFraPref);
        outState.putInt(NOKKEL_INDEKSR, indeksR);
        //array
        outState.putString(NOKKEL_SVARFR,svarFr.getText().toString());
        outState.putIntegerArrayList(NOKKEL_BRUKTARRAY, brukteSpr);

        super.onSaveInstanceState(outState);
    }


    //-------HENTING AV LAGRET DATA---------
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        //strenger
        tellerSpr.setText(savedInstanceState.getString(NOKKEL_ANTTELLER));
        antallRiktige.setText(savedInstanceState.getString(NOKKEL_ANTRIKTIG));
        antallFeil.setText(savedInstanceState.getString(NOKKEL_ANTFEIL));
        sporsmaalet.setText(savedInstanceState.getString(NOKKEL_SPORSMAAL));
        fasit.setText(savedInstanceState.getString(NOKKEL_FASIT));
        //tall
        antTeller = savedInstanceState.getInt(NOKKEL_ANTTELLERINT);
        antRiktigInt = savedInstanceState.getInt(NOKKEL_ANTRIKTIGINT);
        antFeilInt = savedInstanceState.getInt(NOKKEL_ANTFEILINT);
        antallFraPref = savedInstanceState.getInt(NOKKEL_PREFERANSESPILL);
        indeksR = savedInstanceState.getInt(NOKKEL_INDEKSR);
        //array
        svarFr.setText((savedInstanceState.getString(NOKKEL_SVARFR)));
        brukteSpr = savedInstanceState.getIntegerArrayList(NOKKEL_BRUKTARRAY);

        super.onRestoreInstanceState(savedInstanceState);
    }
}
