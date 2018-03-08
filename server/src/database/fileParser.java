package database;

import myClasses.Card;
import myClasses.Player;
import myEnums.CardRarity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
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

        HashMap<Card, Integer> returnMap = new HashMap<>();

        try {
            /* File Setup */
            String filePath = getPath() + "cardList.xml";
            File inputFile = new File(filePath);

            /* Doc Object Setup */
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document document = docBuilder.parse(inputFile);
            document.getDocumentElement().normalize();

            /* Log Comment */
            System.out.println("Reading In XML of Root Element:" + document.getDocumentElement().getNodeName());

            /* Getting Node List */
            ArrayList<NodeList> listOfNodes = new ArrayList<>();
            listOfNodes.add(document.getElementsByTagName("common_cards"));
            listOfNodes.add(document.getElementsByTagName("rare_cards"));
            listOfNodes.add(document.getElementsByTagName("epic_cards"));
            listOfNodes.add(document.getElementsByTagName("legendary_cards"));
            listOfNodes.add(document.getElementsByTagName("unique_cards"));

            for (NodeList nList : listOfNodes) { //For each nList


                /* Determine what type of cards are being handled here */

                for (int index = 0; index < nList.getLength(); index++) { //For each node in the nList

                    Node currentNode = nList.item(index);
                    CardRarity rarity = getRarity(currentNode.getNodeName()); //INEFFICIENT

                    if (currentNode.getNodeType() == Node.ELEMENT_NODE) { //Don't quite understand this

                        Element element = (Element) currentNode;

                        String cardName = element.getElementsByTagName("name").item(0).getTextContent();
                        Integer quantity = Integer.parseInt(
                                element.getElementsByTagName("quantity").item(0).getTextContent());
                        returnMap.put(new Card(cardName, rarity), quantity);
                    }

                }

            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return returnMap;
    }

    public static ArrayList<Card> parseCardsOnMarket() {
        return null;
    }

    public static ArrayList<Player> parsePlayers() {
        return null;
    }

    public static CardRarity getRarity(String str) {

        switch(str) {

            case "common_cards":
                return CardRarity.COMMON;
            case "rare_cards":
                return CardRarity.RARE;
            case "epic_cards":
                return CardRarity.EPIC;
            case "legendary_cards":
                return CardRarity.LEGENDARY;
            case "unique_cards":
                return CardRarity.UNIQUE;
            default:
                return null;

        }

    }

}
