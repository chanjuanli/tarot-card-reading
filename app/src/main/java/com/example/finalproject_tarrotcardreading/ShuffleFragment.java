package com.example.finalproject_tarrotcardreading;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.finalproject_tarrotcardreading.data.Card;
import com.example.finalproject_tarrotcardreading.data.CardData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Fragment responsible for the card shuffling animation and selection process.
 * <p>
 * Key Features:
 * - Complex multi-phase shuffle animation with spiral movements
 * - Three-row card layout for optimal selection experience
 * - Visual feedback for card selection with scaling and glow effects
 * - Dynamic progress tracking for card selection (0-3 cards)
 * - Seamless navigation to card reveal and reading phases
 * - Responsive layout adapting to different screen sizes
 * <p>
 * Animation Phases:
 * 1. Initial shuffle with spiral movements and rotations
 * 2. Gathering phase with cards converging to center
 * 3. Fade-out and transition to selection layout
 * 4. Three-row card spread presentation
 *
 * @author Chanjuan Li
 * @version 1.0
 * @see Card
 * @see CardData
 * @see androidx.navigation.fragment.NavHostFragment
 * @since API level 1
 */
public class ShuffleFragment extends Fragment {

    // Fragment initialization parameters for future extensibility
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Fragment parameters
    private String mParam1;
    private String mParam2;

    // Animation and card layout constants
    /** Total number of cards in the Major Arcana deck */
    private static final int CARD_COUNT = 22;
    /** Number of cards used in the shuffle animation */
    private static final int SHUFFLE_ANIM_CARD_COUNT = 15;
    /** Total duration of the shuffle animation in milliseconds */
    private static final int ANIM_DURATION = 2500;
    /** Scale factor for card size enhancement */
    private static final float CARD_SCALE = 2.0f;

    // Card selection state management
    /** List of currently selected card views */
    private List<ImageView> selectedCards = new ArrayList<>();
    /** Maximum number of cards that can be selected */
    private static final int MAX_SELECTED_CARDS = 3;

    /**
     * Required empty public constructor for Fragment instantiation.
     * <p>
     * This constructor is required by the Android framework for proper
     * fragment lifecycle management and recreation.
     */
    public ShuffleFragment() {
        // Required empty public constructor
    }

