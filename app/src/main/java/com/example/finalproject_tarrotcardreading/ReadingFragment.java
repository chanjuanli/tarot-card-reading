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

public class ReadingFragment extends Fragment {
    private static final String TAG = "ReadingFragment";
    private static final String ARG_SELECTED_CARDS = "selected_cards";
    private Card[] selectedCards;

    public ReadingFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ReadingFragment", "onCreate called");
        if (getArguments() != null) {
            Parcelable[] parcelables = getArguments().getParcelableArray("selected_cards");
            if (parcelables != null) {
                selectedCards = new Card[parcelables.length];
                for (int i = 0; i < parcelables.length; i++) {
                    selectedCards[i] = (Card) parcelables[i];
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reading, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (selectedCards != null && selectedCards.length >= 3) {
            displayCardInfo(view, selectedCards[0], 1);
            displayCardInfo(view, selectedCards[1], 2);
            displayCardInfo(view, selectedCards[2], 3);
        }

        // Set up home button
        Button btnHome = view.findViewById(R.id.btn_home);
        btnHome.setOnClickListener(v -> {
            // Navigate back to main fragment
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_readingFragment_to_mainFragment);
        });
    }

    private void displayCardInfo(View view, Card card, int cardNumber) {
        if (card == null) return;
        // Get TextViews for this card
        TextView titleView = view.findViewById(getResId("card" + cardNumber + "_title", R.id.class));
        TextView meaningView = view.findViewById(getResId("card" + cardNumber + "_meaning", R.id.class));
        TextView descriptionView = view.findViewById(getResId("card" + cardNumber + "_description", R.id.class));
        ImageView imageView = view.findViewById(getResId("card" + cardNumber + "_image", R.id.class));

        // Set card information
        titleView.setText(card.getName());
        meaningView.setText(getString(R.string.card_meaning_format,
                card.getUprightMeaning(),
                card.getReversedMeaning()));
        descriptionView.setText(getString(R.string.card_description_format,
                card.getDescription()));

        //set image
        imageView.setImageResource(card.getImageResId());
    }

    private int getResId(String resName, Class<?> c) {
        try {
            return c.getField(resName).getInt(null);
        } catch (Exception e) {
            Log.e(TAG, "Error getting resource ID for: " + resName, e);
            return -1;
        }
    }
}