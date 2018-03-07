package database;

import myClasses.Card;
import myClasses.Player;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is responsible for parsing all files pertaining to:
 * - Available Cards
 * - Cards on the market
 * - User Account Information
 */
public class fileParser {

    public String getPath() {
        return System.getProperty("user.dir") + "\\databaseFiles\\";
    }

    public HashMap<Card, Integer> parseCards() {

        return null;
    }

    public ArrayList<Card> parseCardsOnMarket() {
        return null;
    }

    public ArrayList<Player> parsePlayers() {
        return null;
    }

}
