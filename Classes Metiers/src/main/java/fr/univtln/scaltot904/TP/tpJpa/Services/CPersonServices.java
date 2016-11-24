package fr.univtln.scaltot904.TP.tpJpa.Services;

import fr.univtln.scaltot904.TP.tpJpa.Classes.CPerson;
import fr.univtln.scaltot904.TP.tpJpa.DAO.CCrudServiceBean;

import javax.ws.rs.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Screetts on 07/03/2016.
 */

@Path("/person")
@Produces({"application/json", "application/xml"})
public class CPersonServices {

   static  CCrudServiceBean<CPerson> crudPerson = new CCrudServiceBean<CPerson>();

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public static CPerson getPerson(@PathParam("id") final int id) {
        return crudPerson.find(CPerson.class, id);
    }

    @GET
    @Produces("application/json")
    @Path("/persons")
    public List<CPerson> getPersonAll(){
        return (List<CPerson>)crudPerson.findWithNamedQuery("CPerson.findPersonAll");
    }

    @PUT
    @Path("/put")
    public void putPerson(CPerson cPerson) {
        crudPerson.update(cPerson);;
    }

    @POST
    @Produces("application/json")
    @Path("/post")
    public void Person(CPerson cPerson ) {
        crudPerson.create(cPerson);
    }

    @DELETE
    @Path("/{id}")
    public void deletePerson(@PathParam("id") final int id ) {
        crudPerson.delete(CPerson.class, id);
    }


}

