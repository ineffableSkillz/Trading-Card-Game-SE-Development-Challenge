package myEnums;

public enum CardRarity {

    COMMON(10.00, "<common_cards>", "</common_cards>"),
    RARE(30.00, "<rare_cards>", "</rare_cards>"),
    EPIC(50.00, "<epic_cards>", "</epic_cards>"),
    LEGENDARY(100.00, "<legendary_cards>", "</legendary_cards>"),
    UNIQUE(300.00, "<unique_cards>", "</unique_cards>");

    double rarityValue;
    String openXML, closeXML;
    CardRarity(double value, String open, String close) {

        rarityValue = value;
        openXML = open;
        closeXML = close;
    }

    public double getRarityValue() { return rarityValue; }

    public String getStartXMLRarity() { return openXML; }

    public String getEndXMLRarity() { return closeXML; }

}
