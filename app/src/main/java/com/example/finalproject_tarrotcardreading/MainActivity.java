package com.example.finalproject_tarrotcardreading;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

/**
 * Main Activity for the Tarot Card Reading application.
 * 
 * This activity serves as the entry point and container for the application's
 * fragment-based navigation system. It handles language localization and
 * provides the main UI structure using EdgeToEdge display.
 * 
 * @author YourName
 * @version 1.0
 * @since API level 1
 * 
 * @see com.example.finalproject_tarrotcardreading.data.LocaleHelper
 * @see androidx.appcompat.app.AppCompatActivity
 */
public class MainActivity extends AppCompatActivity {
    
    /**
     * Attaches a localized context based on saved language preferences.
     * 
     * This method is called before onCreate() and ensures that the activity
     * uses the user's previously selected language setting. It retrieves the
     * saved language preference and applies it to the base context.
     * 
     * @param newBase The original context to be localized
     * 
     * @see com.example.finalproject_tarrotcardreading.data.LocaleHelper#getSavedLanguage(Context)
     * @see com.example.finalproject_tarrotcardreading.data.LocaleHelper#setLocale(Context, String)
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        // Retrieve user's saved language preference
        String lang = com.example.finalproject_tarrotcardreading.data.LocaleHelper.getSavedLanguage(newBase);
        // Apply the localized context to the activity
        super.attachBaseContext(com.example.finalproject_tarrotcardreading.data.LocaleHelper.setLocale(newBase, lang));
    }

    /**
     * Initializes the main activity and sets up the UI.
     * The layout contains a FragmentContainerView that hosts all
     * navigation destinations in the app.
     * 
     * @param savedInstanceState Bundle containing saved state data, if any
     * 
     * @see androidx.activity.EdgeToEdge#enable(androidx.activity.ComponentActivity)
     * @see #setContentView(int)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Enable modern EdgeToEdge display
        EdgeToEdge.enable(this);
        // Set the main layout with navigation container
        setContentView(R.layout.activity_main);
    }
}