package database;

import myClasses.Card;
import myClasses.Player;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is responsible for parsing all files pertaining to:
 * - Available Cards
 * - Cards on the market
 * - User Account Information
 */
public class fileParser {

    public static String getPath() {
        return System.getProperty("user.dir") + "\\databaseFiles\\";
    }

    public static HashMap<Card, Integer> parseCards() {

        String path = getPath() + "cardList.txt";

        try(BufferedReader br = new BufferedReader(new FileReader(path))) {



        } catch (FileNotFoundException e) {
            System.out.println("File not found at path: " + path);
        } catch (IOException e) {
            System.out.println("I/O Exception with file \'cardList.txt\'");
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Card> parseCardsOnMarket() {
        return null;
    }

    public static ArrayList<Player> parsePlayers() {
        return null;
    }

}
