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

    /**
     * Document Factory of sorts. Implemented to remove duplicated code, code smell.
     * @param fileName Name of the .xml file
     * @return Document object used in the parsing
     */
    public static Document getDocument(String fileName) {

       try {

           /* File Setup */
           String filePath = getPath() + fileName;
           File inputFile = new File(filePath);

           /* Doc Object Setup */
           DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
           DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
           return docBuilder.parse(inputFile);

       } catch (ParserConfigurationException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       } catch (SAXException e) {
           e.printStackTrace();
       }

       return null;

    }


    public static HashMap<Card, Integer> parseCards() {

        HashMap<Card, Integer> returnMap = new HashMap<>();

        Document document = getDocument("cardList.xml");
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
            return returnMap;

    }

    public static ArrayList<Card> parseCardsOnMarket() {
        return null;
    }

    public static ArrayList<Player> parsePlayers() {

        ArrayList<Player> returnList = new ArrayList<>();

        Document document = getDocument("playerInfo.xml");
        document.getDocumentElement().normalize();

        /* Log Comment */
        System.out.println("Reading In XML of Root Element:" + document.getDocumentElement().getNodeName());

        NodeList nList = document.getElementsByTagName("player");

        for(int index = 0; index < nList.getLength(); index++) {

            Node currentNode = nList.item(index);

            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {

                /* Extracting Fields */
                Element element = (Element) currentNode;
                String username = element.getElementsByTagName("username").item(0).getTextContent();
                Integer password = Integer.parseInt(element.getElementsByTagName("password").item(0).getTextContent());
                double money = Double.parseDouble(element.getElementsByTagName("money").item(0).getTextContent());

                /* Checking if owns cards */
                HashMap<Card, Integer> playerCards = null;
                if (!(element.getElementsByTagName("owned_cards").item(0) == null)) { //If player owns cards

                    playerCards = new HashMap<>();
                    NodeList listOfOwnedCards = element.getElementsByTagName("owned_cards");

                    /* For Each Card */
                    for (int innerIndex = 0; innerIndex < listOfOwnedCards.getLength(); innerIndex++) {

                        Node currentInnerNode = nList.item(index);

                        if (currentInnerNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element innerElement = (Element) currentInnerNode;
                            String cardName = innerElement.getElementsByTagName("name").item(0).getTextContent();
                            Integer amount = Integer.parseInt(innerElement.getElementsByTagName("quantity").item(0).getTextContent());

                            String rarity = innerElement.getElementsByTagName("rarity").item(0).getTextContent();
                            for(CardRarity rareEnum : CardRarity.values()) {

                                if(rarity.equals(rareEnum.name())) {
                                    playerCards.put(new Card(cardName, rareEnum), amount);
                                    break;
                                }
                            }
                        }
                    }

                    returnList.add(new Player(username, password, money, playerCards));


                }

            }

        }
        return returnList;
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
