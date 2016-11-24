package fr.univtln.scaltot904;

import org.glassfish.tyrus.client.ClientManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * Created by Screetts on 31/03/2016.
 */
public class App {
    public static void main(String[] args) {
        ClientWebSocket beanClient = new ClientWebSocket("Stephan");
        try {
            final ClientManager client = ClientManager.createClient();
            client.connectToServer(
                    beanClient,
                    URI.create("ws://" + ClientWebSocket.SERVER_IP + ":" + ClientWebSocket.SERVER_PORT + "/echo")
            );

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Send empty line to stop the client.");
            String line;
            do {
                line = reader.readLine();
                if (!"".equals(line))
                    beanClient.sendMessage(line);
            } while (!"".equals(line));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                beanClient.closeSession();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}