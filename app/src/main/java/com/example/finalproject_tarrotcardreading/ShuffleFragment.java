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
    private static final float CARD_SCALE = 2.0f; // 变大一倍

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
        FrameLayout cardSpread = view.findViewById(R.id.card_spread_container);

        startEnhancedShuffleAnimation(root, cardSpread);
    }

    /**
     * Creates and starts an enhanced shuffle animation similar to professional tarot apps
     */
    private void startEnhancedShuffleAnimation(ConstraintLayout root, FrameLayout cardSpread) {
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
            int sideMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, getResources().getDisplayMetrics());
            int minX = sideMargin;
            int maxX = screenWidth - sideMargin - card.getLayoutParams().width;
            int minY = 0;
            int maxY = screenHeight;

            float x = minX + random.nextFloat() * (maxX - minX);
            float y = minY + random.nextFloat() * (maxY - minY);
            card.setX(x - card.getLayoutParams().width / 2);
            card.setY(y - card.getLayoutParams().height / 2);
            card.setRotation(0f);
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

                // Wait for layout and setup horizontal cards
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
     * Creates a single card for animation
     */
    private ImageView createAnimationCard() {
        ImageView card = new ImageView(getContext());
        card.setImageResource(R.drawable.cover);

        int cardWidth = (int) (getResources().getDisplayMetrics().density * 140);
        int cardHeight = (int) (cardWidth * 1.4f);

        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(cardWidth, cardHeight);
        card.setLayoutParams(lp);
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics());
        card.setPadding(padding, padding, padding, padding);
        card.setScaleType(ImageView.ScaleType.FIT_CENTER);

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
     * Sets up the three-row card layout for selection
     */
    private static final float CARD_ASPECT_RATIO = 684f / 394f;
    private void setupHorizontalCardLayout(FrameLayout cardSpread) {
        int containerWidth = cardSpread.getWidth();
        int containerHeight = cardSpread.getHeight();

        // 三行分布
        int[] cardsPerRow = {8, 7, 7};
        int totalRows = cardsPerRow.length;
        int cardIndex = 0;

        // 边距
        int sideMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        int availableWidth = containerWidth - 2 * sideMargin;

        // 计算最大一行的牌数
        int maxCardsInRow = 0;
        for (int n : cardsPerRow) {
            if (n > maxCardsInRow) maxCardsInRow = n;
        }

        // 计算最大允许的卡牌宽度
        int cardWidth = availableWidth / maxCardsInRow;
        int cardHeight = (int) (cardWidth * CARD_ASPECT_RATIO);

        // 行间距
        int rowSpacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());

        // 计算总高度，垂直居中
        int totalHeight = totalRows * cardHeight + (totalRows - 1) * rowSpacing;
        int offsetY = (containerHeight - totalHeight) / 2;

        for (int row = 0; row < totalRows; row++) {
            int cardsInThisRow = cardsPerRow[row];
            int rowTotalWidth = cardsInThisRow * cardWidth;
            int startX = sideMargin + (availableWidth - rowTotalWidth) / 2;
            int y = offsetY + row * (cardHeight + rowSpacing);

            for (int col = 0; col < cardsInThisRow && cardIndex < CARD_COUNT; col++, cardIndex++) {
                ImageView card = new ImageView(getContext());
                card.setImageResource(R.drawable.cover);

                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(cardWidth, cardHeight);
                card.setLayoutParams(lp);
                card.setScaleType(ImageView.ScaleType.FIT_CENTER);

                int x = startX + col * cardWidth;
                card.setX(x);
                card.setY(y);
                card.setRotation(0f);

                card.setClickable(true);
                card.setTag(cardIndex);
                card.setOnClickListener(v -> handleCardClick(card));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    card.setForeground(null);
                }

                cardSpread.addView(card);
            }
        }
    }

    /**
     * Handles card selection with very subtle visual feedback - 最小化移动效果
     */
    private void handleCardClick(ImageView clickedCard) {
        if (selectedCards.contains(clickedCard)) {
            selectedCards.remove(clickedCard);
            setCardUnselected(clickedCard);
        } else {
            if (selectedCards.size() < MAX_SELECTED_CARDS) {
                selectedCards.add(clickedCard);
                setCardSelected(clickedCard);
            }
        }
        updateTipText();
    }

    private void setCardSelected(ImageView card) {
        card.animate()
                .scaleX(1.08f)
                .scaleY(1.08f)
                .setDuration(200)
                .start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            card.setForeground(ContextCompat.getDrawable(getContext(), R.drawable.card_selected_glow));
        }
        card.clearColorFilter();
        card.setAlpha(1.0f);
    }

    private void setCardUnselected(ImageView card) {
        card.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(200)
                .start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            card.setForeground(null);
        }
        card.clearColorFilter();
        card.setAlpha(1.0f);
    }

    /**
     * Updates tip text based on selection
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
    private void showCardReading() {
        // Create array of selected cards
        List<Card> allCards = CardData.getMajorArcanaCards(requireContext());
        Card[] selectedCardArray = new Card[selectedCards.size()];
        for (int i = 0; i < selectedCards.size(); i++) {
            ImageView cardView = selectedCards.get(i);
            int cardIndex = (int) cardView.getTag();
            selectedCardArray[i] = allCards.get(cardIndex);
        }

        // Navigate to ThreeCardsFragment
        Bundle bundle = new Bundle();
        bundle.putParcelableArray("selected_cards", selectedCardArray);
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_shuffleFragment_to_threeCardsFragment, bundle);
    }
}



