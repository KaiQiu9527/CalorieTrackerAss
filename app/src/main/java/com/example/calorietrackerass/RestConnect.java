package com.example.calorietrackerass;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import SQlite.Credential;
import SQlite.Food;
import SQlite.User;

public class RestConnect {
    private static final String BASE_URL =
            "http://118.138.93.99:8080/CalorieTracker/webresources/";

    public static String findAllUserId() {
        final String methodPath = "calorie.users/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
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
        String[] users = textResult.split("}");
        return textResult;
    }


    public static String findUserByName(String username) {
        final String methodPath = "calorie.credential/findByName/" + username;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
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

    public static String findUserByEmail(String email) {
        final String methodPath = "calorie.users/findByEmail/" + email;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
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

    public static String findUserByNameAndHash(String username, String hash) {
        final String methodPath = "calorie.credential/findByNameAndHash/" + username + "/" + hash;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
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
        } catch (SocketTimeoutException ste) {
            return "timeout";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static int findLargestID(String path) {
        final String methodPath = "calorie.";
        int largestID = 0;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath + path + "/findLargestID/");
////open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "text/plain");
//Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
//read the input steream and store it as string
            //Send the POST out
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        largestID = Integer.parseInt(textResult);
        return largestID;
    }

    public static void postNewUser(User user) {
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "calorie.users/";
//Making HTTP request
        try {
            //Gson gson = new Gson();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            String stringUserJson = gson.toJson(user);
            url = new URL(BASE_URL + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(100000);
            conn.setConnectTimeout(150000);
//set the connection method to GET
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
//set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringUserJson.getBytes().length);
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringUserJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void postNewCredential(Credential credential) {
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "calorie.credential/";
//Making HTTP request
        try {
            //Gson gson = new Gson();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            String stringCredentialJson = gson.toJson(credential);
            url = new URL(BASE_URL + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(100000);
            conn.setConnectTimeout(150000);
//set the connection method to GET
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
//set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCredentialJson.getBytes().length);
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringCredentialJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static String findFoodByCategory(String category) {
        final String methodPath = "calorie.food/findByCategory/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath + category);
////open the connection
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
            //Send the POST out
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static void postNewFood(Food food) {
        int id = findLargestID("food") + 1;
        food.setId(id);
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "calorie.food/";
//Making HTTP request
        try {
            //Gson gson = new Gson();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            String stringFoodJson = gson.toJson(food);
            url = new URL(BASE_URL + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(100000);
            conn.setConnectTimeout(150000);
//set the connection method to GET
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
//set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringFoodJson.getBytes().length);
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringFoodJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static User findUserByUsername(String name) {
        final String methodPath = "calorie.credential/findUserByName/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        User userid;
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath + name);
////open the connection
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
            //Send the POST out
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        try {
            JSONArray credentialarray = new JSONArray(textResult);
            JSONObject credential = credentialarray.getJSONObject(0);
            String useridstring = credential.get("userid").toString();
            JsonObject useridjson = new JsonParser().parse(useridstring).getAsJsonObject();
            Gson g = new Gson();
            userid = g.fromJson(useridjson, User.class);
        } catch (Exception e) {
            System.out.println(e);
            userid = null;
        }
        return userid;
    }

    public static Food findFoodByFoodname(String name) {
        final String methodPath = "calorie.food/findByName/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        Food foodid;
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath + name);
////open the connection
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
            //Send the POST out
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        try {
            JSONArray foodarray = new JSONArray(textResult);
            JSONObject food = foodarray.getJSONObject(0);
            String foodidstring = food.toString();
            JsonObject foodidjson = new JsonParser().parse(foodidstring).getAsJsonObject();
            Gson g = new Gson();
            foodid = g.fromJson(foodidjson, Food.class);
        } catch (Exception e) {
            System.out.println(e);
            foodid = null;
        }
        return foodid;
    }

    public static String findAddressByUsername(String name) {
        final String methodPath = "calorie.credential/findByName/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        String address;
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath + name);
////open the connection
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
            //Send the POST out
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        try {
            JSONArray credentialarray = new JSONArray(textResult);
            JSONObject credential = credentialarray.getJSONObject(0);
            JSONObject userid = credential.getJSONObject("userid");
            address = userid.getString("address");
        } catch (Exception e) {
            System.out.println(e);
            address = null;
        }
        return address;
    }


    public static void postNewConsumption(String foodname, String username) {
        int consumptionid = findLargestID("consumption") + 1;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "calorie.consumption/";
//Making HTTP request
        try {
            //Gson gson = new Gson();
            User userid = findUserByUsername(username);
            Food foodid = findFoodByFoodname(foodname);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date now = Calendar.getInstance().getTime();
            Consumption consumption = new Consumption(userid, foodid, 1, now);
            consumption.setId(consumptionid);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            String stringConsumptionJson = gson.toJson(consumption);
            url = new URL(BASE_URL + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(100000);
            conn.setConnectTimeout(150000);
//set the connection method to GET
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
//set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringConsumptionJson.getBytes().length);
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringConsumptionJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static String findByAddress(String address) {
        String prasedAddress = address.replace(" ", "+").replace("/", "+");
        String methodPath = "https://maps.googleapis.com/maps/api/geocode/json?address=" + prasedAddress + "&key=AIzaSyCneRdZHhHFeBYXeAMACvlt-J3KMGCgC_A";
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

    public static String findParkByLocation(String locationString) {
        String methodPath = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + locationString + "&radius=5000&types=park&name=cruise&key=AIzaSyCneRdZHhHFeBYXeAMACvlt-J3KMGCgC_A";
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

    public static Integer findUserIDByUsername(String username) {
        final String methodPath = "calorie.credential/findUseridByUsername/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        String address;
        int userid;
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath + username);
////open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "text/plain");
//Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
//read the input steream and store it as string
            //Send the POST out
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            userid = Integer.parseInt(textResult);
        } catch (Exception e) {
            e.printStackTrace();
            userid = 0;
        } finally {
            conn.disconnect();
        }
        return userid;
    }

    public static double findConsumption(int userid, String date) {
        final String methodPath = "calorie.users/totalCalConsumeThatDay/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        double calconsumed = 0;
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath + userid + "/" + date);
////open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "text/plain");
//Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
//read the input steream and store it as string
            //Send the POST out
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
                calconsumed = Double.parseDouble(textResult);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return calconsumed;
    }

    public static double findbmr(int userid) {
        final String methodPath = "calorie.users/bmr/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        double bmr = 0;
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath + userid);
////open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "text/plain");
//Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
//read the input steream and store it as string
            //Send the POST out
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
                bmr = Double.parseDouble(textResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return bmr;
    }

    public static double findcalperstep(int userid) {
        final String methodPath = "calorie.users/calperstep/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        double calperstep = 0;
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath + userid);
////open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "text/plain");
//Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
//read the input steream and store it as string
            //Send the POST out
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            calperstep = Double.parseDouble(textResult);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return calperstep;
    }

    public static void postReport(Report report) {
        int reportid = findLargestID("report") + 1;
        report.setId(reportid);
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "calorie.report/";
//Making HTTP request
        try {
            //Gson gson = new Gson();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            String reportstring = gson.toJson(report);
            url = new URL(BASE_URL + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(100000);
            conn.setConnectTimeout(150000);
//set the connection method to GET
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
//set length of the data you want to send
            conn.setFixedLengthStreamingMode(reportstring.getBytes().length);
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(reportstring);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static String findUserById(int userid) {
        final String methodPath = "calorie.users/" + userid;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
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

    public static String findReportByUser(int userid) {
        final String methodPath = "calorie.report/findByUserid/" + userid;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
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

    public static String findReportByUseridAndDate(int id, String date) {
        final String methodPath = "calorie.report/calReportSomeDays/" + id + "/" + date + "/" + date;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
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

    public static String findReportByUseridBetweenDate(int id, String from, String to) {
        final String methodPath = "calorie.report/calReportSomeDays/" + id + "/" + from + "/" + to;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
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