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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class is responsible for parsing all files pertaining to:
 * - Available Cards
 * - Cards on the market
 * - User Account Information
 */
public class FileParser {

    static final String cardPath = "cardList.xml";
    static final String playerPath = "playerInfo.xml";

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

    /**
     * Parser to read in the available cards from the xml file
     * @return Map of cards and their quantities
     */
    public static HashMap<Card, Integer> parseCards() {

        HashMap<Card, Integer> returnMap = new HashMap<>();

        Document document = getDocument(cardPath);
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

    /**
     * Used by parseCards() to obtain the correct enum corresponding
     * to the read card's rarity.
     * @param str Holds string pertaining to card's rarity
     * @return CardRarity enum to be set within the card
     * @see parseCards()
     */
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


    public static ArrayList<Card> parseCardsOnMarket() {
        return null;
    }

    /**
     * Parser to read in all of the players from the xml file
     * @return ArrayList of all players
     */
    public static ArrayList<Player> parsePlayers() {

        ArrayList<Player> returnList = new ArrayList<>();

        Document document = getDocument(playerPath);
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



    /* Saving Methods */

    /**
     * Used to write all of the players to the xml file
     * @param listOfPlayers ArrayList holding all players
     */
    public static void packAllPlayers(ArrayList<Player> listOfPlayers) {

        StringBuilder sb = new StringBuilder();

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("\n");
        sb.append("<player_collection>\n");

        for(Player p : listOfPlayers)
            sb.append(p.packPlayer() + "\n");

        sb.append("</player_collection>");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(getPath() + playerPath))){

            bw.write(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Used to write all of the cards to the xml file
     * @param listOfCards HashMap holding of the cards and their quantities
     */
    public static void packAllCards(HashMap<Card, Integer> listOfCards) {

        StringBuilder sb = new StringBuilder();

        /* XML Header */
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n");
        sb.append("<tradingCards>\n");

        /* Obtaining Card Ranks in Order */
        LinkedHashMap<CardRarity, ArrayList<Card>> orderedListOfCards =
                getCardsOrganisedByRarity(listOfCards);

        /* Building up card info */
        for(Map.Entry<CardRarity, ArrayList<Card>> entry : orderedListOfCards.entrySet()) {

            sb.append("\t" + entry.getKey().getStartXMLRarity() + "\n");

            for(Card card : entry.getValue()) {

                String cardName = card.getName();
                Integer quantity = listOfCards.get(card);

                sb.append("\t\t<card>\n");
                    sb.append("\t\t\t<name>" + cardName + "</name>\n");
                    sb.append("\t\t\t<quantity>" + quantity + "</quantity>\n");
                sb.append("\t\t</card>\n");

            }
            sb.append("\t" + entry.getKey().getEndXMLRarity() + "\n\n");

        }
        sb.append("</tradingCards>");

        /*Writing Info to File*/
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(getPath() + cardPath))) {

            bw.write(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used by packAllCards() to obtain cards by rarity to preserve the readable format of the .xml document
     * @param listOfCards Not guaranteed order of cards
     * @return returnMap Mapping of <CardRarity, ArrayList<Card>>
     * @see packAllCards
     */
    public static LinkedHashMap<CardRarity, ArrayList<Card>> getCardsOrganisedByRarity(HashMap<Card, Integer> listOfCards) {

        ArrayList<Card> commonCardList = new ArrayList<>();
        ArrayList<Card> rareCardList = new ArrayList<>();
        ArrayList<Card> epicCardList = new ArrayList<>();
        ArrayList<Card> legendaryCardList = new ArrayList<>();
        ArrayList<Card> uniqueCardList = new ArrayList<>();

        for(Map.Entry<Card, Integer> entry : listOfCards.entrySet()) {

            Card tempCard = entry.getKey();

            switch(tempCard.getRarity()) {

                case COMMON:
                    commonCardList.add(tempCard); break;
                case RARE:
                    rareCardList.add(tempCard); break;
                case EPIC:
                    epicCardList.add(tempCard); break;
                case LEGENDARY:
                    legendaryCardList.add(tempCard); break;
                case UNIQUE:
                    uniqueCardList.add(tempCard); break;

            }

        }

        /* Setting Up Returned LinkedHashMap */
        LinkedHashMap<CardRarity, ArrayList<Card>> returnMap = new LinkedHashMap<>();
        returnMap.put(CardRarity.COMMON, commonCardList);
        returnMap.put(CardRarity.RARE, rareCardList);
        returnMap.put(CardRarity.EPIC, epicCardList);
        returnMap.put(CardRarity.LEGENDARY, legendaryCardList);
        returnMap.put(CardRarity.UNIQUE, uniqueCardList);
        return returnMap;
    }

}
