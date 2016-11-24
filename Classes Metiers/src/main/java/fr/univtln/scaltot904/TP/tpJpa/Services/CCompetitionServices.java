package fr.univtln.scaltot904.TP.tpJpa.Services;

import fr.univtln.scaltot904.TP.tpJpa.Classes.CCompetition;
import fr.univtln.scaltot904.TP.tpJpa.DAO.CCrudServiceBean;
import fr.univtln.scaltot904.TP.tpJpa.DAO.QueryParameter;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by Screetts on 07/03/2016.
 */

@Path("/competition")
@Produces("application/json")
public class CCompetitionServices {

    static CCrudServiceBean<CCompetition> crudCompetition = new CCrudServiceBean<CCompetition>();

    @GET
    @Produces("application/json")
    @Path("/{nom}")
    public static CCompetition getCompetition(@PathParam("nom") final String nom) {
        return (CCompetition) crudCompetition.findWithNamedQuery(CCompetition.COMPETITION_BY_NAME, QueryParameter.with("Pnom",nom).parameters()).get(0);
    }

    @GET
    @Produces("application/json")
    @Path("/competitions")
    public List<CCompetition> getCompetitionAll() {
        return (List<CCompetition>)crudCompetition.findWithNamedQuery("CCompetition.findCompetitionAll");
    }

    @PUT
    @Produces("application/json")
    public void putCompetition(CCompetition cCompetition) {
        crudCompetition.update(cCompetition);
    }

    @POST
    @Produces("application/json")
    public void postCompetition(CCompetition cCompetition ) {
        crudCompetition.create(cCompetition);
    }


    @DELETE
    @Path("/{id}")
    public void deleteCompetition(@PathParam("id") final int id ) {
        //Map parameters = new HashMap();
        //parameters.put("Pnom", nom);
        //crudCompetition.findWithNamedQuery(CCompetition.COMPETITION_DELETE,parameters);
        crudCompetition.delete(CCompetition.class,id);
    }
}
