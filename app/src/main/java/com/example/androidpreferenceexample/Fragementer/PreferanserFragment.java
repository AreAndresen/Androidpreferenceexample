package com.example.androidpreferenceexample.Fragementer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidpreferenceexample.MainActivity;
import com.example.androidpreferenceexample.R;

import java.util.Locale;

public class PreferanserFragment extends AppCompatActivity {

    String spraakKode;
    private static final String NOKKEL_SPRAAKKODE = "spraakKode_nokkel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);

        //Loader preferanser fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PrefsFragment()).commit();
    }


    //restart fragmentmetode for språkendring fortløpende ved endring
    public void restartFragment() {
        setLocale(getSpraakKode());
        PrefsFragment fragment = new PrefsFragment();
        getFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
    }

    //get/set for å hente kode fra fragment
    public String getSpraakKode() {
        return spraakKode;
    }
    public void setSpraakKode(String spraakKode) {
        this.spraakKode = spraakKode;
    }



    public static class PrefsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

        //private static SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            //loader preferanser.xmler.xml
            addPreferencesFromResource(R.xml.preferanser);

        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("spraak")) {
                Preference spraakValg = findPreference(key);
                //endrer summarytekst ved endring
                spraakValg.setSummary(sharedPreferences.getString(key, ""));

                //Gir verdien til koden i klassen
                ((PreferanserFragment)getActivity()).setSpraakKode(sharedPreferences.getString(key,""));

                //restarter fragment etter endring av språk for å se virkning av språkendring
                ((PreferanserFragment)getActivity()).restartFragment();
            }
            if (key.equals("spill")) {
                //endrer tekst
                Preference spillValg = findPreference(key);
                spillValg.setSummary(sharedPreferences.getString(key, ""));
            }
        }

        //metode for å endre summary når et valg er utført i prferanser
        @Override
        public void onResume() {
            super.onResume();
            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

            Preference spraakValg = findPreference("spraak");
            Preference spillValg = findPreference("spill");

            if(spraakValg.equals("no")) {
                spraakValg.setSummary(getPreferenceScreen().getSharedPreferences().getString("spraak", "") + " er valgt språk");
                spillValg.setSummary(getPreferenceScreen().getSharedPreferences().getString("spill", "") + " er valgt spill");
            }
            if(spraakValg.equals("de")) {
                spraakValg.setSummary(getPreferenceScreen().getSharedPreferences().getString("spraak", "") + " das es sprach");
                spillValg.setSummary(getPreferenceScreen().getSharedPreferences().getString("spill", "") + " ist spiel");
            }
        }

        @Override
        public void onPause() {
            //loadLocale();
            getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
            super.onPause();
        }

    }


    //Trenger denne så intent/main oppdateres på tilbake samt språk
    @Override
    public void onBackPressed() {
        Intent intent_tilbake = new Intent (PreferanserFragment.this, MainActivity.class);
        startActivity(intent_tilbake);
    }

    //TRENGER DENNE HER FOR Å LA VALGT SPRÅK VÆRE MED FRA START
    public void setLocale(String lang) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration cf = res.getConfiguration();
        cf.setLocale(new Locale(lang));
        res.updateConfiguration(cf,dm);
    }

    //DENNE MOTVIRKER SPRÅKENDRING TIL DEFAULT VED ROTASJON
    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("APP_INFO", MODE_PRIVATE);
        String spraak = prefs.getString(NOKKEL_SPRAAKKODE,"");

        setLocale(spraak);
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


    /*lagrer innholdet i teksten - for å beholde til rotasjon av skjermen
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
    }*/
}

