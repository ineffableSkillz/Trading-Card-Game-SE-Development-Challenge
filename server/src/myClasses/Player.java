package myClasses;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Player {

    private String username;
    private int password;

    private HashMap<Card, Integer> ownedCards = null;

    private double money = 0.0;


    private Socket socket = null;
    private BufferedReader br = null;
    private PrintWriter pw = null;

    private Runnable run = null;
    private Thread cmdThread = null;



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

    /**
     * Used to obtain in-game information about the player's cards
     * @return A string containing all information about a player's cards.
     */
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
     * Used to save player state when the server is shut down
     * @return A string containing all player data to be written to the .XML file.
     */
    public String packPlayer() {
        StringBuilder sb = new StringBuilder();

        sb.append("<player>\n");

        sb.append("\t<username>" + getUsername() + "</username>\n");
        sb.append("\t<password>" + getPassword() + "</password>\n");
        sb.append("\t<money>" + getMoney() + "</money>\n");
        sb.append("\n");

        sb.append("\t" + packPlayerCards());

        sb.append("</player>\n");

        return sb.toString();
    }

    /**
     * Used by packPlayer() to pack all of the player's cards.
     * @return Player's cards in .XML format
     */
    public String packPlayerCards() {

        StringBuilder sb = new StringBuilder();
        sb.append("<owned_cards>\n");

        for(Map.Entry<Card, Integer> entry : ownedCards.entrySet()) {
            Card tempCard = entry.getKey();

            String name = tempCard.getName();
            String rarity = tempCard.getRarity().name(); //COMMON, RARE etc.
            Integer quantity = entry.getValue();

            sb.append("\t\t<card>\n");
            sb.append("\t\t\t<name>" + name + "</name>\n" +
                        "\t\t\t<rarity>" + rarity + "</rarity>\n" +
                            "\t\t\t<quantity>" + quantity + "</quantity>\n");
            sb.append("\t\t</card>\n");
        }

        sb.append("\t</owned_cards>\n");

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

    public void addCommunicationInfo(Socket currentSocket, BufferedReader in, PrintWriter out, Runnable run, Thread cmdThread) {

        setSocket(currentSocket);
        setReader(in);
        setWriter(out);
        setRunnable(run);
        setThread(cmdThread);

    }

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

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public BufferedReader getReader() {
        return br;
    }

    public void setReader(BufferedReader br) {
        this.br = br;
    }

    public PrintWriter getWriter() {
        return pw;
    }

    public void setWriter(PrintWriter pw) {
        this.pw = pw;
    }

    public void setRunnable(Runnable runnable) {
        this.run = runnable;
    }

    public void setThread(Thread t) {
        this.cmdThread = t;
    }
}
