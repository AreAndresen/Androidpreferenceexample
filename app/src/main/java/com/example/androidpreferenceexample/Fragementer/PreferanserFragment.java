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
    //--------LAGRINGSKODER--------
    private static final String NOKKEL_SPRAAKKODE = "spraakKode_nokkel";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //--------OPPDATERER SPÅKLOCAL FØR INNHOLDET BLIR SATT--------
        getLocale();


        //--------LOADER FRAGMENTT--------
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PreferansrFragment()).commit();
    }


    //--------RESTARTER FRAGMENTET - BRUKES VED SPRÅKENDRING--------
    public void restartFragment() {
        setLocale(getSpraakKode());
        PreferansrFragment fragment = new PreferansrFragment();
        getFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
    }

    //--------GET/SET FOR Å OVERFØRE SPRÅKODE--------
    public String getSpraakKode() {
        return spraakKode;
    }
    public void setSpraakKode(String spraakKode) {
        this.spraakKode = spraakKode;
    }



    //-------FRAGMENTET STARTER---------
    public static class PreferansrFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            //-------LOADER PREFERANSER.XMl---------
            addPreferencesFromResource(R.xml.preferanser);

        }


        //-------LYTTER ETTER ENDRING GJORT I FRAGMENTET---------
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            //språkendring
            if (key.equals("spraak")) {

                //Gir verdien til koden i klassen
                ((PreferanserFragment)getActivity()).setSpraakKode(sharedPreferences.getString(key,""));

                //restarter fragment etter endring av språk fortløpende ved språkendring
                ((PreferanserFragment)getActivity()).restartFragment();
            }
            //spillendring
            if (key.equals("spill")) {
                //endrer antall spill fortløpende ved endring
                Preference spillValg = findPreference(key);
                spillValg.setSummary(sharedPreferences.getString(key, ""));
            }
        }


        //-------ENDRER SUMMARY FORTLØPENDE VED RESUME NÅR VALG ER UTFØRT---------
        @Override
        public void onResume() {
            super.onResume();
            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

            Preference spraakValg = findPreference("spraak");
            Preference spillValg = findPreference("spill");

            //Oppdaterer summary ved spill/språk
            spraakValg.setSummary(getPreferenceScreen().getSharedPreferences().getString("spraak", ""));
            spillValg.setSummary(getPreferenceScreen().getSharedPreferences().getString("spill", ""));
        }


        @Override
        public void onPause() {
            getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
            super.onPause();
        }

    }
    //-------FRAGMENTET SLUTTER---------



    //-------TILBAKEKNAPP - OPPDATERER INTENT FOR Å OPPDATERE SPRÅKENDRING---------
    @Override
    public void onBackPressed() {
        Intent intent_tilbake = new Intent (PreferanserFragment.this, MainActivity.class);
        startActivity(intent_tilbake);
        finish();
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



    //-------LAGRER SPRÅKENRDRING VED RETUR TIL MAIN-------
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

