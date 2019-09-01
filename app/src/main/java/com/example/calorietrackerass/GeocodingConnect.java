package com.example.calorietrackerass;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import SQlite.Credential;
import SQlite.User;

public class GeocodingConnect {

    public GeocodingConnect()
    {

    }

    private static String findByAddress(String address) {
        String prasedAddress = address.replace(" ","+");
        String methodPath = "https://maps.googleapis.com/maps/api/geocode/json?address="+address+"&key=AIzaSyCneRdZHhHFeBYXeAMACvlt-J3KMGCgC_A";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
//Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
//read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error!";
        } finally {
            conn.disconnect();
        }
        return textResult;
    }
}