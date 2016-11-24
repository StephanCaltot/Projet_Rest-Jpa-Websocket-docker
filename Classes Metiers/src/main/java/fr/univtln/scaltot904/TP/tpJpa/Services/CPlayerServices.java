package fr.univtln.scaltot904.TP.tpJpa.Services;


import fr.univtln.scaltot904.TP.tpJpa.Classes.CPlayer;
import fr.univtln.scaltot904.TP.tpJpa.DAO.CCrudServiceBean;
import fr.univtln.scaltot904.TP.tpJpa.DAO.QueryParameter;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by Screetts on 07/03/2016.
 */

@Path("/player")
@Produces("application/json")
public class CPlayerServices {

    static CCrudServiceBean<CPlayer> crudPlayer = new CCrudServiceBean<CPlayer>();

    @GET
    @Produces("application/json")
    @Path("/{nom}")
    public CPlayer getPlayer(@PathParam("nom") final String nom) {
        return (CPlayer) crudPlayer.findWithNamedQuery(CPlayer.PLAYER_BY_NOM, QueryParameter.with("Pnom",nom).parameters()).get(0);
    }

    @GET
    @Produces("application/json")
    @Path("/players")
    public List<CPlayer> getPersonAll() {
        return (List<CPlayer>)crudPlayer.findWithNamedQuery("CPlayer.findPlayerAll");
    }

    @GET
    @Produces("application/json")
    @Path("/players/{team}")
    public List<CPlayer> getPersonByTeam(@PathParam("team") final String team) {
        return (List<CPlayer>)crudPlayer.findWithNamedQuery(CPlayer.PLAYER_BY_TEAM, QueryParameter.with("Pnom",team).parameters());
    }

    @PUT
    public void putPlayer(CPlayer player) {
        crudPlayer.update(player);
    }


    @POST
    @Produces("application/json")
    public void postPlayer(CPlayer cPlayer) {
        crudPlayer.create(cPlayer);
    }


    @DELETE
    @Path("/{id}")
    public void deletePlayer(@PathParam("id") final int id ) {
        crudPlayer.delete(CPlayer.class, id);
    }
}