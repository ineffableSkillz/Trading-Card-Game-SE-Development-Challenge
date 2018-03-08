package myEnums;

public enum CardRarity {

    COMMON(10.00), RARE(30.00), EPIC(50.00), LEGENDARY(100.00), UNIQUE(300.00);

    double rarityValue;
    CardRarity(double value) {
        rarityValue = value;
    }

    public double getRarityValue() { return rarityValue; }

}
