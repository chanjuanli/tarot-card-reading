package com.example.finalproject_tarrotcardreading.data;

import android.content.Context;
import com.example.finalproject_tarrotcardreading.R;
import java.util.ArrayList;
import java.util.List;
import com.example.finalproject_tarrotcardreading.data.Card;

/**
 * Static data provider class for Tarot card information and creation.
 * <p>
 * The class uses the Context parameter to access localized string resources,
 * ensuring that card names, meanings, and descriptions are displayed in the
 * user's preferred language.
 * <p>
 * Usage Example:
 * <pre>{@code
 * List<Card> cards = CardData.getMajorArcanaCards(context);
 * Card foolCard = cards.get(0); // The Fool (card #0)
 * }**</pre>
 *
 * @author Chanjuan Li
 * @version 1.0
 * @see Card
 * @see android.content.Context
 * @see com.example.finalproject_tarrotcardreading.R.string
 * @see com.example.finalproject_tarrotcardreading.R.drawable
 * @since API level 1
 */
public class CardData {

    /**
     * Creates and returns a complete list of all 22 Major Arcana tarot cards.
     * <p>
     * This method constructs Card objects for all Major Arcana cards, numbered
     * from 0 (The Fool) to 21 (The World). Each card is created with:
     * - Proper sequential numbering (0-21)
     * - Localized names from string resources
     * - Corresponding drawable resource IDs for card images
     * - Localized upright and reversed meanings
     * - Localized descriptions and advice
     *
     * @param context The Android Context used to access string resources                for localized card names, meanings, and descriptions.                Must not be null.
     * @return A List containing all 22 Major Arcana Card objects in sequential         order (0-21). The list is guaranteed to contain exactly 22 cards.         Returns a new ArrayList instance on each call.
     * @throws NullPointerException                            if the context parameter is null
     * @throws android.content.res.Resources.NotFoundException if any required         string or drawable resources are missing from the app resources
     * @see Card#Card(String, String, int, String, String, String) Card#Card(String, String, int, String, String, String)Card#Card(String, String, int, String, String, String)
     * @see Context#getString(int) Context#getString(int)Context#getString(int)
     */
    public static List<Card> getMajorArcanaCards(Context context) {
        // Initialize ArrayList with expected capacity for performance
        List<Card> cards = new ArrayList<>();

        // Card 0: The Fool - New beginnings, innocence, spontaneity
        cards.add(new Card("0", context.getString(R.string.card_fool), R.drawable.fool,
                context.getString(R.string.upright_fool),
                context.getString(R.string.reversed_fool),
                context.getString(R.string.desc_fool)));

        // Card 1: The Magician - Manifestation, resourcefulness, power
        cards.add(new Card("1", context.getString(R.string.card_magician), R.drawable.magician,
                context.getString(R.string.upright_magician),
                context.getString(R.string.reversed_magician),
                context.getString(R.string.desc_magician)));

        // Card 2: The High Priestess - Intuition, sacred knowledge, divine feminine
        cards.add(new Card("2", context.getString(R.string.card_high_priestess), R.drawable.high_priestess,
                context.getString(R.string.upright_high_priestess),
                context.getString(R.string.reversed_high_priestess),
                context.getString(R.string.desc_high_priestess)));

        // Card 3: The Empress - Femininity, beauty, nature, nurturing
        cards.add(new Card("3", context.getString(R.string.card_empress), R.drawable.empress,
                context.getString(R.string.upright_empress),
                context.getString(R.string.reversed_empress),
                context.getString(R.string.desc_empress)));

        // Card 4: The Emperor - Authority, establishment, structure, father figure
        cards.add(new Card("4", context.getString(R.string.card_emperor), R.drawable.emperor,
                context.getString(R.string.upright_emperor),
                context.getString(R.string.reversed_emperor),
                context.getString(R.string.desc_emperor)));

        // Card 5: The Hierophant - Spiritual wisdom, religious beliefs, conformity
        cards.add(new Card("5", context.getString(R.string.card_hierophant), R.drawable.hierophant,
                context.getString(R.string.upright_hierophant),
                context.getString(R.string.reversed_hierophant),
                context.getString(R.string.desc_hierophant)));

        // Card 6: The Lovers - Love, harmony, relationships, values alignment
        cards.add(new Card("6", context.getString(R.string.card_lovers), R.drawable.lovers,
                context.getString(R.string.upright_lovers),
                context.getString(R.string.reversed_lovers),
                context.getString(R.string.desc_lovers)));

        // Card 7: The Chariot - Control, willpower, success, action, determination
        cards.add(new Card("7", context.getString(R.string.card_chariot), R.drawable.chariot,
                context.getString(R.string.upright_chariot),
                context.getString(R.string.reversed_chariot),
                context.getString(R.string.desc_chariot)));

        // Card 8: Strength - Inner strength, courage, persuasion, influence
        cards.add(new Card("8", context.getString(R.string.card_strength), R.drawable.strength,
                context.getString(R.string.upright_strength),
                context.getString(R.string.reversed_strength),
                context.getString(R.string.desc_strength)));

        // Card 9: The Hermit - Soul searching, introspection, guidance, solitude
        cards.add(new Card("9", context.getString(R.string.card_hermit), R.drawable.hermit,
                context.getString(R.string.upright_hermit),
                context.getString(R.string.reversed_hermit),
                context.getString(R.string.desc_hermit)));

        // Card 10: Wheel of Fortune - Good luck, karma, life cycles, destiny
        cards.add(new Card("10", context.getString(R.string.card_wheel_of_fortune), R.drawable.wheel_of_fortune,
                context.getString(R.string.upright_wheel_of_fortune),
                context.getString(R.string.reversed_wheel_of_fortune),
                context.getString(R.string.desc_wheel_of_fortune)));

        // Card 11: Justice - Justice, fairness, truth, cause and effect
        cards.add(new Card("11", context.getString(R.string.card_justice), R.drawable.justice,
                context.getString(R.string.upright_justice),
                context.getString(R.string.reversed_justice),
                context.getString(R.string.desc_justice)));

        // Card 12: The Hanged Man - Surrender, letting go, new perspective
        cards.add(new Card("12", context.getString(R.string.card_hanged_man), R.drawable.hanged_man,
                context.getString(R.string.upright_hanged_man),
                context.getString(R.string.reversed_hanged_man),
                context.getString(R.string.desc_hanged_man)));

        // Card 13: Death - Endings, beginnings, change, transformation
        cards.add(new Card("13", context.getString(R.string.card_death), R.drawable.death,
                context.getString(R.string.upright_death),
                context.getString(R.string.reversed_death),
                context.getString(R.string.desc_death)));

        // Card 14: Temperance - Balance, moderation, patience, purpose
        cards.add(new Card("14", context.getString(R.string.card_temperance), R.drawable.temperance,
                context.getString(R.string.upright_temperance),
                context.getString(R.string.reversed_temperance),
                context.getString(R.string.desc_temperance)));

        // Card 15: The Devil - Shadow self, attachment, addiction, restriction
        cards.add(new Card("15", context.getString(R.string.card_devil), R.drawable.devil,
                context.getString(R.string.upright_devil),
                context.getString(R.string.reversed_devil),
                context.getString(R.string.desc_devil)));

        // Card 16: The Tower - Sudden change, upheaval, chaos, revelation
        cards.add(new Card("16", context.getString(R.string.card_tower), R.drawable.tower,
                context.getString(R.string.upright_tower),
                context.getString(R.string.reversed_tower),
                context.getString(R.string.desc_tower)));

        // Card 17: The Star - Hope, faith, purpose, renewal, spirituality
        cards.add(new Card("17", context.getString(R.string.card_star), R.drawable.star,
                context.getString(R.string.upright_star),
                context.getString(R.string.reversed_star),
                context.getString(R.string.desc_star)));

        // Card 18: The Moon - Illusion, fear, anxiety, subconscious, intuition
        cards.add(new Card("18", context.getString(R.string.card_moon), R.drawable.moon,
                context.getString(R.string.upright_moon),
                context.getString(R.string.reversed_moon),
                context.getString(R.string.desc_moon)));

        // Card 19: The Sun - Positivity, fun, warmth, success, vitality
        cards.add(new Card("19", context.getString(R.string.card_sun), R.drawable.sun,
                context.getString(R.string.upright_sun),
                context.getString(R.string.reversed_sun),
                context.getString(R.string.desc_sun)));

        // Card 20: Judgement - Judgement, rebirth, inner calling, absolution
        cards.add(new Card("20", context.getString(R.string.card_judgement), R.drawable.judgement,
                context.getString(R.string.upright_judgement),
                context.getString(R.string.reversed_judgement),
                context.getString(R.string.desc_judgement)));

        // Card 21: The World - Completion, integration, accomplishment, travel
        cards.add(new Card("21", context.getString(R.string.card_world), R.drawable.world,
                context.getString(R.string.upright_world),
                context.getString(R.string.reversed_world),
                context.getString(R.string.desc_world)));

        return cards;
    }
}