package myClasses;

import myEnums.CardRarity;

public class Card {

    private static double originalValue;
    private static double sellingValue;

    private static String name;

    private CardRarity rarity;


    public Card(String name, CardRarity rare) {
        setName(name);
        setRarity(rare); //Sets original value too
    }

    public String toString() {
        return "Card Name: " + getName() + "\nCard Rarity: " + rarity.name() + "\nOriginal Value:" + getOriginalValue();
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
        originalValue = rarity.getRarityValue();
    }
}
