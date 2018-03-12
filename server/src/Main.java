import database.FileParser;
import myClasses.Card;
import myClasses.Player;
import resources.MyMutex;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static HashMap<Card, Integer> cardManager;
    private static ArrayList<Player> playerManager; //Need to re-consider player structure (inefficient during login)
    private static ArrayList<Card> cardMarket;
    private static ArrayList<Player> onlinePlayers = new ArrayList<>();


    private static final int portno = 34512;
    private static final int backlog = 10;
    private static InetAddress serverAddr = null;

    /**
     * Accepts incoming connections and asks for login details
     */
    static class AcceptThread implements Runnable {

        SSLServerSocket socket = null;

        @Override
        public void run() {
            while(true)
                validateUser(socket); // Will want to further thread this
        }

        public AcceptThread(SSLServerSocket s) {
            socket = s;
        }
    }

    /**
     * Class to represent a communication between the server and the client
     */
    static class CommunicationThread implements Runnable {

        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private Player player;

        @Override
        public void run() {
            while(true) {

            }
        }

        public CommunicationThread(Socket s, BufferedReader in, PrintWriter out, Player p) {
            socket = s;
            this.in = in;
            this.out = out;
            player = p;
        }


    }

    /**
     * Class/Thread to remove closed connections
     */
    static class CheckVoidConnectionThread implements Runnable {

        @Override
        public void run() {

        }
    }


    public static boolean isConnectionStillActive(CommunicationThread t) {
        return t.socket.isConnected();
    }

    public static void main(String[] args) {

        /* Setting Up Data Sets */
        setupDataSets();

        /* Setting Up Connection */
        openConnection();

        /* Testing */
        //printTests();

    }
    private static void printTests() {

        for(Map.Entry<Card, Integer> entry : cardManager.entrySet()) {
            Card tempCard = entry.getKey();
            Integer quantity = entry.getValue();

            System.out.println(tempCard);
            System.out.println("Quantity: " + quantity + "\n");
        }

        for(Player p : playerManager)
            System.out.println(p);

        FileParser.packAllPlayers(playerManager);
        FileParser.packAllCards(cardManager);

    }


    private static void openConnection() {

        /* Creating Server Socket */
        SSLServerSocket socket = null;
        String[] cipherSuites = null;
        final SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        socket = createServerSocket(factory, socket, cipherSuites, portno, backlog);

        /* Setting up accept thread */
        Runnable run = new AcceptThread(socket);
        Thread acceptingThread = new Thread(run);
        acceptingThread.start();

        /* Printing Out IP Address*/
        try {
            serverAddr = InetAddress.getLocalHost();
            System.out.println("Host Addr: " +serverAddr);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    /**
     * Creates the server socket that will be used
     * @param factory Server Socket Factory
     * @param socket Socket created by the Server Socket Factory
     * @param cipherSuites An array containing a list of available cipher suites to be used
     * @param portno Used port number
     * @param backlog Set backlog
     * @return
     */
    private static SSLServerSocket createServerSocket(SSLServerSocketFactory factory, SSLServerSocket socket, String[] cipherSuites, int portno, int backlog) {

        try {

            socket = (SSLServerSocket) factory.createServerSocket(portno, backlog, Inet4Address.getLocalHost());
            cipherSuites = socket.getSupportedCipherSuites();//{"TLS_RSA_WITH_AES_128_CBC_SHA256"};
            socket.setEnabledCipherSuites(cipherSuites);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }

    /**
     * Calls appropriate readers to setup the data sets for the server.
     */
    private static void setupDataSets() {
        cardManager = FileParser.parseCards();
        playerManager = FileParser.parsePlayers();
        cardMarket = FileParser.parseCardsOnMarket();
    }

    /**
     * This method takes in connections and questions the client for the username and password
     * @param socket Server socket used to accept a connection
     */
    private static void validateUser(SSLServerSocket socket) {

        PrintWriter out = null;
        BufferedReader in = null;
        MyMutex mutex = MyMutex.getInstance();

        String username = "";
        String password = "";

        try {

            /* Accepting Connection */
            Socket currentSocket = socket.accept();
            System.out.println("Connection Accepted");

            /* Setting Up Readers and Writers */
            out = new PrintWriter(currentSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(currentSocket.getInputStream()));


            Player playerInfo = isUserKnown(out, in, username, password);

            if(playerInfo != null) {

                /* Get Player Info */
                out.println("Credentials Accepted. Transferring data now...");
                in.readLine(); //Client 'OK' response
                out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?> " + playerInfo.packPlayer().replaceAll("\n", ""));


                Runnable run = new CommunicationThread(currentSocket, in, out, playerInfo);
                Thread cmdThread = new Thread(run);
                cmdThread.start();

                playerInfo.addCommunicationInfo(currentSocket, in, out, run, cmdThread);
                onlinePlayers.add(playerInfo);


            } else {

                out.println("Bad Credentials. You only get one shot because this is a 24hour project");
                in.readLine();
                currentSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Checks received details from user
     * @param out Writer
     * @param in Reader
     * @param username
     * @param password
     * @return Whether or not user credentials are correct
     */
    private static Player isUserKnown(PrintWriter out, BufferedReader in, String username, String password) {

        try{



            /* Obtain Username and Password */
            out.println("Username: ");
            username = in.readLine();
            out.println("Password: ");
            password = in.readLine();

            /* Check if it's valid */
            MyMutex mutex = MyMutex.getInstance();
            mutex.getPermission();

            /* Goes through list of players */
            for(Player p : playerManager) { //INEFFICIENT

                if(p.getUsername().equals(username))
                    if(p.getPassword() == p.passwordHash(password)) {
                        mutex.relinquishPermission();
                        return p;
                    }

                mutex.relinquishPermission();
                return null;

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
