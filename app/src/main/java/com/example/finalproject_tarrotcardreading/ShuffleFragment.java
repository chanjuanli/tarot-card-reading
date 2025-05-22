package com.example.finalproject_tarrotcardreading;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass for card shuffling and selection.
 * Enhanced with beautiful shuffle animation similar to professional tarot apps.
 */
public class ShuffleFragment extends Fragment {

    // Fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Fragment parameters
    private String mParam1;
    private String mParam2;

    // Animation and card constants
    private static final int CARD_COUNT = 22;
    private static final int SHUFFLE_ANIM_CARD_COUNT = 15;
    private static final int ANIM_DURATION = 2500;

    // Card selection tracking
    private List<ImageView> selectedCards = new ArrayList<>();
    private static final int MAX_SELECTED_CARDS = 3;

    public ShuffleFragment() {
        // Required empty public constructor
    }

    /**
     * Factory method to create a new instance of this fragment
     */
    public static ShuffleFragment newInstance(String param1, String param2) {
        ShuffleFragment fragment = new ShuffleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shuffle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ConstraintLayout root = view.findViewById(R.id.shuffle_root);
        TextView tip = view.findViewById(R.id.shuffle_tip);
        FrameLayout cardSpread = view.findViewById(R.id.card_spread_container);


        startEnhancedShuffleAnimation(root, cardSpread, tip);
    }

