import database.fileParser;
import myClasses.Card;
import myClasses.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    static HashMap<Card, Integer> cardManager;
    static ArrayList<Player> playerManager;
    static ArrayList<Card> cardMarket;

    public static void main(String[] args) {

        /* Setting Up Data Sets */
        setupDataSets();


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
