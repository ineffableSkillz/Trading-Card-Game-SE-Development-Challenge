import database.fileParser;
import myClasses.Card;
import myClasses.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    static HashMap<Card, Integer> cardManager;
    static ArrayList<Player> playerManager;
    static ArrayList<Card> cardMarket;

    public static void main(String[] args) {

        /* Setting Up Data Sets */
        setupDataSets();

        for(Map.Entry<Card, Integer> entry : cardManager.entrySet()) {
            Card tempCard = entry.getKey();
            Integer quantity = entry.getValue();

            System.out.println(tempCard);
            System.out.println("Quantity: " + quantity + "\n");
        }

        for(Player p : playerManager)
            System.out.println(p);

    }

    /**
     * Calls appropriate readers to setup the data sets for the server.
     */
    private static void setupDataSets() {
        cardManager = fileParser.parseCards();
        playerManager = fileParser.parsePlayers();
        cardMarket = fileParser.parseCardsOnMarket();
    }
}
