package it.univaq.disim.mwt.esse3;

import it.univaq.disim.mwt.exceptions.AppException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Class used to send requests to the Esse3's server
 */
public class Esse3SoapRequest {

    private final static String URL = "https://segreteriavirtuale.univaq.it/services/ESSE3WS";

    /**
     * Send request to the Esse3 server
     *
     * @param opcode          Esse3 Request Operation Code
     * @param inputParameters Esse3 Request parameters
     * @return A String that represents the response of the server
     * @throws AppException
     */
    public static String request(String opcode, Map<String, String> inputParameters) throws AppException {
        return soapRequest(URL, opcode, MapToString(inputParameters));
    }

    /**
     * Send request to the Esse3 server
     *
     * @param urlString       The URL of the server
     * @param opcode          Esse3 Request Operation Code
     * @param inputParameters Esse3 Request parameters
     * @return A String that represents the response of the server
     * @throws AppException
     */
    private static String soapRequest(String urlString, String opcode, String inputParameters) throws AppException {

        HttpURLConnection con = null;
        String result = null;

        try {

            URL url = new URL(urlString);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/xml; utf-8");
            con.setRequestProperty("SOAPAction", "");
            con.setDoOutput(true);

            String xmlRequest = "<soapenv:Envelope" + "\n" +
                    "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + "\n" +
                    "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"" + "\n" +
                    "xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"" + "\n" +
                    "xmlns:ws=\"http://ws.esse3.frk.kion.it\">" + "\n" +
                    "<soapenv:Header/>" + "\n" +
                    "<soapenv:Body>" + "\n" +
                    "<ws:fn_retrieve_xml_p" + "\n" +
                    "soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" + "\n" +
                    "<retrieve xsi:type=\"soapenc:string\"" + "\n" +
                    "xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">" + opcode + "</retrieve>" + "\n" +
                    "<params xsi:type=\"soapenc:string\"" + "\n" +
                    "xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">" + inputParameters + "</params>" + "\n" +
                    "<xml xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\"/>" + "\n" +
                    "</ws:fn_retrieve_xml_p>" + "\n" +
                    "</soapenv:Body>" + "\n" +
                    "</soapenv:Envelope>";

            OutputStream os = con.getOutputStream();
            os.write(xmlRequest.getBytes());
            os.flush();
            os.close();

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                result = response.toString();
            } else {
                throw new AppException("HTTP Error: " + responseCode);
            }

        } catch (IOException e) {
            throw new AppException(e);
        } finally {
            if (con != null) {
                con.disconnect();
            }

        }

        return result;
    }

    /**
     * This function converts the map that contains the input parameters in a String
     *
     * @param map Map that will be converted
     * @return String representation of the input parameters
     */
    private static String MapToString(Map<String, String> map) {

        if (map == null || map.size() == 0) {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.append(entry.getKey() + "=");
            builder.append(entry.getValue() + ";");
        }
        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }
}