    /**
     * Factory method to create a new instance of ShuffleFragment with parameters.
     * <p>
     * This method follows the recommended pattern for fragment creation
     * with arguments, providing future extensibility for customization.
     *
     * @param param1 First parameter for future customization options
     * @param param2 Second parameter for future customization options
     * @return A new instance of ShuffleFragment with the provided arguments
     */
    public static ShuffleFragment newInstance(String param1, String param2) {
        ShuffleFragment fragment = new ShuffleFragment();
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
     * Inflates the shuffle fragment layout containing animation containers
     * and card selection areas.
     * 
     * @param inflater The LayoutInflater used to inflate the fragment's layout
     * @param container The parent ViewGroup that the fragment UI will be attached to
     * @param savedInstanceState Bundle containing saved state data, if any
     * @return The root View of the inflated fragment layout
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shuffle, container, false);
    }

    /**
     * Sets up the shuffle animation and card selection interface after view creation.
     * 
     * This method initiates the shuffle animation sequence
     * and prepares the card selection interface for user interaction.
     * 
     * @param view The fragment's root view
     * @param savedInstanceState Bundle containing saved state data, if any
     * 
     * @see #startEnhancedShuffleAnimation(ConstraintLayout, FrameLayout)
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ConstraintLayout root = view.findViewById(R.id.shuffle_root);
        FrameLayout cardSpread = view.findViewById(R.id.card_spread_container);

        startEnhancedShuffleAnimation(root, cardSpread);
    }

    /**
     * Creates and executes a shuffle animation sequence.
     * 
     * This method orchestrates a complex multi-phase animation:
     * 1. Creates multiple animated cards scattered across the screen
     * 2. Executes spiral movement patterns with random variations
     * 3. Transitions to gathering phase with convergence to center
     * 4. Cleans up animation cards and reveals selection interface
     * 
     * The animation creates an immersive experience similar to professional
     * tarot reading applications with realistic card movement patterns.
     * 
     * @param root The root ConstraintLayout for positioning animation cards
     * @param cardSpread The FrameLayout container for the final card selection layout
     * 
     * @see #createAnimationCard()
     * @see #createShuffleAnimation(List, int, int)
     * @see #setupHorizontalCardLayout(FrameLayout)
     */
    private void startEnhancedShuffleAnimation(ConstraintLayout root, FrameLayout cardSpread) {
        List<ImageView> animCards = new ArrayList<>();
        Random random = new Random();

        // Calculate screen dimensions for optimal animation positioning
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

        // Create and position multiple cards for realistic shuffle effect
        for (int i = 0; i < SHUFFLE_ANIM_CARD_COUNT; i++) {
            ImageView card = createAnimationCard();

            // Calculate safe positioning bounds with margins
            int sideMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, getResources().getDisplayMetrics());
            int minX = sideMargin;
            int maxX = screenWidth - sideMargin - card.getLayoutParams().width;
            int minY = 0;
            int maxY = screenHeight;

            // Position cards randomly within safe bounds
            float x = minX + random.nextFloat() * (maxX - minX);
            float y = minY + random.nextFloat() * (maxY - minY);
            card.setX(x - card.getLayoutParams().width / 2);
            card.setY(y - card.getLayoutParams().height / 2);
            card.setRotation(0f);
            card.setAlpha(0.8f + random.nextFloat() * 0.2f); // Subtle alpha variation

            root.addView(card);
            animCards.add(card);
        }

        // Execute the complex shuffle animation sequence
        AnimatorSet shuffleAnimation = createShuffleAnimation(animCards, centerX, centerY);
        shuffleAnimation.start();

        // Transition to card selection interface after animation completion
        shuffleAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // Clean up animation cards
                for (ImageView card : animCards) {
                    root.removeView(card);
                }

                // Reveal card selection interface
                cardSpread.setVisibility(View.VISIBLE);

                // Wait for layout completion before setting up card positions
                cardSpread.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        cardSpread.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        setupHorizontalCardLayout(cardSpread);
                    }
                });
            }
        });
    }

    /**
     * Creates a single card ImageView for use in shuffle animations.
     * 
     * Configures card appearance, dimensions, and layout parameters
     * optimized for smooth animation performance and visual appeal.
     * 
     * @return A configured ImageView representing a tarot card back
     * 
     * @see android.widget.ImageView.ScaleType#FIT_CENTER
     */
    private ImageView createAnimationCard() {
        ImageView card = new ImageView(getContext());
        card.setImageResource(R.drawable.cover);

        // Calculate card dimensions maintaining proper aspect ratio
        int cardWidth = (int) (getResources().getDisplayMetrics().density * 140);
        int cardHeight = (int) (cardWidth * 1.4f);

        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(cardWidth, cardHeight);
        card.setLayoutParams(lp);
        
        // Add padding for visual spacing and configure scaling
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics());
        card.setPadding(padding, padding, padding, padding);
        card.setScaleType(ImageView.ScaleType.FIT_CENTER);

        return card;
    }

    /**
     * Creates a complex multi-phase animation sequence for realistic card shuffling.
     * 
     * The animation consists of two main phases:
     * 1. Spiral Movement Phase: Cards move in expanding spiral patterns with rotations
     * 2. Gathering Phase: Cards converge toward the center with final positioning
     * 
     * @param animCards List of ImageView cards to animate
     * @param centerX X coordinate of the screen center for convergence
     * @param centerY Y coordinate of the screen center for convergence
     * @return AnimatorSet containing all card animation sequences
     * 
     * @see ObjectAnimator
     * @see AnimatorSet#playTogether(java.util.Collection)
     */
    private AnimatorSet createShuffleAnimation(List<ImageView> animCards, int centerX, int centerY) {
        List<Animator> allAnimators = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < animCards.size(); i++) {
            ImageView card = animCards.get(i);
            List<Animator> cardAnimators = new ArrayList<>();

            // Phase 1: Spiral movement with multiple sub-phases
            float spiralRadius = 150 + random.nextFloat() * 100;
            for (int phase = 0; phase < 3; phase++) {
                float targetAngle = (360f / animCards.size()) * i + phase * 120 + random.nextFloat() * 60;
                float targetRadius = spiralRadius + phase * 50;

                // Calculate spiral target positions
                float targetX = (float) (centerX + targetRadius * Math.cos(Math.toRadians(targetAngle)));
                float targetY = (float) (centerY + targetRadius * Math.sin(Math.toRadians(targetAngle)));

                // Create movement and rotation animators
                ObjectAnimator moveX = ObjectAnimator.ofFloat(card, "x", targetX - card.getLayoutParams().width / 2);
                ObjectAnimator moveY = ObjectAnimator.ofFloat(card, "y", targetY - card.getLayoutParams().height / 2);
                ObjectAnimator rotate = ObjectAnimator.ofFloat(card, "rotation", targetAngle + random.nextFloat() * 180);

                // Configure timing for sequential spiral phases
                moveX.setDuration(ANIM_DURATION / 4);
                moveY.setDuration(ANIM_DURATION / 4);
                rotate.setDuration(ANIM_DURATION / 4);

                moveX.setStartDelay(phase * (ANIM_DURATION / 4));
                moveY.setStartDelay(phase * (ANIM_DURATION / 4));
                rotate.setStartDelay(phase * (ANIM_DURATION / 4));

                cardAnimators.add(moveX);
                cardAnimators.add(moveY);
                cardAnimators.add(rotate);
            }

            // Phase 2: Final gathering animation with convergence to center
            float finalAngle = random.nextFloat() * 360;
            float finalRadius = 80 + random.nextFloat() * 40;
            float finalX = (float) (centerX + finalRadius * Math.cos(Math.toRadians(finalAngle)));
            float finalY = (float) (centerY + finalRadius * Math.sin(Math.toRadians(finalAngle)));

            // Create final positioning animators
            ObjectAnimator finalMoveX = ObjectAnimator.ofFloat(card, "x", finalX - card.getLayoutParams().width / 2);
            ObjectAnimator finalMoveY = ObjectAnimator.ofFloat(card, "y", finalY - card.getLayoutParams().height / 2);
            ObjectAnimator finalRotate = ObjectAnimator.ofFloat(card, "rotation", random.nextFloat() * 360);
            ObjectAnimator finalAlpha = ObjectAnimator.ofFloat(card, "alpha", 0.5f + random.nextFloat() * 0.5f);

            // Configure final phase timing
            finalMoveX.setDuration(ANIM_DURATION / 3);
            finalMoveY.setDuration(ANIM_DURATION / 3);
            finalRotate.setDuration(ANIM_DURATION / 3);
            finalAlpha.setDuration(ANIM_DURATION / 3);

            finalMoveX.setStartDelay(ANIM_DURATION * 2 / 3);
            finalMoveY.setStartDelay(ANIM_DURATION * 2 / 3);
            finalRotate.setStartDelay(ANIM_DURATION * 2 / 3);
            finalAlpha.setStartDelay(ANIM_DURATION * 2 / 3);

            cardAnimators.add(finalMoveX);
            cardAnimators.add(finalMoveY);
            cardAnimators.add(finalRotate);
            cardAnimators.add(finalAlpha);

            allAnimators.addAll(cardAnimators);
        }

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(allAnimators);
        return animatorSet;
    }

    /**
     * Card aspect ratio constant for maintaining proper proportions.
     * Based on standard tarot card dimensions (approximately 1.74:1 ratio).
     */
    private static final float CARD_ASPECT_RATIO = 684f / 394f;
    
    /**
     * Sets up the three-row card layout for interactive selection.
     * 
     * Creates a visually appealing spread of all 22 Major Arcana cards
     * arranged in three rows with optimal spacing
     * and responsive sizing based on screen dimensions.
     * 
     * @param cardSpread The FrameLayout container for positioning cards
     * 
     * @see #handleCardClick(ImageView)
     * @see #CARD_ASPECT_RATIO
     */
    private void setupHorizontalCardLayout(FrameLayout cardSpread) {
        int containerWidth = cardSpread.getWidth();
        int containerHeight = cardSpread.getHeight();

        // Define three-row distribution for optimal visual balance
        int[] cardsPerRow = {8, 7, 7};
        int totalRows = cardsPerRow.length;
        int cardIndex = 0;

        // Calculate margins and available space
        int sideMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        int availableWidth = containerWidth - 2 * sideMargin;

        // Determine optimal card size based on maximum row width
        int maxCardsInRow = 0;
        for (int n : cardsPerRow) {
            if (n > maxCardsInRow) maxCardsInRow = n;
        }

        // Calculate card dimensions maintaining proper aspect ratio
        int cardWidth = availableWidth / maxCardsInRow;
        int cardHeight = (int) (cardWidth * CARD_ASPECT_RATIO);

        // Configure row spacing for visual separation
        int rowSpacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());

        // Calculate vertical centering offset
        int totalHeight = totalRows * cardHeight + (totalRows - 1) * rowSpacing;
        int offsetY = (containerHeight - totalHeight) / 2;

        // Create and position cards in three rows
        for (int row = 0; row < totalRows; row++) {
            int cardsInThisRow = cardsPerRow[row];
            int rowTotalWidth = cardsInThisRow * cardWidth;
            int startX = sideMargin + (availableWidth - rowTotalWidth) / 2;
            int y = offsetY + row * (cardHeight + rowSpacing);

            for (int col = 0; col < cardsInThisRow && cardIndex < CARD_COUNT; col++, cardIndex++) {
                ImageView card = new ImageView(getContext());
                card.setImageResource(R.drawable.cover);

                // Configure card layout and positioning
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(cardWidth, cardHeight);
                card.setLayoutParams(lp);
                card.setScaleType(ImageView.ScaleType.FIT_CENTER);

                int x = startX + col * cardWidth;
                card.setX(x);
                card.setY(y);
                card.setRotation(0f);

                // Enable interaction and tag with card index
                card.setClickable(true);
                card.setTag(cardIndex);
                card.setOnClickListener(v -> handleCardClick(card));

                // Clear any existing foreground effects
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    card.setForeground(null);
                }

                cardSpread.addView(card);
            }
        }
    }

    /**
     * Handles card selection and deselection with visual feedback.
     * 
     * Manages the selection state of individual cards, enforcing the
     * three-card maximum limit and providing immediate visual feedback
     * through scaling and glow effects.
     * 
     * @param clickedCard The ImageView card that was clicked by the user
     * 
     * @see #setCardSelected(ImageView)
     * @see #setCardUnselected(ImageView)
     * @see #updateTipText()
     */
    private void handleCardClick(ImageView clickedCard) {
        if (selectedCards.contains(clickedCard)) {
            // Deselect if already selected
            selectedCards.remove(clickedCard);
            setCardUnselected(clickedCard);
        } else {
            // Select if under the maximum limit
            if (selectedCards.size() < MAX_SELECTED_CARDS) {
                selectedCards.add(clickedCard);
                setCardSelected(clickedCard);
            }
        }
        updateTipText();
    }

    /**
     * Applies visual feedback for card selection state.
     * 
     * Creates subtle scaling animation and applies selection glow effect
     * to clearly indicate the card's selected status.
     * 
     * @param card The ImageView to mark as selected
     * 
     * @see androidx.core.content.ContextCompat#getDrawable(android.content.Context, int)
     */
    private void setCardSelected(ImageView card) {
        // Apply subtle scaling for selection feedback
        card.animate()
                .scaleX(1.08f)
                .scaleY(1.08f)
                .setDuration(200)
                .start();
        
        // Add selection glow effect on supported versions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            card.setForeground(ContextCompat.getDrawable(getContext(), R.drawable.card_selected_glow));
        }
        card.clearColorFilter();
        card.setAlpha(1.0f);
    }

    /**
     * Removes visual feedback for card deselection.
     * 
     * Returns the card to its normal visual state by removing scaling
     * and selection effects.
     * 
     * @param card The ImageView to mark as unselected
     */
    private void setCardUnselected(ImageView card) {
        // Return to normal scale
        card.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(200)
                .start();
        
        // Remove selection effects
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            card.setForeground(null);
        }
        card.clearColorFilter();
        card.setAlpha(1.0f);
    }

    /**
     * Updates the instruction text and button state based on current selection.
     * 
     * Provides dynamic feedback to guide the user through the selection process:
     * - Prompts to select cards when none are chosen
     * - Shows progress when partially selected
     * - Enables continuation when exactly three cards are selected
     * 
     * Uses localized string resources for international support.
     * 
     * @see #showCardReading()
     */
    private void updateTipText() {
        int selectedCount = selectedCards.size();
        Button btn = getView().findViewById(R.id.complete_reading_button);

        btn.setVisibility(View.VISIBLE);

        if (selectedCount == 0) {
            btn.setText(getString(R.string.select_3_cards_tip));
            btn.setEnabled(false);
        } else if (selectedCount < MAX_SELECTED_CARDS) {
            btn.setText(getString(R.string.selected_cards_tip, selectedCount, MAX_SELECTED_CARDS - selectedCount));
            btn.setEnabled(false);
        } else {
            btn.setText(getString(R.string.perfect_3_selected));
            btn.setEnabled(true);
            btn.setOnClickListener(v -> showCardReading());
        }
    }
    
    /**
     * Prepares selected cards data and navigates to the card reveal phase.
     * 
     * Converts the selected card ImageViews to Card objects by matching
     * their tag indices with the CardData source, then passes them to
     * the ThreeCardsFragment for the flip animation and reading display.
     * 
     * @see CardData#getMajorArcanaCards(android.content.Context)
     * @see androidx.navigation.fragment.NavHostFragment#findNavController(Fragment)
     */
    private void showCardReading() {
        // Convert selected ImageViews to Card objects
        List<Card> allCards = CardData.getMajorArcanaCards(requireContext());
        Card[] selectedCardArray = new Card[selectedCards.size()];
        
        for (int i = 0; i < selectedCards.size(); i++) {
            ImageView cardView = selectedCards.get(i);
            int cardIndex = (int) cardView.getTag();
            selectedCardArray[i] = allCards.get(cardIndex);
        }

        // Navigate to card reveal phase with selected cards
        Bundle bundle = new Bundle();
        bundle.putParcelableArray("selected_cards", selectedCardArray);
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_shuffleFragment_to_threeCardsFragment, bundle);
    }
}



