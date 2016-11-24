package fr.univtln.scaltot904;

import javax.websocket.*;
import java.io.IOException;

/**
 * Created by scaltot904 on 31/03/16.
 */

@ClientEndpoint()
public class ClientWebSocket {
    public final static String SERVER_IP;
    public final static int SERVER_PORT;
    private String sender;

    public ClientWebSocket(String sender) {
        this.sender = sender;
    }

    static {
        String ip = "localhost";
        int port = 8025;
        SERVER_IP = ip;
        SERVER_PORT = port;
        System.out.println("Server IP:" + SERVER_IP + " Port: " + SERVER_PORT);
    }

    private Session session;

    @OnOpen
    public void onOpen(final Session session, EndpointConfig endpointConfig) throws IOException, EncodeException {
        this.session = session;
        System.out.println("I am " + session.getId());
        System.out.println("Sending Hello message to server");
        session.getBasicRemote().sendObject("Hello");
    }

    @OnMessage
    public void OnMessage(String bean) {
        System.out.println("RECU !");
        System.out.println(bean);
    }

    @OnClose
    public void OnClose(final Session session, EndpointConfig endpointConfig) {
        System.out.println("Session closed");
    }

    public void closeSession() throws IOException {
        if (session.isOpen())
            session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "OK"));
    }

    public void sendMessage(String message) {
        String bean = message;

        try {
            session.getBasicRemote().sendObject(bean);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }
}
