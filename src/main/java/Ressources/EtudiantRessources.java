package Ressources;

import entities.Etudiant;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/etudiants")
public class EtudiantRessources {
    EtudiantBusiness etudiantBusiness = new EtudiantBusiness();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Etudiant> getList () {
        return etudiantBusiness.getAllEtudiants();
    }

    @GET
    @Path("/{identifiant}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantById(@PathParam("identifiant") String identifiant)
    {
        Etudiant etudiant = etudiantBusiness.getEtudiantByIdentifiant(identifiant);
        if (etudiant != null) {
            return Response.status(Response.Status.OK).entity(etudiant).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("etudiant n'existe pas").build();
    }

    @GET
    @Path("/option")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantParParOptions(@QueryParam("code") int codeOption)
    {
        Option option = new OptionBusiness().getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("option n'existe pas").type(MediaType.TEXT_PLAIN).build();
        }
        else {
            List<Etudiant> etudiantList = etudiantBusiness.getEtudiantsByOption(option);
            if (!etudiantList.isEmpty()) {
                return Response.status(Response.Status.OK).entity(etudiantList).build();}
            return Response.status(Response.Status.NOT_FOUND).entity("aucun etudiant").build();
        }
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ajoutEtudiant(Etudiant etudiant) {
        if(etudiantBusiness.addEtudiant(etudiant))
            return Response.status(Response.Status.CREATED).entity("added sucssfuly").build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("/{identifiant}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response supprimerEtudiant(@PathParam("identifiant") String identifiant) {
        if (etudiantBusiness.deleteEtudiant(identifiant)) {
            return Response.ok("etudiant supprimée").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("etudiant introuvable").build();
    }





}
