package fr.univtln.scaltot904.TP.tpJpa.Services;

import fr.univtln.scaltot904.TP.tpJpa.Classes.CTeam;
import fr.univtln.scaltot904.TP.tpJpa.DAO.CCrudServiceBean;
import fr.univtln.scaltot904.TP.tpJpa.DAO.QueryParameter;

import javax.ws.rs.*;
import java.util.List;


/**
 * Created by Screetts on 07/03/2016.
 */

@Path("/team")
@Produces("application/json")
public class CTeamServices {

    static CCrudServiceBean<CTeam> crudTeam = new CCrudServiceBean<CTeam>();

    @GET
    @Produces("application/json")
    @Path("/{nom}")
    public CTeam getTeam(@PathParam("nom") final String nom){
        return (CTeam) crudTeam.findWithNamedQuery(CTeam.TEAM_BY_NAME, QueryParameter.with("Pnom",nom).parameters()).get(0);
    }

    @GET
    @Produces("application/json")
    @Path("/teams")
    public List<CTeam> getTeamAll() {
        return (List<CTeam>)crudTeam.findWithNamedQuery("CTeam.findTeamAll");
    }


    @GET
    @Produces("application/json")
    @Path("/teams/{nom_competition}")
    public List<CTeam> getTeamByComp(@PathParam("nom_competition") final String nom_competition) {
        return (List<CTeam>)crudTeam.findWithNamedQuery(CTeam.TEAM_BY_COMP, QueryParameter.with("Pnom_competition",nom_competition).parameters());
    }

    @PUT
    @Produces("application/json")
    public void putTeam(CTeam cTeam) {
        crudTeam.update(cTeam);
    }


    @POST
    @Produces("application/json")
    public void postTeam(CTeam cTeam ) {
        crudTeam.create(cTeam);
    }

    @DELETE
    @Path("/{id}")
    public void deleteTeam(@PathParam("id") final int id ) {
        crudTeam.delete(CTeam.class, id);
    }
}