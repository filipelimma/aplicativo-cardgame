package br.edu.utfpr.filipenogueira.aplicativocardgame;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AutoriaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocaleHelper.setLocale(this, PrefUtils.getLanguage(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autoria);
        setTitle(R.string.about);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
