package com.example.finalproject_tarrotcardreading;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.animation.ObjectAnimator;
import android.animation.AnimatorSet;
import androidx.navigation.fragment.NavHostFragment;
import android.util.Log;

import com.example.finalproject_tarrotcardreading.data.Card;

/**
 * Fragment responsible for revealing the three selected tarot cards with dramatic flip animations.
 * <p>
 * Key Features:
 * - Sequential 3D flip animations for each of the three cards
 * - Seamless transition from card backs to actual tarot card faces
 * - Navigation integration to proceed to the interpretation phase
 * - Comprehensive logging for debugging card data flow
 *
 * @author Chanjuan Li
 * @version 1.0
 * @see Card
 * @see android.animation.ObjectAnimator
 * @see androidx.navigation.fragment.NavHostFragment
 * @see ShuffleFragment
 * @see ReadingFragment
 * @since API level 1
 */
public class ThreeCardsFragment extends Fragment {

    /** Bundle argument key for passing selected cards array between fragments */
    private static final String ARG_SELECTED_CARDS = "selected_cards";
    
    /** Array containing the three cards selected by the user */
    private Card[] selectedCards;
    
    /** Reference to the middle card ImageView for potential special handling */
    private ImageView card2;

    /**
     * Required empty public constructor for Fragment instantiation.
     * <p>
     * This constructor is required by the Android framework for proper
     * fragment lifecycle management and recreation during configuration changes.
     */
    public ThreeCardsFragment() {
        // Required empty public constructor
    }

    /**
     * Factory method to create a new instance of ThreeCardsFragment with selected cards.
     *
     * @param cards Array of exactly three Card objects selected by the user
     * @return A new instance of ThreeCardsFragment configured with the provided cards
     * @throws IllegalArgumentException if cards array is null or not exactly 3 elements
     * @see Card
     * @see Bundle#putParcelableArray(String, android.os.Parcelable[]) Bundle#putParcelableArray(String, android.os.Parcelable[])Bundle#putParcelableArray(String, android.os.Parcelable[])
     */
    public static ThreeCardsFragment newInstance(Card[] cards) {
        ThreeCardsFragment fragment = new ThreeCardsFragment();
        Bundle args = new Bundle();
        args.putParcelableArray(ARG_SELECTED_CARDS, cards);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Initializes the fragment and extracts the selected cards from arguments.
     * 
     * Retrieves the Card array passed from the previous fragment (ShuffleFragment)
     * via Bundle arguments and stores it for use in the reveal animations.
     * 
     * @param savedInstanceState Bundle containing saved state data, if any
     * 
     * @see Bundle#getParcelableArray(String)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedCards = (Card[]) getArguments().getParcelableArray(ARG_SELECTED_CARDS);
        }
    }

    /**
     * Creates and returns the view hierarchy for the card reveal interface.
     * 
     * Inflates the three-card layout containing three ImageView cards arranged
     * horizontally and an interpretation button for proceeding to the reading phase.
     * 
     * @param inflater The LayoutInflater used to inflate the fragment's layout
     * @param container The parent ViewGroup that the fragment UI will be attached to
     * @param savedInstanceState Bundle containing saved state data, if any
     * @return The root View of the inflated fragment layout
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_three_cards, container, false);
    }

    /**
     * Sets up the card reveal animations and user interaction after view creation.
     * 
     * This method orchestrates the entire card reveal experience:
     * 1. Initializes all three card ImageViews and the interpretation button
     * 2. Sets initial card back images for all three cards
     * 3. Starts sequential flip animations with strategic delays
     * 4. Configures navigation to the interpretation phase
     * 5. Provides comprehensive logging for debugging purposes
     * 
     * @param view The fragment's root view containing all UI components
     * @param savedInstanceState Bundle containing saved state data, if any
     * 
     * @see #startFlipAnimation(ImageView, Card, long)
     * @see androidx.navigation.fragment.NavHostFragment#findNavController(Fragment)
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize card views and interaction button
        ImageView card1 = view.findViewById(R.id.card1);
        card2 = view.findViewById(R.id.card2);
        ImageView card3 = view.findViewById(R.id.card3);
        Button btnInterpret = view.findViewById(R.id.btn_interpret);

        // Set initial card back images for all three positions
        card1.setImageResource(R.drawable.cover);
        card2.setImageResource(R.drawable.cover);
        card3.setImageResource(R.drawable.cover);

        // Execute sequential flip animations with dramatic timing
        startFlipAnimation(card1, selectedCards[0], 0);     // Immediate start
        startFlipAnimation(card2, selectedCards[1], 400);   // 400ms delay
        startFlipAnimation(card3, selectedCards[2], 800);   // 800ms delay

        // Configure interpretation button for navigation to reading phase
        btnInterpret.setOnClickListener(v -> {
            Log.d("ThreeCardsFragment", "Interpret button clicked");
            Bundle bundle = new Bundle();
            bundle.putParcelableArray("selected_cards", selectedCards);
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_threeCardsFragment_to_readingFragment, bundle);
        });

        // Debug logging for card data verification
        for (Card c : selectedCards) {
            Log.d("CardDebug", "name=" + c.getName() + ", desc=" + c.getDescription() + 
                  ", up=" + c.getUprightMeaning() + ", rev=" + c.getReversedMeaning() + 
                  ", img=" + c.getImageResource());
        }
    }

    /**
     * Creates and executes a dramatic 3D flip animation for a single card reveal.
     * 

     * The card rotates from the opposite edge back to face
     * the viewer, display the actual tarot card image. The image switch
     * occurs precisely at the 90° midpoint when the card is edge-on.
     * 
     * Technical Details:
     * - Uses Y-axis rotation for realistic 3D flip effect
     * - AccelerateDecelerateInterpolator provides natural motion curves
     * - Image replacement occurs at animation midpoint for seamless transition
     * - Configurable delay allows for sequential reveals across multiple cards
     * 
     * @param cardView The ImageView representing the card to animate
     * @param card The Card object containing the image resource to reveal
     * @param delay Delay in milliseconds before starting the animation
     * 
     * @see ObjectAnimator#ofFloat(Object, String, float...)
     * @see AccelerateDecelerateInterpolator
     */
    private void startFlipAnimation(ImageView cardView, Card card, long delay) {
        cardView.postDelayed(() -> {
            // Phase 1: Rotate card away from viewer (0° to 90°)
            ObjectAnimator flipOut = ObjectAnimator.ofFloat(cardView, "rotationY", 0f, 90f);
            flipOut.setDuration(300);
            flipOut.setInterpolator(new AccelerateDecelerateInterpolator());

            // Phase 2: Rotate card back to viewer (-90° to 0°)
            ObjectAnimator flipIn = ObjectAnimator.ofFloat(cardView, "rotationY", -90f, 0f);
            flipIn.setDuration(300);
            flipIn.setInterpolator(new AccelerateDecelerateInterpolator());

            // Create sequential animation set
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(flipOut, flipIn);

            // Switch card image at the midpoint (90°) for seamless transition
            flipOut.addListener(new android.animation.AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(android.animation.Animator animation) {
                    cardView.setImageResource(card.getImageResId());
                }
            });

            // Execute the complete flip sequence
            animatorSet.start();
        }, delay);
    }
}