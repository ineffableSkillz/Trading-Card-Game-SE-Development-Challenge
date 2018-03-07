package myClasses;

import myEnums.CardRarity;

public class Card {

    private static double originalValue;
    private static double sellingValue;

    private static String name;

    private CardRarity rarity;


    public Card(double value, String name, CardRarity rare) {
        setOriginalValue(value);
        setName(name);
        setRarity(rare);
    }






    /* Setters and Getters */

    public static double getOriginalValue() {
        return originalValue;
    }

    private static void setOriginalValue(double originalValue) {
        Card.originalValue = originalValue;
    }

    public static String getName() {
        return name;
    }

    private static void setName(String name) {
        Card.name = name;
    }

    public CardRarity getRarity() {
        return rarity;
    }

    private void setRarity(CardRarity rarity) {
        this.rarity = rarity;
    }
}
