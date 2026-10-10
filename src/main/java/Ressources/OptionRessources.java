package Ressources;


import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/options")
public class OptionRessources {
    OptionBusiness optionBusiness = new OptionBusiness();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Option> getList () {
        return optionBusiness.getListeOptions();

    }
    @GET
    @Path("/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptionById(@PathParam("code") int code){ //<ce id tu doit recuperer from  path
        Option opt = optionBusiness.getOptionByCode(code);
        if (opt != null){
            return Response.status(Response.Status.OK).entity(opt).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/semestre")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBySemestre(@QueryParam("s") int semestre) {
        List<Option> opt = optionBusiness.getOptionsBySemestre(semestre);
        if (opt != null && !opt.isEmpty()) {
            return Response.ok(opt).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("aucun semestre").type(MediaType.TEXT_PLAIN).build();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ajoutOption(Option opt) {
        if(optionBusiness.addOption(opt))
            return Response.status(Response.Status.CREATED).entity("added sucssfuly").build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response modifOption(@PathParam("code") int code, Option opt) {
        if (optionBusiness.updateOption(code, opt)) {
            return Response.ok("Option modifiée avec succès").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @DELETE
    @Path("/{code}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response supprimerOption(@PathParam("code") int code) {
        if (optionBusiness.deleteOption(code)) {
            return Response.ok("Option supprimée").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Option introuvable").build();
    }

}
