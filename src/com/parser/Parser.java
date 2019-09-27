package com.parser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class Parser implements Runnable {
    private String domain;
    private int procId;
    private String result = "";
    private String task = "";

    @Override
    public void run() {
        if (this.isEmptyTask()) {
            return;
        }

        System.out.println("Thread started #" + this.procId);
        try {
            URL url = new URL(this.domain + this.task);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            String params = "{\"jsonrpc\": \"2.0\",\"id\" : 1,\"params\" : {\"projectId\": 6,\"date\": \"2018-10-02\"},\"method\" : \"mg.findNewPlayersAt\"}";
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(params);
            wr.flush();
            wr.close();

            int status = connection.getResponseCode();
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

        this.task = "";
    }

    public boolean isEmpty() {
        return this.result.equals("") && this.task.equals("");
    }

    public boolean isEmptyTask() {
        return this.task.equals("");
    }

    public Parser setDomain(String domain) {
        this.domain = domain;

        return this;
    }

    public Parser setProcId(int procId) {
        this.procId = procId;

        return this;
    }

    public int getProcId()
    {
        return this.procId;
    }

    public String getResult() {
        String result = this.result;
        this.result = "";

        return result;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
