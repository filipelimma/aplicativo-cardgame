package br.edu.utfpr.filipenogueira.aplicativocardgame;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

// Em SettingsFragment.java
public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        // Aqui você pode adicionar lógica para quando uma preferência for alterada
    }
}

