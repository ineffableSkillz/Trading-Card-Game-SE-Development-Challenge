import myClasses.Player;
import resources.ResourceClass;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    /* Server Information */
    static int portno = 34512;
    static String addr = "192.168.122.1";

    /* Socket Stuff */
    static SSLSocket socket = null;
    static SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
    static PrintWriter out = null;
    static BufferedReader in = null;


    static final String filePath = "playerInfo.xml";

    /* Game Information */
    static Player player = null;

    public static void main(String[] args) {

        //getServerIp();

        connectToHost();
        while(true);
    }

    private static void connectToHost() {

        try(Scanner kbd = new Scanner(System.in)) {

            socket = (SSLSocket) factory.createSocket(addr, portno);

            String cipherSuits[] = socket.getSupportedCipherSuites(); //{"TLS_RSA_WITH_AES_128_CBC_SHA256"};
            socket.setEnabledCipherSuites(cipherSuits);
            socket.setUseClientMode(true);

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            /* Credential Validation */
            System.out.println(in.readLine()); //Username
            out.println(kbd.nextLine());

            System.out.println(in.readLine()); //Password
            out.println(kbd.nextLine());

            System.out.println(in.readLine()); //Result
            out.println("OK");

            writeToFile(in.readLine());
            generatePlayer(getPath() + filePath);
            System.out.println(player);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static String getPath() {
        return System.getProperty("user.dir") + "\\";
    }


    public static void writeToFile(String playerInfo) {

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(getPath() + filePath))) {

           bw.write(playerInfo);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void getServerIp() {

        try(Scanner kbd = new Scanner(System.in)) {

            System.out.println("Server IP: ");
            addr = kbd.nextLine();
        }

    }

    public static void generatePlayer(String filePath) {

        player = ResourceClass.parsePlayer(filePath);
    }

}
