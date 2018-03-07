package myClasses;

import java.util.HashMap;
import java.util.Objects;

public class Player {

    private String username;
    private int password;

    private HashMap<Card, Integer> ownedCards;

    private double money = 0.0;


    public Player(String usr, String pass, HashMap<Card, Integer> cardList, double dosh) {
        setUsername(usr);
        setPassword(passwordHash(pass));
        setOwnedCards(cardList);
        incMoney(dosh);

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
