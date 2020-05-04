package it.univaq.disim.mwt.resources;

import it.univaq.disim.mwt.AppException;
import it.univaq.disim.mwt.Esse3Interface;
import it.univaq.disim.mwt.JSONDealer;
import it.univaq.disim.mwt.RestWebApplicationException;
import it.univaq.disim.mwt.model.lists.TipoDiAttivitaFormaticaRegolamentoDiPercorsoList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("regole_di_percorso_tipi_di_attivita_formative")
public class RegoleDiPercorsoTipiDiAttivitaFormativeRes {

    @GET
    @Path("{cds_id: ([0-9]+)}/{aa_ord_id: ([0-9]+)}/{pds_id: ([0-9]+)}/{prof_cod: ([0-9]+)}/{aa_reg_id: ([0-9]+)}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRegoleDiPercorsoTipiDiAttivitaFormativeRes(@PathParam("cds_id") int cds_id, @PathParam("aa_ord_id") int aa_ord_id, @PathParam("pds_id") int pds_id, @PathParam("prof_cod") int prof_cod, @PathParam("aa_reg_id") int aa_reg_id, @QueryParam("lingua_iso6392_cod") String lingua_iso6392_cod) {
        Map<String, String> inputParameters = new HashMap<String, String>();
        inputParameters.put("cds_id", String.valueOf(cds_id));
        inputParameters.put("aa_ord_id", String.valueOf(aa_ord_id));
        inputParameters.put("pds_id", String.valueOf(pds_id));
        inputParameters.put("prof_cod", String.valueOf(prof_cod));
        inputParameters.put("aa_reg_id", String.valueOf(aa_reg_id));

        if (lingua_iso6392_cod != null) {
            inputParameters.put("lingua_iso6392_cod", lingua_iso6392_cod);
        }

        try {
            TipoDiAttivitaFormaticaRegolamentoDiPercorsoList tipoDiAttivitaFormaticaRegolamentoDiPercorsoList = Esse3Interface.regoleDiPercorsoTipiDiAttivitaFormative(inputParameters);

            return Response.ok(JSONDealer.toJSON(tipoDiAttivitaFormaticaRegolamentoDiPercorsoList)).build();
        } catch (AppException e) {
            e.getCause().printStackTrace();
            throw new RestWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getCause().getMessage());
        }
    }
}