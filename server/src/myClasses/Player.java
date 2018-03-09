package myClasses;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Player {

    private String username;
    private int password;

    private HashMap<Card, Integer> ownedCards = null;

    private double money = 0.0;


    public Player(String usr, Integer pass, double dosh, HashMap<Card, Integer> cardList) {

        username = usr;
        password = pass;
        money = dosh;
        ownedCards = cardList;

    }

    public String toString() {
        return "Username: " + getUsername() +
                "\nMoney:" + getMoney() +
                "\nOwnedCards:\n" + getCardsToString();
    }

    public String getCardsToString() {

        StringBuilder sb = new StringBuilder();

        for(Map.Entry<Card, Integer> entry : ownedCards.entrySet()) {
            Card tempCard = entry.getKey();
            Integer quantity = entry.getValue();

            sb.append(tempCard.toString() + "\nNoOwned: " + quantity);

        }

        return sb.toString();
    }

    /**
     * Creates a hash for the passwords
     * @return passwordHash
     */
    public int passwordHash(String hashMe) {
        return Objects.hash(hashMe);
    }







    /* Setters and Getters */

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public int getPassword() {
        return password;
    }

    private void setPassword(int password) {
        this.password = password;
    }

    private HashMap<Card, Integer> getOwnedCards() {
        return ownedCards;
    }

    public void setOwnedCards(HashMap<Card, Integer> ownedCards) {
        this.ownedCards = ownedCards;
    }

    public double getMoney() {
        return money;
    }

    public void incMoney(double money) {
        this.money += money;
    }
}
