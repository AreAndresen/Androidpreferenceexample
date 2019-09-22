package com.example.s304114mappe1;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.s304114mappe1.Fragementer.SlettStatistikkDialogFragment;
import java.util.Locale;

public class StatistikkActivity extends AppCompatActivity implements SlettStatistikkDialogFragment.DialogClickListener {


    //--------DIALOG KNAPPER TIL SLETTSTATISTIKKDIALOG--------
    @Override
    public void jaClick() {
        nullStill();
        Toast.makeText(getApplicationContext(),R.string.statistikkSlettet,Toast.LENGTH_LONG).show();
    }

    @Override
    public void neiClick() {
        Toast.makeText(getApplicationContext(),R.string.avbrotSlettMsg,Toast.LENGTH_LONG).show();
        return;
    }

    //--------LAGRINGSKODER--------
    //feil/riktig
    private static final String NOKKEL_ANTRIKTIGINT = "antRiktigINT_nokkel";
    private static final String NOKKEL_ANTFEILINT = "antFeilINT_nokkel";
    //preferanser
    private static final String NOKKEL_SPRAAKKODE = "spraakKode_nokkel";

    //--------DIV TALL OG INDEKSER--------
    int antFeilInt, antRiktigInt;

    //--------KNAPPER--------
    Button tilbakeKnapp, slettStatistikkKnapp;

    //--------VIWES--------
    TextView totalAntR, totalAntF;


    //-------CREATE STARTER---------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //--------OPPDATERER SPÅKLOCAL FØR INNHOLDET BLIR SATT--------
        getLocale();

        setContentView(R.layout.activity_statistikk);


        //--------OUTPUT--------
        totalAntR = findViewById(R.id.totalAntRiktig);
        totalAntF = findViewById(R.id.totalAntFeil);

        //--------KNAPPER--------
        tilbakeKnapp = findViewById(R.id.tilbake);
        slettStatistikkKnapp = findViewById(R.id.slettStatistikk);


        //--------LISTENERS--------
        tilbakeKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); //bruker finish så activity ikke legger seg på stack
            }
        });
        slettStatistikkKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slettDialog();
            }
        });
        //--------SLUTT LISTENERS--------


        //--------SETTER FØRSTE OPPSETT AV STATISTIKK-SKJERMBILDET--------
        setOppsett();
    }
    //-------CREATE SLUTTER---------


    //-------VISER DIALOG VED TRYKK PÅ SLETT---------
    private void slettDialog() {
        DialogFragment dialog = new SlettStatistikkDialogFragment();
        dialog.show(getFragmentManager(), "Slett");
    }

    //-------TILBAKEKNAPP LUKKER ACTIVITY---------
    @Override
    public void onBackPressed() {
        finish();
    }


    //-------OPPSETT AV STATISTIKK-SKJERMBILDET---------
    private void setOppsett () {
        //henter fra disk - samt setter standardverdiene 0 ved første kjøring av spill
        antRiktigInt = getSharedPreferences("APP_INFO",MODE_PRIVATE).getInt(NOKKEL_ANTRIKTIGINT,0);
        antFeilInt = getSharedPreferences("APP_INFO",MODE_PRIVATE).getInt(NOKKEL_ANTFEILINT,0);

        //setter textviewet
        totalAntR.setText(String.valueOf(antRiktigInt));
        totalAntF.setText(String.valueOf(antFeilInt));
    }


    //-------NULLSTILLER STATISTIKK---------
    public void nullStill() {
        antFeilInt = 0;
        antRiktigInt = 0;

        totalAntR.setText(String.valueOf(antRiktigInt));
        totalAntF.setText(String.valueOf(antFeilInt));
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
        outState.putInt(NOKKEL_ANTRIKTIGINT, antRiktigInt);
        outState.putInt(NOKKEL_ANTFEILINT, antFeilInt);

        super.onSaveInstanceState(outState);
    }


    //-------HENTING AV LAGRET DATA---------
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        //restor tall
        antRiktigInt = savedInstanceState.getInt(NOKKEL_ANTRIKTIGINT);
        antFeilInt = savedInstanceState.getInt(NOKKEL_ANTFEILINT);

        super.onRestoreInstanceState(savedInstanceState);
    }


    //-------SØRGER FOR AT STATISTIKK FORBLIR NULLSTILT OM DETTE ER TILFELLE---------
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

