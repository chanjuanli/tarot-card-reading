package com.example.finalproject_tarrotcardreading;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.finalproject_tarrotcardreading.data.LocaleHelper;

import java.util.Locale;

/**
 * Main home screen fragment for the Tarot Card Reading application.
 * <p>
 * This fragment displays the application's welcome screen with the main
 * tarot deck image, shuffle button, and language switching capability.
 * It serves as the entry point for users to begin a tarot reading session.
 *
 * @author Chanjuan Li
 * @version 1.0
 * @see com.example.finalproject_tarrotcardreading.data.LocaleHelper
 * @see androidx.navigation.fragment.NavHostFragment
 * @since API level 1
 */
public class MainFragment extends Fragment {

    // Fragment argument keys for potential future parameter passing
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Fragment parameters for potential future use
    private String mParam1;
    private String mParam2;

    /**
     * Required empty public constructor for Fragment instantiation.
     * <p>
     * This constructor is required by the Android framework for proper
     * fragment lifecycle management and recreation during configuration changes.
     */
    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Factory method to create a new instance of MainFragment with parameters.
     *
     * @param param1 First parameter for future extensibility
     * @param param2 Second parameter for future extensibility
     * @return A new instance of MainFragment with the provided arguments
     */
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Initializes the fragment and retrieves any passed arguments.
     * 
     * @param savedInstanceState Bundle containing saved state data, if any
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * Creates and returns the view hierarchy for this fragment.
     * 
     * Inflates the main fragment layout containing the app title,
     * deck image, shuffle button, and language switch button.
     * 
     * @param inflater The LayoutInflater used to inflate the fragment's layout
     * @param container The parent ViewGroup that the fragment UI will be attached to
     * @param savedInstanceState Bundle containing saved state data, if any
     * @return The root View of the inflated fragment layout
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    /**
     * Sets up UI components and click listeners after the view is created.
     * 
     * This method initializes:
     * - Shuffle button navigation to the card shuffling screen
     * - Language switch button for toggling between English and Chinese
     * 
     * @param view The fragment's root view
     * @param savedInstanceState Bundle containing saved state data, if any
     * 
     * @see androidx.navigation.fragment.NavHostFragment#findNavController(Fragment)
     * @see LocaleHelper#setLocale(android.content.Context, String)
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Set up shuffle button to navigate to card selection
        View shuffleButton = view.findViewById(R.id.shuffle_button);
        shuffleButton.setOnClickListener(v -> {
            androidx.navigation.fragment.NavHostFragment.findNavController(this)
                .navigate(R.id.action_mainFragment_to_shuffleFragment);
        });

        // Set up language switch button for EN/CN toggle
        Button btnSwitchLanguage = view.findViewById(R.id.btn_switch_language);
        btnSwitchLanguage.setOnClickListener(v -> {
            // Toggle between Chinese and English based on current locale
            if (Locale.getDefault().getLanguage().startsWith("zh")) {
                LocaleHelper.setLocale(requireActivity(), "en");
            } else {
                LocaleHelper.setLocale(requireActivity(), "zh");
            }
            // Recreate activity to apply language changes
            requireActivity().recreate();
        });
    }
}