import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import fr.univtln.scaltot904.ClientWebSocket;
import fr.univtln.scaltot904.TP.tpJpa.Classes.CCompetition;
import fr.univtln.scaltot904.TP.tpJpa.Test.CServer;
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
        ClientWebSocket beanClient = null;
        try {
            beanClient = new ClientWebSocket("Stephan");
            final ClientManager client = ClientManager.createClient();
            client.connectToServer(
                    beanClient,
                    URI.create("ws://" + ClientWebSocket.SERVER_IP + ":" + ClientWebSocket.SERVER_PORT + "/echo")
            );

            Client c = Client.create();
            WebResource webResource = c.resource(CServer.BASE_URI);

            CCompetition compet = new CCompetition();
            compet.setName("LigueWebSocket");
            compet.setCountry("PaysWebSocket");
            compet.setNbteam(20);
            //compet.setId(177);
            webResource.path("competition").post(compet);

            beanClient.sendMessage("Competition "+compet.getName().toString() +" ajoutée ! ");


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