package com.example.finalproject_tarrotcardreading.data;

import android.content.Context;
import com.example.finalproject_tarrotcardreading.R;
import java.util.ArrayList;
import java.util.List;
import com.example.finalproject_tarrotcardreading.data.Card;

public class CardData {
    public static List<Card> getMajorArcanaCards(Context context) {
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("0", context.getString(R.string.card_fool), R.drawable.fool,
                context.getString(R.string.upright_fool),
                context.getString(R.string.reversed_fool),
                context.getString(R.string.desc_fool)));

        cards.add(new Card("1", context.getString(R.string.card_magician), R.drawable.magician,
                context.getString(R.string.upright_magician),
                context.getString(R.string.reversed_magician),
                context.getString(R.string.desc_magician)));

        cards.add(new Card("2", context.getString(R.string.card_high_priestess), R.drawable.high_priestess,
                context.getString(R.string.upright_high_priestess),
                context.getString(R.string.reversed_high_priestess),
                context.getString(R.string.desc_high_priestess)));

        cards.add(new Card("3", context.getString(R.string.card_empress), R.drawable.empress,
                context.getString(R.string.upright_empress),
                context.getString(R.string.reversed_empress),
                context.getString(R.string.desc_empress)));

        cards.add(new Card("4", context.getString(R.string.card_emperor), R.drawable.emperor,
                context.getString(R.string.upright_emperor),
                context.getString(R.string.reversed_emperor),
                context.getString(R.string.desc_emperor)));

        cards.add(new Card("5", context.getString(R.string.card_hierophant), R.drawable.hierophant,
                context.getString(R.string.upright_hierophant),
                context.getString(R.string.reversed_hierophant),
                context.getString(R.string.desc_hierophant)));

        cards.add(new Card("6", context.getString(R.string.card_lovers), R.drawable.lovers,
                context.getString(R.string.upright_lovers),
                context.getString(R.string.reversed_lovers),
                context.getString(R.string.desc_lovers)));

        cards.add(new Card("7", context.getString(R.string.card_chariot), R.drawable.chariot,
                context.getString(R.string.upright_chariot),
                context.getString(R.string.reversed_chariot),
                context.getString(R.string.desc_chariot)));

        cards.add(new Card("8", context.getString(R.string.card_strength), R.drawable.strength,
                context.getString(R.string.upright_strength),
                context.getString(R.string.reversed_strength),
                context.getString(R.string.desc_strength)));

        cards.add(new Card("9", context.getString(R.string.card_hermit), R.drawable.hermit,
                context.getString(R.string.upright_hermit),
                context.getString(R.string.reversed_hermit),
                context.getString(R.string.desc_hermit)));

        cards.add(new Card("10", context.getString(R.string.card_wheel_of_fortune), R.drawable.wheel_of_fortune,
                context.getString(R.string.upright_wheel_of_fortune),
                context.getString(R.string.reversed_wheel_of_fortune),
                context.getString(R.string.desc_wheel_of_fortune)));

        cards.add(new Card("11", context.getString(R.string.card_justice), R.drawable.justice,
                context.getString(R.string.upright_justice),
                context.getString(R.string.reversed_justice),
                context.getString(R.string.desc_justice)));

        cards.add(new Card("12", context.getString(R.string.card_hanged_man), R.drawable.hanged_man,
                context.getString(R.string.upright_hanged_man),
                context.getString(R.string.reversed_hanged_man),
                context.getString(R.string.desc_hanged_man)));

        cards.add(new Card("13", context.getString(R.string.card_death), R.drawable.death,
                context.getString(R.string.upright_death),
                context.getString(R.string.reversed_death),
                context.getString(R.string.desc_death)));

        cards.add(new Card("14", context.getString(R.string.card_temperance), R.drawable.temperance,
                context.getString(R.string.upright_temperance),
                context.getString(R.string.reversed_temperance),
                context.getString(R.string.desc_temperance)));

        cards.add(new Card("15", context.getString(R.string.card_devil), R.drawable.devil,
                context.getString(R.string.upright_devil),
                context.getString(R.string.reversed_devil),
                context.getString(R.string.desc_devil)));

        cards.add(new Card("16", context.getString(R.string.card_tower), R.drawable.tower,
                context.getString(R.string.upright_tower),
                context.getString(R.string.reversed_tower),
                context.getString(R.string.desc_tower)));

        cards.add(new Card("17", context.getString(R.string.card_star), R.drawable.star,
                context.getString(R.string.upright_star),
                context.getString(R.string.reversed_star),
                context.getString(R.string.desc_star)));

        cards.add(new Card("18", context.getString(R.string.card_moon), R.drawable.moon,
                context.getString(R.string.upright_moon),
                context.getString(R.string.reversed_moon),
                context.getString(R.string.desc_moon)));

        cards.add(new Card("19", context.getString(R.string.card_sun), R.drawable.sun,
                context.getString(R.string.upright_sun),
                context.getString(R.string.reversed_sun),
                context.getString(R.string.desc_sun)));

        cards.add(new Card("20", context.getString(R.string.card_judgement), R.drawable.judgement,
                context.getString(R.string.upright_judgement),
                context.getString(R.string.reversed_judgement),
                context.getString(R.string.desc_judgement)));

        cards.add(new Card("21", context.getString(R.string.card_world), R.drawable.world,
                context.getString(R.string.upright_world),
                context.getString(R.string.reversed_world),
                context.getString(R.string.desc_world)));

        return cards;
    }
}