package com.example.finalproject_tarrotcardreading.data;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import java.util.Locale;

/**
 * Helper class for managing application localization and language switching.
 * <p>
 * This utility class provides methods to:
 * - Set and persist user-selected language preferences
 * - Create context with specific locale configurations
 * - Retrieve saved language settings from SharedPreferences
 * <p>
 * The class uses SharedPreferences to store the user's language choice
 * and applies it by creating a new Configuration context.
 *
 * @author Chanjuan Li
 * @version 1.0
 * @since API level 1
 */
public class LocaleHelper {
    /**
     * SharedPreferences file name for storing application preferences.
     * Used to persist user settings across app sessions.
     */
    public static final String PREFS_NAME = "app_prefs";
    /**
     * Key for storing the selected language in SharedPreferences.
     * This key is used to save and retrieve the user's language preference.
     */
    public static final String KEY_LANGUAGE = "app_language";

    /**
     * Sets the application locale to the specified language and saves the preference.
     *
     * @param context  The application context used to access SharedPreferences                and create the new configuration context
     * @param language The language code (e.g., "en" for English, "zh" for Chinese)                Must be a valid ISO 639-1 language code
     * @return A new Context instance configured with the specified locale.         This context should be used for accessing localized resources.
     * @throws IllegalArgumentException if the language code is invalid
     * @see Locale
     * @see Configuration
     * @see Context#createConfigurationContext(Configuration) Context#createConfigurationContext(Configuration)Context#createConfigurationContext(Configuration)
     */
    public static Context setLocale(Context context, String language) {
        // Save user language preference to SharedPreferences for persistence
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putString(KEY_LANGUAGE, language).apply();

        // Create a new Locale object with the specified language code
        Locale locale = new Locale(language);
        
        // Set this locale as the default for the current application instance
        Locale.setDefault(locale);

        // Create a new Configuration based on the current configuration
        Configuration config = new Configuration(context.getResources().getConfiguration());
        
        // Apply the new locale to the configuration
        config.setLocale(locale);

        // Return a new context that uses the updated configuration
        // This context will provide localized resources (strings, layouts, etc.)
        return context.createConfigurationContext(config);
    }

    /**
     * Retrieves the user's saved language preference from SharedPreferences.
     * <p>
     * This method checks SharedPreferences for a previously saved language setting.
     * If no language has been explicitly set by the user, it returns the
     * system's default language as a fallback.
     *
     * @param context The application context used to access SharedPreferences
     * @return The saved language code as a String (e.g., "en", "zh").         Returns the system default language if no preference is saved.
     * @see Locale#getDefault() Locale#getDefault()Locale#getDefault()
     * @see android.content.SharedPreferences#getString(String, String) android.content.SharedPreferences#getString(String, String)android.content.SharedPreferences#getString(String, String)
     */
    public static String getSavedLanguage(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString(KEY_LANGUAGE, Locale.getDefault().getLanguage());
    }
}
