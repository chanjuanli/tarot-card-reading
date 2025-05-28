# 📱 Tarot Card Reading App - Project Structure

## 🏗️ Overall Architecture
```
FinalProject_TarrotCardReading/
├── MainActivity (Entry Point)
├── Fragment Navigation System
└── Data Layer
```

## 📱 Fragment Flow & Class Hierarchy

```
MainActivity.java
    ↓ (hosts)
NavHostFragment
    ↓ (navigates between)
┌─────────────────────────────────────────────────────────────┐
│                    Fragment Navigation                      │
├─────────────────────────────────────────────────────────────┤
│ MainFragment → ShuffleFragment → ThreeCardsFragment → ReadingFragment │
└─────────────────────────────────────────────────────────────┘
```

## 🎯 Class Structure Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                     MAIN COMPONENTS                         │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  MainActivity.java                                          │
│  ├── extends AppCompatActivity                              │
│  └── manages → NavHostFragment                              │
│                                                             │
│  ┌─────────────────────────────────────────────────────────┐│
│  │                    FRAGMENTS                            ││
│  ├─────────────────────────────────────────────────────────┤│
│  │                                                         ││
│  │  MainFragment.java                                      ││
│  │  ├── extends Fragment                                   ││
│  │  ├── shows welcome screen                               ││
│  │  └── navigates to → ShuffleFragment                     ││
│  │                                                         ││
│  │  ShuffleFragment.java                                   ││
│  │  ├── extends Fragment                                   ││
│  │  ├── uses → CardData.getAllCards()                      ││
│  │  ├── implements card shuffling animation                ││
│  │  └── passes Card[] to → ThreeCardsFragment              ││
│  │                                                         ││
│  │  ThreeCardsFragment.java                                ││
│  │  ├── extends Fragment                                   ││
│  │  ├── receives Card[] from ShuffleFragment               ││
│  │  ├── implements flip animations                         ││
│  │  └── passes Card[] to → ReadingFragment                 ││
│  │                                                         ││
│  │  ReadingFragment.java                                   ││
│  │  ├── extends Fragment                                   ││
│  │  ├── receives Card[] from ThreeCardsFragment            ││
│  │  └── displays final tarot reading                       ││
│  │                                                         ││
│  └─────────────────────────────────────────────────────────┘│
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                     DATA LAYER                              │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Card.java                                                  │
│  ├── implements Parcelable                                  │
│  ├── @Entity (Room database)                                │
│  ├── Fields:                                                │
│  │   ├── int id                                             │
│  │   ├── String number                                      │
│  │   ├── String name                                        │
│  │   ├── int imageResource                                  │
│  │   ├── String uprightMeaning                              │
│  │   ├── String reversedMeaning                             │
│  │   ├── String description                                 │
│  │   └── boolean isReversed                                 │
│  └── Methods: getters, setters, constructors                │
│                                                             │
│  CardData.java                                              │
│  ├── static class (Factory Pattern)                         │
│  ├── getAllCards(Context) → List<Card>                      │
│  ├── creates all 22 Major Arcana cards                      │
│  ├── reads from strings.xml resources                       │
│  └── provides data to fragments                             │
│                                                             │
│  LocaleHelper.java                                          │
│  ├── handles internationalization                           │
│  ├── switchLanguage(Context, String)                        │
│  └── supports English/Chinese                               │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

## 🔄 Data Flow Diagram

```
CardData.getAllCards(Context)
    ↓ (provides)
List<Card> (22 Major Arcana)
    ↓ (used by)
ShuffleFragment
    ↓ (shuffles and selects 3)
Card[] selectedCards
    ↓ (passes via Bundle)
ThreeCardsFragment
    ↓ (animates and passes)
Card[] selectedCards
    ↓ (interprets)
ReadingFragment
```

## 🎨 Resource Dependencies

```
strings.xml (English)
├── card names (card_fool, card_magician, ...)
├── upright meanings (upright_fool, upright_magician, ...)
├── reversed meanings (reversed_fool, reversed_magician, ...)
└── descriptions (desc_fool, desc_magician, ...)

strings.xml (Chinese - values-zh)
└── Same structure as English with translations

drawable/
├── 22 tarot card images (.webp)
└── cover.webp (card back)

layout/
├── activity_main.xml
├── fragment_main.xml
├── fragment_shuffle.xml
├── fragment_three_cards.xml
└── fragment_reading.xml
```

## 🚀 Key Relationships

- **MainActivity** hosts all fragments via Navigation Component
- **All Fragments** receive and pass **Card[]** data through Bundle arguments
- **CardData** serves as central data provider using **Context** for string resources
- **Card** implements **Parcelable** for efficient fragment data transfer
- **LocaleHelper** provides multilingual support across all components 