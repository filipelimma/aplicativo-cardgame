package br.edu.utfpr.filipenogueira.aplicativocardgame;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class PrefUtils {
    public static SharedPreferences getPrefs(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static String getTheme(Context ctx){
        return getPrefs(ctx).getString(ctx.getString(R.string.pref_key_theme), "light");
    }
    public static String getLanguage(Context ctx){
        return getPrefs(ctx).getString(ctx.getString(R.string.pref_key_language), "en");
    }
    public static String getSort(Context ctx){
        return getPrefs(ctx).getString(ctx.getString(R.string.pref_key_sort_order), "alpha_asc");
    }
}