    /**
     * Creates and starts an enhanced shuffle animation similar to professional tarot apps
     */
    private void startEnhancedShuffleAnimation(ConstraintLayout root, FrameLayout cardSpread, TextView tip) {
        List<ImageView> animCards = new ArrayList<>();
        Random random = new Random();

        // Get screen dimensions for positioning
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

        // Create multiple cards for shuffle animation
        for (int i = 0; i < SHUFFLE_ANIM_CARD_COUNT; i++) {
            ImageView card = createAnimationCard();

            // Position cards in a scattered formation around center
            float angle = (360f / SHUFFLE_ANIM_CARD_COUNT) * i + random.nextFloat() * 60 - 30;
            float radius = 100 + random.nextFloat() * 200; // Random radius from center

            float x = (float) (centerX + radius * Math.cos(Math.toRadians(angle)));
            float y = (float) (centerY + radius * Math.sin(Math.toRadians(angle)));

            card.setX(x - card.getLayoutParams().width / 2);
            card.setY(y - card.getLayoutParams().height / 2);
            card.setRotation(random.nextFloat() * 360); // Random initial rotation
            card.setAlpha(0.8f + random.nextFloat() * 0.2f); // Slight alpha variation

            root.addView(card);
            animCards.add(card);
        }

        // Create complex shuffle animation
        AnimatorSet shuffleAnimation = createShuffleAnimation(animCards, centerX, centerY);
        shuffleAnimation.start();

        // After animation, show card selection
        shuffleAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // Remove animation cards
                for (ImageView card : animCards) {
                    root.removeView(card);
                }

                // Show card selection interface
                cardSpread.setVisibility(View.VISIBLE);
                tip.setText("Select 3 cards for your reading");

                // Wait for layout and setup horizontal cards
                cardSpread.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        cardSpread.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        setupHorizontalCardLayout(cardSpread, tip);
                    }
                });
            }
        });
    }

    /**
     * Creates a single card for animation
     */
    private ImageView createAnimationCard() {
        ImageView card = new ImageView(getContext());
        card.setImageResource(R.drawable.cover);

        int cardWidth = (int) (getResources().getDisplayMetrics().density * 70);
        int cardHeight = (int) (cardWidth * 1.4f);

        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(cardWidth, cardHeight);
        card.setLayoutParams(lp);
        card.setScaleType(ImageView.ScaleType.FIT_XY);

        return card;
    }

    /**
     * Creates complex shuffle animation with multiple movement patterns
     */
    private AnimatorSet createShuffleAnimation(List<ImageView> animCards, int centerX, int centerY) {
        List<Animator> allAnimators = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < animCards.size(); i++) {
            ImageView card = animCards.get(i);

            // Multiple animation phases
            List<Animator> cardAnimators = new ArrayList<>();

            // Phase 1: Spiral movement
            float spiralRadius = 150 + random.nextFloat() * 100;
            for (int phase = 0; phase < 3; phase++) {
                float targetAngle = (360f / animCards.size()) * i + phase * 120 + random.nextFloat() * 60;
                float targetRadius = spiralRadius + phase * 50;

                float targetX = (float) (centerX + targetRadius * Math.cos(Math.toRadians(targetAngle)));
                float targetY = (float) (centerY + targetRadius * Math.sin(Math.toRadians(targetAngle)));

                ObjectAnimator moveX = ObjectAnimator.ofFloat(card, "x", targetX - card.getLayoutParams().width / 2);
                ObjectAnimator moveY = ObjectAnimator.ofFloat(card, "y", targetY - card.getLayoutParams().height / 2);
                ObjectAnimator rotate = ObjectAnimator.ofFloat(card, "rotation", targetAngle + random.nextFloat() * 180);

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

            // Phase 2: Final gathering animation
            float finalAngle = random.nextFloat() * 360;
            float finalRadius = 80 + random.nextFloat() * 40;
            float finalX = (float) (centerX + finalRadius * Math.cos(Math.toRadians(finalAngle)));
            float finalY = (float) (centerY + finalRadius * Math.sin(Math.toRadians(finalAngle)));

            ObjectAnimator finalMoveX = ObjectAnimator.ofFloat(card, "x", finalX - card.getLayoutParams().width / 2);
            ObjectAnimator finalMoveY = ObjectAnimator.ofFloat(card, "y", finalY - card.getLayoutParams().height / 2);
            ObjectAnimator finalRotate = ObjectAnimator.ofFloat(card, "rotation", random.nextFloat() * 360);
            ObjectAnimator finalAlpha = ObjectAnimator.ofFloat(card, "alpha", 0.5f + random.nextFloat() * 0.5f);

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
     * Sets up the horizontal card layout for selection
     */
    private static final float CARD_ASPECT_RATIO = 342f / 197f;
    private void setupHorizontalCardLayout(FrameLayout cardSpread, TextView tip) {
        int containerWidth = cardSpread.getWidth();
        if (containerWidth == 0) {
            containerWidth = getResources().getDisplayMetrics().widthPixels;
        }
        int cardWidth = (int) (containerWidth * 0.22f);
        int cardHeight = (int) (cardWidth * CARD_ASPECT_RATIO);

        int spacing = Math.max((containerWidth - cardWidth) / (CARD_COUNT - 1), cardWidth / 4);
        // 让牌排在FrameLayout的下半部分
        int centerY = (int) (cardSpread.getHeight() * 0.65f);

        for (int i = 0; i < CARD_COUNT; i++) {
            ImageView card = new ImageView(getContext());
            card.setImageResource(R.drawable.cover);

            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(cardWidth, cardHeight);
            card.setLayoutParams(lp);
            card.setScaleType(ImageView.ScaleType.FIT_XY);

            float x = i * spacing;
            card.setX(Math.max(0, x));
            card.setY(centerY);
            card.setRotation((float)(Math.random() * 4 - 2));

            card.setClickable(true);
            card.setTag(i);
            card.setOnClickListener(v -> handleCardClick(card, tip));

            cardSpread.addView(card);
        }
    }

    /**
     * Handles card selection with visual feedback
     */
    private void handleCardClick(ImageView clickedCard, TextView tip) {
        if (selectedCards.contains(clickedCard)) {
            // 取消选中时的动画
            selectedCards.remove(clickedCard);

            ObjectAnimator moveBack = ObjectAnimator.ofFloat(clickedCard, "translationY", 0f);
            ObjectAnimator scaleBackX = ObjectAnimator.ofFloat(clickedCard, "scaleX", 1f);
            ObjectAnimator scaleBackY = ObjectAnimator.ofFloat(clickedCard, "scaleY", 1f);
            ObjectAnimator alphaBack = ObjectAnimator.ofFloat(clickedCard, "alpha", 1f);

            AnimatorSet backSet = new AnimatorSet();
            backSet.playTogether(moveBack, scaleBackX, scaleBackY, alphaBack);
            backSet.setDuration(300);
            backSet.start();

        } else {
            if (selectedCards.size() < MAX_SELECTED_CARDS) {
                // 选中卡牌时的动画
                selectedCards.add(clickedCard);

                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -12, getResources().getDisplayMetrics());

                ObjectAnimator moveY = ObjectAnimator.ofFloat(clickedCard, "translationY", px);
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(clickedCard, "scaleX", 1.15f);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(clickedCard, "scaleY", 1.15f);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(clickedCard, "alpha", 0.9f);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(moveY, scaleX, scaleY, alpha);
                animatorSet.setDuration(300);
                animatorSet.start();
            }
        }

        updateTipText(tip);
    }

    /**
     * Updates tip text based on selection
     */
    private void updateTipText(TextView tip) {
        int selectedCount = selectedCards.size();
        Button btn = getView().findViewById(R.id.complete_reading_button);

        if (selectedCount == 0) {
            tip.setText("Select 3 cards for your reading");
            btn.setVisibility(View.GONE);
        } else if (selectedCount < MAX_SELECTED_CARDS) {
            tip.setText("Selected " + selectedCount + " cards, need " + (MAX_SELECTED_CARDS - selectedCount) + " more");
            btn.setVisibility(View.GONE); // hide button
        } else {
            tip.setText("Perfect! " + MAX_SELECTED_CARDS + " cards selected");
            btn.setVisibility(View.VISIBLE); //show button
            btn.setOnClickListener(v -> showCardReading());
        }
    }

    private void showCardReading() {

    }
}

