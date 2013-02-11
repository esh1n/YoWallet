package com.example.StayAlive;

/**
 * Created with IntelliJ IDEA.
 * User: shelbi
 * Date: 2/12/13
 * Time: 12:32 AM
 * To change this template use File | Settings | File Templates.
 */
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PrefActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
    }
}