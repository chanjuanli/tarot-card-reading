package com.example.finalproject_tarrotcardreading.data;

import com.example.finalproject_tarrotcardreading.R;
import java.util.ArrayList;
import java.util.List;

public class CardData {
    public static List<Card> getMajorArcanaCards() {
        List<Card> cards = new ArrayList<>();
        
        // 0 - The Fool
        cards.add(new Card(
            "0",
            "The Fool",
            R.drawable.fool,
            "New beginnings, innocence, spontaneity, free spirit, taking risks, new journey, potential",
            "Recklessness, carelessness, rushing in, foolishness, naivety, risk-taking to the point of danger",
            "You are ready for a new adventure. Trust yourself and embrace the unknown with optimism."
        ));
        
        // 1 - The Magician
        cards.add(new Card(
            "1",
            "The Magician",
            R.drawable.magician,
            "Manifestation, resourcefulness, power, inspired action, creation, talent, capabilities",
            "Manipulation, poor planning, untapped talents, latent abilities, insecurity, lack of planning",
            "You have the skills and resources to achieve your goals. Focus your energy and take inspired action."
        ));
        
        // 2 - The High Priestess
        cards.add(new Card(
            "2",
            "The High Priestess",
            R.drawable.high_priestess,
            "Intuition, sacred knowledge, divine feminine, the subconscious mind, mystery, spirituality",
            "Secrets, disconnection from intuition, withdrawal, silence, information withheld",
            "You are encouraged to trust your intuition and listen to your inner voice."
        ));
        
        // 3 - The Empress
        cards.add(new Card(
            "3",
            "The Empress",
            R.drawable.empress,
            "Femininity, beauty, nature, nurturing, abundance, creativity, fertility, motherhood",
            "Creative block, dependency on others, neglect, smothering, lack of growth, emptiness",
            "You are surrounded by abundance and creativity. Nurture yourself and those around you."
        ));
        
        // 4 - The Emperor
        cards.add(new Card(
            "4",
            "The Emperor",
            R.drawable.emperor,
            "Authority, establishment, structure, father figure, stability, protection, logic, rationality",
            "Domination, excessive control, rigidity, inflexibility, lack of compassion, immaturity",
            "You are called to take control and provide structure. Use your authority wisely and lead with confidence."
        ));
        
        // 5 - The Hierophant
        cards.add(new Card(
            "5",
            "The Hierophant",
            R.drawable.hierophant,
            "Spiritual wisdom, religious beliefs, tradition, conformity, education, knowledge, institutions",
            "Rebellion, subversiveness, freedom, personal beliefs, challenging the status quo",
            "You benefit from tradition and spiritual guidance. Seek wisdom from trusted sources."
        ));
        
        // 6 - The Lovers
        cards.add(new Card(
            "6",
            "The Lovers",
            R.drawable.lovers,
            "Love, harmony, relationships, values alignment, choices, union, partnerships",
            "Disharmony, imbalance, misalignment of values, disconnection, poor choices",
            "You are facing an important choice in love or values. Choose what brings you true happiness."
        ));
        
        // 7 - The Chariot
        cards.add(new Card(
            "7",
            "The Chariot",
            R.drawable.chariot,
            "Control, willpower, success, action, determination, overcoming obstacles, focus",
            "Lack of control, lack of direction, aggression, defeat, obstacles overwhelming you",
            "You have the determination to overcome obstacles. Stay focused and keep moving forward."
        ));
        
        // 8 - Strength
        cards.add(new Card(
            "8",
            "Strength",
            R.drawable.strength,
            "Inner strength, courage, persuasion, influence, compassion, patience, soft control",
            "Self-doubt, weakness, lack of self-discipline, insecurity, raw emotion, vulnerability",
            "You possess great inner strength. Approach challenges with patience and compassion."
        ));
        
        // 9 - The Hermit
        cards.add(new Card(
            "9",
            "The Hermit",
            R.drawable.hermit,
            "Soul-searching, introspection, being alone, inner guidance, spiritual enlightenment",
            "Isolation, loneliness, withdrawal, rejection, exile, abandonment",
            "You need time for introspection. Seek answers within and trust your inner guidance."
        ));
        
        // 10 - Wheel of Fortune
        cards.add(new Card(
            "10",
            "Wheel of Fortune",
            R.drawable.wheel_of_fortune,
            "Good luck, karma, life cycles, destiny, a turning point, change, fortune",
            "Bad luck, resistance to change, breaking cycles, unexpected events, downturns",
            "You are entering a period of good luck and positive change. Trust the flow and embrace new opportunities."
        ));
        
        // 11 - Justice
        cards.add(new Card(
            "11",
            "Justice",
            R.drawable.justice,
            "Justice, fairness, truth, cause and effect, law, balance, integrity",
            "Unfairness, lack of accountability, dishonesty, unbalanced scales, bias",
            "You are called to act with fairness and integrity. Make decisions based on truth and balance."
        ));
        
        // 12 - The Hanged Man
        cards.add(new Card(
            "12",
            "The Hanged Man",
            R.drawable.hanged_man,
            "Surrender, letting go, new perspective, enlightenment, sacrifice, release, martyrdom",
            "Stalling, resistance, indecision, delay, feeling stuck or trapped",
            "You are encouraged to let go and see things from a new perspective. Surrender brings insight."
        ));
        
        // 13 - Death
        cards.add(new Card(
            "13",
            "Death",
            R.drawable.death,
            "Endings, change, transformation, transition, letting go, release",
            "Resistance to change, inability to move on, stagnation, decay, fear of new beginnings",
            "You are experiencing transformation. Let go of the old to welcome new beginnings."
        ));
        
        // 14 - Temperance
        cards.add(new Card(
            "14",
            "Temperance",
            R.drawable.temperance,
            "Balance, moderation, patience, purpose, harmony, finding middle ground",
            "Imbalance, excess, lack of long-term vision, discord, hastiness, recklessness",
            "You are finding balance in your life. Practice patience and moderation for harmony."
        ));
        
        // 15 - The Devil
        cards.add(new Card(
            "15",
            "The Devil",
            R.drawable.devil,
            "Shadow self, attachment, addiction, restriction, sexuality, materialism",
            "Breaking free, release, restoring control, power reclaimed, detachment",
            "You may feel restricted by unhealthy patterns. Recognize what holds you back and reclaim your power."
        ));
        
        // 16 - The Tower
        cards.add(new Card(
            "16",
            "The Tower",
            R.drawable.tower,
            "Sudden change, upheaval, chaos, revelation, awakening, truth, destruction",
            "Averting disaster, fear of change, delaying necessary collapse, resisting transformation",
            "You are facing sudden change. Embrace the truth and allow transformation to happen."
        ));
        
        // 17 - The Star
        cards.add(new Card(
            "17",
            "The Star",
            R.drawable.star,
            "Hope, faith, purpose, renewal, spirituality, inspiration, serenity",
            "Lack of faith, despair, discouragement, disconnection from self, insecurity",
            "You are filled with hope and inspiration. Trust in yourself and look forward to renewal."
        ));
        
        // 18 - The Moon
        cards.add(new Card(
            "18",
            "The Moon",
            R.drawable.moon,
            "Illusion, fear, anxiety, subconscious, intuition, dreams, the unknown",
            "Release of fear, repressed emotions coming to surface, clarity, overcoming deception",
            "You are navigating uncertainty. Trust your intuition and be aware of illusions."
        ));
        
        // 19 - The Sun
        cards.add(new Card(
            "19",
            "The Sun",
            R.drawable.sun,
            "Success, joy, abundance, optimism, vitality, happiness, clarity, enlightenment",
            "Temporary depression, lack of success, pessimism, sadness, clouded judgment",
            "You are experiencing joy and success. Celebrate your achievements and share your happiness."
        ));
        
        // 20 - Judgement
        cards.add(new Card(
            "20",
            "Judgement",
            R.drawable.judgement,
            "Judgement, rebirth, inner calling, absolution, awakening, renewal, purpose",
            "Self-doubt, refusal of self-examination, lack of self-awareness, failure to learn lessons",
            "You are awakening to a new purpose. Reflect on your past and embrace your true calling."
        ));
        
        // 21 - The World
        cards.add(new Card(
            "21",
            "The World",
            R.drawable.world,
            "Completion, integration, accomplishment, travel, wholeness, fulfillment",
            "Incompletion, shortcuts, delays, lack of closure, stagnation, inability to finish",
            "You are reaching a place of fulfillment and completion. Celebrate your journey and achievements."
        ));
        
        return cards;
    }
} 