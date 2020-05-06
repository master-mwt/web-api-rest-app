package it.univaq.disim.mwt.resources;

import it.univaq.disim.mwt.esse3.Esse3Interface;
import it.univaq.disim.mwt.exceptions.AppException;
import it.univaq.disim.mwt.exceptions.RestWebApplicationException;
import it.univaq.disim.mwt.model.lists.OrdinamentoList;
import it.univaq.disim.mwt.utils.JSONDealer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("elenco_ordinamenti_corso_di_studio")
public class ElencoOrdinamentiCorsoDiStudioRes {

    @GET
    @Path("cds_id/{cds_id: ([0-9]+)}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getElencoOrdinamentiCorsoDiStudio(@PathParam("cds_id") String cds_id, @QueryParam("lingua_iso6392_cod") String lingua_iso6392_cod) {
        Map<String, String> inputParameters = new HashMap<String, String>();
        inputParameters.put("cds_id", cds_id);

        if (lingua_iso6392_cod != null) {
            inputParameters.put("lingua_iso6392_cod", lingua_iso6392_cod);
        }

        try {

            OrdinamentoList ordinamentoList = Esse3Interface.elencoDegliOrdinamentiDiUnCorsoDiStudio(inputParameters);

            return Response.ok(JSONDealer.toJSON(ordinamentoList)).build();

        } catch (AppException e) {
            e.getCause().printStackTrace();
            throw new RestWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getCause().getMessage());
        }
    }
}