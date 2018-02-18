package com.apps.anilgr.simpledictionary;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by anilgr on 11/2/18.
 */

public class DictionaryPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        //set the preference values to the screen
        Preference preference = getPreferenceManager().findPreference(getResources().getString(R.string.preference_theme));
        preference.setSummary(getPreferenceManager().getSharedPreferences().getString(getResources().getString(R.string.preference_theme),"1"));

       //register on preference changed listener
       getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Preference preference = getPreferenceManager().findPreference(key);
                preference.setSummary(sharedPreferences.getString(getResources().getString(R.string.preference_theme),"1"));
                Log.d("Awesome log",sharedPreferences.getString(getResources().getString(R.string.preference_theme),"1"));
            }
        });
    }


}
