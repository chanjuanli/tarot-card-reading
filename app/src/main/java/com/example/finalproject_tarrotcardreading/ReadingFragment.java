package com.example.finalproject_tarrotcardreading;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.navigation.fragment.NavHostFragment;
import android.util.Log;
import android.os.Parcelable;

import com.example.finalproject_tarrotcardreading.data.Card;

/**
 * Fragment for displaying the final tarot card reading results.
 * <p>
 * This fragment presents the interpretation of three selected tarot cards,
 * showing their images, names, meanings (both upright and reversed), and
 * detailed descriptions. It serves as the final destination in the tarot
 * reading flow where users receive their personalized card interpretations.
 *
 * @author Chanjuan Li
 * @version 1.0
 * @see Card
 * @see androidx.navigation.fragment.NavHostFragment
 * @since API level 1
 */
public class ReadingFragment extends Fragment {
    
    /** Tag for logging purposes */
    private static final String TAG = "ReadingFragment";
    
    /** Bundle argument key for selected cards array */
    private static final String ARG_SELECTED_CARDS = "selected_cards";
    
    /** Array of three selected cards to display */
    private Card[] selectedCards;

    /**
     * Required empty public constructor for Fragment instantiation.
     * <p>
     * This constructor is required by the Android framework for proper
     * fragment lifecycle management and recreation.
     */
    public ReadingFragment() {
        // Required empty public constructor
    }

    /**
     * Initializes the fragment and retrieves the selected cards from arguments.
     * 
     * This method extracts the three selected cards passed from the previous
     * fragment through navigation arguments and converts them from Parcelable
     * objects back to Card instances.
     * 
     * @param savedInstanceState Bundle containing saved state data, if any
     * 
     * @see Card
     * @see android.os.Parcelable
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        
        // Extract selected cards from navigation arguments
        if (getArguments() != null) {
            Parcelable[] parcelables = getArguments().getParcelableArray("selected_cards");
            if (parcelables != null) {
                selectedCards = new Card[parcelables.length];
                // Convert Parcelable array back to Card array
                for (int i = 0; i < parcelables.length; i++) {
                    selectedCards[i] = (Card) parcelables[i];
                }
            }
        }
    }

    /**
     * Creates and returns the view hierarchy for this fragment.
     * 
     * Inflates the reading fragment layout containing card display areas
     * and navigation controls.
     * 
     * @param inflater The LayoutInflater used to inflate the fragment's layout
     * @param container The parent ViewGroup that the fragment UI will be attached to
     * @param savedInstanceState Bundle containing saved state data, if any
     * @return The root View of the inflated fragment layout
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reading, container, false);
    }

    /**
     * Sets up the UI components and displays card information after view creation.
     * 
     * This method:
     * - Displays information for all three selected cards
     * - Sets up the home button navigation
     * - Handles cases where cards might be missing
     * 
     * @param view The fragment's root view
     * @param savedInstanceState Bundle containing saved state data, if any
     * 
     * @see #displayCardInfo(View, Card, int)
     * @see androidx.navigation.fragment.NavHostFragment#findNavController(Fragment)
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Display information for all three cards if available
        if (selectedCards != null && selectedCards.length >= 3) {
            displayCardInfo(view, selectedCards[0], 1);
            displayCardInfo(view, selectedCards[1], 2);
            displayCardInfo(view, selectedCards[2], 3);
        }

        // Set up home button navigation
        Button btnHome = view.findViewById(R.id.btn_home);
        btnHome.setOnClickListener(v -> {
            // Navigate back to main fragment to start new reading
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_readingFragment_to_mainFragment);
        });
    }

    /**
     * Displays comprehensive information for a single card in the reading layout.
     * 
     * This method populates the UI elements for one card position with:
     * - Card image
     * - Card name/title
     * - Formatted upright and reversed meanings
     * - Detailed description
     * 
     * @param view The fragment's root view containing the card display elements
     * @param card The Card object containing the information to display
     * @param cardNumber The position number (1, 2, or 3) indicating which card slot to fill
     * 
     * @see #getResId(String, Class)
     * @see Card#getName()
     * @see Card#getUprightMeaning()
     * @see Card#getReversedMeaning()
     * @see Card#getDescription()
     */
    private void displayCardInfo(View view, Card card, int cardNumber) {
        if (card == null) return;
        
        // Get UI components for this card position using dynamic resource lookup
        TextView titleView = view.findViewById(getResId("card" + cardNumber + "_title", R.id.class));
        TextView meaningView = view.findViewById(getResId("card" + cardNumber + "_meaning", R.id.class));
        TextView descriptionView = view.findViewById(getResId("card" + cardNumber + "_description", R.id.class));
        ImageView imageView = view.findViewById(getResId("card" + cardNumber + "_image", R.id.class));

        // Populate card information with localized content
        titleView.setText(card.getName());
        meaningView.setText(getString(R.string.card_meaning_format,
                card.getUprightMeaning(),
                card.getReversedMeaning()));
        descriptionView.setText(getString(R.string.card_description_format,
                card.getDescription()));

        // Set card image
        imageView.setImageResource(card.getImageResId());
    }

    /**
     * Utility method to dynamically resolve resource IDs by name using reflection.
     * 
     * This method allows for programmatic access to resource IDs when the exact
     * resource name is constructed at runtime .
     * 
     * @param resName The name of the resource to look up
     * @param c The class containing the resource constants
     * @return The integer resource ID, or -1 if the resource cannot be found
     * 
     * @see java.lang.reflect.Field#getInt(Object)
     */
    private int getResId(String resName, Class<?> c) {
        try {
            return c.getField(resName).getInt(null);
        } catch (Exception e) {
            Log.e(TAG, "Error getting resource ID for: " + resName, e);
            return -1;
        }
    }
}