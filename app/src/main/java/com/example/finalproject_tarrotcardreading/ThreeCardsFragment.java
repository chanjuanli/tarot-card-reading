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
 * A simple {@link Fragment} subclass.
 * Use the {@link ThreeCardsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThreeCardsFragment extends Fragment {

    

    private static final String ARG_SELECTED_CARDS = "selected_cards";
    private Card[] selectedCards;
    private ImageView card2;



    public ThreeCardsFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment ThreeCardsFragment.
     */
    public static ThreeCardsFragment newInstance(Card[] cards) {
        ThreeCardsFragment fragment = new ThreeCardsFragment();
        Bundle args = new Bundle();
        args.putParcelableArray(ARG_SELECTED_CARDS, cards);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedCards = (Card[]) getArguments().getParcelableArray(ARG_SELECTED_CARDS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_three_cards, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        ImageView card1 = view.findViewById(R.id.card1);
        card2 = view.findViewById(R.id.card2);
        ImageView card3 = view.findViewById(R.id.card3);
        Button btnInterpret = view.findViewById(R.id.btn_interpret);

        // Set initial card backs
        card1.setImageResource(R.drawable.cover);
        card2.setImageResource(R.drawable.cover);
        card3.setImageResource(R.drawable.cover);

        // Start flip animations with delay
        startFlipAnimation(card1, selectedCards[0], 0);
        startFlipAnimation(card2, selectedCards[1], 400);
        startFlipAnimation(card3, selectedCards[2], 800);

        // Set up interpret button
        btnInterpret.setOnClickListener(v -> {
            Log.d("ThreeCardsFragment", "Interpret button clicked");
            Bundle bundle = new Bundle();
            bundle.putParcelableArray("selected_cards", selectedCards);
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_threeCardsFragment_to_readingFragment, bundle);
        });

        // Log card details
        for (Card c : selectedCards) {
            Log.d("CardDebug", "name=" + c.getName() + ", desc=" + c.getDescription() + ", up=" + c.getUprightMeaning() + ", rev=" + c.getReversedMeaning() + ", img=" + c.getImageResource());
        }
    }

    private void startFlipAnimation(ImageView cardView, Card card, long delay) {
        cardView.postDelayed(() -> {
            // First half of flip
            ObjectAnimator flipOut = ObjectAnimator.ofFloat(cardView, "rotationY", 0f, 90f);
            flipOut.setDuration(300);
            flipOut.setInterpolator(new AccelerateDecelerateInterpolator());

            // Second half of flip
            ObjectAnimator flipIn = ObjectAnimator.ofFloat(cardView, "rotationY", -90f, 0f);
            flipIn.setDuration(300);
            flipIn.setInterpolator(new AccelerateDecelerateInterpolator());

            // Create animation sequence
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(flipOut, flipIn);

            // Change card image at midpoint
            flipOut.addListener(new android.animation.AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(android.animation.Animator animation) {
                    cardView.setImageResource(card.getImageResId());
                }
            });

            animatorSet.start();
        }, delay);
    }
}