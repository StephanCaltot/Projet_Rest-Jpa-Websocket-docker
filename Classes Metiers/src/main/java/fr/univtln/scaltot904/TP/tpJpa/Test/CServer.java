package fr.univtln.scaltot904.TP.tpJpa.Test;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import fr.univtln.scaltot904.TP.tpJpa.Classes.*;
import fr.univtln.scaltot904.TP.tpJpa.DAO.CCrudServiceBean;
import org.glassfish.grizzly.http.server.HttpServer;
import org.omg.CORBA.COMM_FAILURE;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class CServer {

    private static int getPort(int defaultPort) {
        //grab port from environment, otherwise fall back to default port 9998
        String httpPort = System.getProperty("jersey.test.port");
        if (null != httpPort) {
            try {
                return Integer.parseInt(httpPort);
            } catch (NumberFormatException e) {
            }
        }
        return defaultPort;
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://0.0.0.0/").port(getPort(9998)).build();
    }

    public static final URI BASE_URI = getBaseURI();
    
    protected static HttpServer startServer() throws IOException {
        ResourceConfig resourceConfig = new PackagesResourceConfig("fr.univtln.scaltot904.TP.tpJpa");

        System.out.println("Starting grizzly2...");
        return GrizzlyServerFactory.createHttpServer(BASE_URI, resourceConfig);
    }
    
    public static void main(String[] args) throws IOException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("testpostgresqllocal");
        EntityManager em = emf.createEntityManager();

        CCrudServiceBean<CPlayer> crud = new CCrudServiceBean<CPlayer>();
        //CCrudServiceBean<CTeam> crud2 = new CCrudServiceBean<CTeam>();

        //CPlayer player = crud.find(CPlayer.class, 144);
        //player.setTeam(crud2.find(CTeam.class,39));
        //crud2.find(CTeam.class,39).adPlayer(player);



        EntityTransaction transac = em.getTransaction();
        transac.begin();
        //em.merge(player);
        transac.commit();

        em.close();
        emf.close();

        //System.out.println(crud2.find(CTeam.class, 39).getPlayersList().toString());

        HttpServer httpServer = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop IT quickly...",
                BASE_URI));
        //System.in.read();
        //httpServer.stop();
        while(true);
    }
}
