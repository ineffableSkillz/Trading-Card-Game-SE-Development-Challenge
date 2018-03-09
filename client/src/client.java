import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    /* Server Information */
    static int portno = 23456;
    static String addr = "";

    /* Socket Stuff */
    static SSLSocket socket = null;
    static SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
    static PrintWriter out = null;
    static BufferedReader in = null;

    public static void main(String[] args) {

        getServerIp();

        connectToHost();

    }

    private static void connectToHost() {

        try(Scanner kbd = new Scanner(System.in)) {

            socket = (SSLSocket) factory.createSocket(addr, portno);

            String cipherSuits[] = socket.getSupportedCipherSuites(); //{"TLS_RSA_WITH_AES_128_CBC_SHA256"};
            socket.setEnabledCipherSuites(cipherSuits);

            socket.addHandshakeCompletedListener(new MyHandShakeListener());
            socket.setUseClientMode(true);

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            /* Credential Validation */
            System.out.println(in.readLine()); //Username
            out.write(kbd.nextLine());

            System.out.println(in.readLine()); //Password
            out.write(kbd.nextLine());


        } catch (UnknownHostException e) {
            e.printStackTrace();
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

    static class MyHandShakeListener implements HandshakeCompletedListener {

        public void handshakeCompleted(HandshakeCompletedEvent e) {
            System.out.println("Handshake Successful");
            System.out.println("Using Cipher Suit: " + e.getCipherSuite());

        }
    }

}
