package com.parser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class Parser implements Runnable {
    public String result = "";

    @Override
    public void run() {
        System.out.println("Thread started");
        try {
            URL url = new URL("http://project-admin.local/en/api/json-rpc");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            String params = "{\"jsonrpc\": \"2.0\",\"id\" : 1,\"params\" : {\"projectId\": 6,\"date\": \"2018-10-02\"},\"method\" : \"mg.findNewPlayersAt\"}";
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(params);
            wr.flush();
            wr.close();

            int status = connection.getResponseCode();
            System.out.println("Status: " + status);
            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            String responseString = "";
            while ((line = responseBuffer.readLine()) != null) {
                responseString += line;
            }
            responseBuffer.close();

            connection.disconnect();

            JSONObject json = new JSONObject(responseString);
            this.result = json.getString("jsonrpc");
        } catch (IOException exception) {
            this.result = exception.getMessage();
        }
    }
}
