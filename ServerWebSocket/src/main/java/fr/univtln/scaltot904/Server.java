package fr.univtln.scaltot904;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


@ServerEndpoint(value = "/echo")
public class Server {
    private static List<Session> sessions = new ArrayList<Session>();
    public final static String SERVER_IP;
    public final static int SERVER_PORT;

    static {
        String ip = "localhost";
        int port = 8025;

        SERVER_IP = ip;
        SERVER_PORT = port;
        System.out.println("Server IP:" + SERVER_IP + " Port: " + SERVER_PORT);
    }


    @OnOpen
    public void onOpen(final Session session, EndpointConfig endpointConfig) {
        System.out.println("new Client connected in session "+session.getId());
        sessions.add(session);
    }

    @OnMessage
    public void echo(String bean, Session peer) throws IOException, EncodeException {
        System.out.println("Received: "+bean);
        for (Session session : sessions)
            session.getBasicRemote().sendObject(bean);
    }

    @OnClose
    public void onClose(final Session session, EndpointConfig endpointConfig) {
        System.out.println(session.getId() + " leaved.");
        sessions.remove(session);
    }

    public static void main(String[] args) {
        System.out.println("Server starting...");
        org.glassfish.tyrus.server.Server server =
                new org.glassfish.tyrus.server.Server(SERVER_IP, SERVER_PORT, "/", null, Server.class);

        try {
            server.start();
            //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Please press a key to stop the server QUICKLY.");
            //reader.readLine();
            while(true);
        } catch (Exception e) {
            e.printStackTrace();
        } //finally {
            //server.stop();
        //}
    }
}