package resources;

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
import java.util.HashMap;

public class ResourceClass {


    /**
     * Document Factory of sorts. Implemented to remove duplicated code, code smell.
     * @param playerData .xml in String form to be parsed
     * @return Document object used in the parsing
     */
    public static Document getDocument(String filePath) {

        try {

            /* File Setup */
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
     * Creates a player object based off of the received data
     * @param playerData String containing player data received from the server
     * @return Player object
     */
    public static Player parsePlayer(String playerPath) {

        Document document = getDocument(playerPath);
        document.getDocumentElement().normalize();

        /* Log Comment */
        System.out.println("Reading In XML of Root Element:" + document.getDocumentElement().getNodeName());

        NodeList nList = document.getElementsByTagName("player");

        Node currentNode = nList.item(0);
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

                    Node currentInnerNode = nList.item(innerIndex);

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
            }

            return new Player(username, password, money, playerCards);
        }

        return null;
    }

}
