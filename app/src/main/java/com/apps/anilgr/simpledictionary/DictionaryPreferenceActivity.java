package com.apps.anilgr.simpledictionary;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by anilgr on 11/2/18.
 */

public class DictionaryPreferenceActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        //add the preference fragment to the container
        getFragmentManager().beginTransaction()
                .add(R.id.container,new DictionaryPreferenceFragment(),"tagfrag")
                .commit();


    }
}
