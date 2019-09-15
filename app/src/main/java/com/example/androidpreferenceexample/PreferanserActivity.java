package com.example.androidpreferenceexample;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class PreferanserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Load setting fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PrefsFragment()).commit();

    }


    public static class PrefsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

        //private static SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("spraak")) {
                Preference spraakValg = findPreference(key);
                //endrer tekst
                spraakValg.setSummary(sharedPreferences.getString(key, ""));

                //endrer språk til valgt verdi (no/de)
                setLocale(sharedPreferences.getString(key, ""));

            }

            if (key.equals("spill")) {
                //endrer tekst
                Preference spillValg = findPreference(key);
                spillValg.setSummary(sharedPreferences.getString(key, ""));
            }
        }

        //metode for endring av språk
        public void setLocale(String lang) {
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration cf = res.getConfiguration();
            cf.setLocale(new Locale(lang));
            res.updateConfiguration(cf,dm);
        }


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
            getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
            super.onPause();
        }

    }
}

