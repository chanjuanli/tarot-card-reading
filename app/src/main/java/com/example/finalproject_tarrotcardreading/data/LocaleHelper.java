package com.example.finalproject_tarrotcardreading.data;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import java.util.Locale;

public class LocaleHelper {
    public static final String PREFS_NAME = "app_prefs";
    public static final String KEY_LANGUAGE = "app_language";

    public static Context setLocale(Context context, String language) {
        // save user option
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putString(KEY_LANGUAGE, language).apply();

        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration config = new Configuration(context.getResources().getConfiguration());
        config.setLocale(locale);

        return context.createConfigurationContext(config);
    }

    public static String getSavedLanguage(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString(KEY_LANGUAGE, Locale.getDefault().getLanguage());
    }
}
