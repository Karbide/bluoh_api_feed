package bluoh.feed.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ashutosh on 11-12-2016.
 */
@Service
public class Connection {

    private final String USER_AGENT = "Mozilla/5.0";

    // HTTP GET request
    public String sendGet(String url) throws Exception {
        HttpURLConnection con = getHttpURLConnection(url,"GET");
        StringBuilder response = getResponse(con);
        return response.toString();
    }

    // HTTP POST request
    public String sendPost(String url,String parameters) throws Exception {
        HttpURLConnection con = getHttpURLConnection(url,"POST");
        setPostBody(url,con,parameters);
        StringBuilder response = getResponse(con);
        return response.toString();
    }

    public String sendPostWithBody(String url,String parameters) throws Exception {
        HttpURLConnection con = getHttpURLConnection(url,"POST");
        setPostBody(url,con,parameters);
        StringBuilder response = getResponse(con);
        return response.toString();
    }

    // HTTP PUT request
    public String sendPutWithBody(String url,String parameters) throws Exception {
        HttpURLConnection con = getHttpURLConnection(url,"PUT");
        setPostBody(url,con,parameters);
        StringBuilder response = getResponse(con);
        return response.toString();
    }

    private StringBuilder getResponse(HttpURLConnection con) throws IOException {
        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);
        BufferedReader in;
        if(responseCode<=399){
            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
        }else {
            in = new BufferedReader(
                    new InputStreamReader(con.getErrorStream()));
        }
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response;
    }

    private HttpURLConnection getHttpURLConnection(String url, String method) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod(method);
        con.setRequestProperty("User-Agent", USER_AGENT);
        String encoded = Base64.encode("rebel.again1@gmail.com:1258824304181317".getBytes());
        con.setRequestProperty("Authorization", "Basic "+encoded);
        return con;
    }

    private void setPostBody(String url, HttpURLConnection con, String param) throws IOException {
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.addRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(param);
        wr.flush();
        wr.close();
        System.out.println("\nSending 'POST' request to URL : " + url+"?"+param);
    }
}