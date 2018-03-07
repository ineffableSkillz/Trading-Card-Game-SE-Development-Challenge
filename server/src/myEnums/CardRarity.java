package myEnums;

public enum CardRarity {

    COMMON(10), RARE(30), EPIC(50), LEGENDARY(100), UNIQUE(300);

    int rarityValue;
    CardRarity(int value) {
        rarityValue = value;
    }
}
