package it.univaq.disim.mwt;

import it.univaq.disim.mwt.model.AnnoAccademicoList;

import java.util.HashMap;
import java.util.Map;

// TODO: hardcoded parameters
public class Esse3Interface {

    public static AnnoAccademicoList annoAccademicoCorrente() {
        Map<String, String> inputParameters = new HashMap<String, String>();

        // OP: ANNO_ACCADEMICO_CORRENTE
        String opcode = "GET_CURR_AA";
        inputParameters.put("tipo_data_rif_cod", "DR_CALESA");

        String response = Esse3SoapRequest.request(opcode, inputParameters);
        String xmlString = XmlDealer.getContent(response);

        return JAXBDealer.<AnnoAccademicoList>unMarshall(AnnoAccademicoList.class, xmlString);
    }

    public static AnnoAccademicoList elencoAnniAccademici() {
        Map<String, String> inputParameters = new HashMap<String, String>();

        // OP: ELENCO_ANNI_ACCADEMICI
        String opcode = "AA_LOOKUP";
        inputParameters.put("aa_ini_id", "2005");

        String response = Esse3SoapRequest.request(opcode, inputParameters);
        String xmlString = XmlDealer.getContent(response);

        return JAXBDealer.<AnnoAccademicoList>unMarshall(AnnoAccademicoList.class, xmlString);
    }

}
