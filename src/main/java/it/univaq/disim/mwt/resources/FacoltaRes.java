package it.univaq.disim.mwt.resources;

import it.univaq.disim.mwt.esse3.Esse3Interface;
import it.univaq.disim.mwt.exceptions.AppException;
import it.univaq.disim.mwt.exceptions.RestWebApplicationException;
import it.univaq.disim.mwt.model.lists.FacoltaList;
import it.univaq.disim.mwt.utils.JSONDealer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("facolta")
public class FacoltaRes {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFacolta(@QueryParam("fac_id") String fac_id, @QueryParam("lingua_iso6392_cod") String lingua_iso6392_cod) {
        Map<String, String> inputParameters = new HashMap<String, String>();

        if (fac_id != null) {
            inputParameters.put("fac_id", fac_id);
        }

        if (lingua_iso6392_cod != null) {
            inputParameters.put("lingua_iso6392_cod", lingua_iso6392_cod);
        }

        try {

            FacoltaList facoltaList = Esse3Interface.facolta(inputParameters);

            if (facoltaList.getFacoltaList() == null) {
                // empty response, the server has not found the requested resource
                return Response.status(Response.Status.NOT_FOUND).entity(JSONDealer.errorToJSON("The server has not found your request")).build();
            }

            return Response.ok(JSONDealer.toJSON(facoltaList)).build();

        } catch (AppException e) {
            // e.getCause().printStackTrace();
            throw new RestWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), JSONDealer.errorToJSON(e));
        }
    }
}
