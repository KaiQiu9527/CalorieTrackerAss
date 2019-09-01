package com.example.calorietrackerass;

import android.arch.persistence.room.SharedSQLiteStatement;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import SQlite.Credential;
import SQlite.Food;

public class DisplayDietFragment extends Fragment {
    View vDisplayDiet;
    List<HashMap<String, String>> categoryListArray;
    List<HashMap<String, String>> itemListArray;
    SimpleAdapter categoryListAdapter;
    SimpleAdapter itemListAdapter;
    ListView categoryList;
    ListView itemList;
    HashMap<String, String> categoryMap;
    HashMap<String, String> itemMap;
    String[] categoryHEAD = new String[]{"category"};
    String[] itemHEAD = new String[]{"item"};
    TextView foodcategory;
    EditText addfood;
    Button searchfood;
    Button submitfood;
    TextView result;
    EdamamConnect edamam = new EdamamConnect();
    ImageView foodimage;

    int[] dataCategory = new int[]{R.id.category};
    int[] dataItem = new int[]{R.id.item};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle
            savedInstanceState) {
        final SharedPreferences userinfo = getActivity().getSharedPreferences("userinfo",0);
        SharedPreferences.Editor editor = userinfo.edit();
        vDisplayDiet = inflater.inflate(R.layout.fragment_diet, container, false);
        addfood = vDisplayDiet.findViewById(R.id.et_addfood);
        searchfood = vDisplayDiet.findViewById(R.id.foodsearch);
        submitfood = vDisplayDiet.findViewById(R.id.foodsubmit);
        result = vDisplayDiet.findViewById(R.id.search);
        foodimage = vDisplayDiet.findViewById(R.id.foodimage);
        foodcategory = vDisplayDiet.findViewById(R.id.tv_addfood);
        //catagory list set
        categoryList = vDisplayDiet.findViewById(R.id.list_view1);
        itemList = vDisplayDiet.findViewById(R.id.list_view2);
        categoryListArray = new ArrayList<HashMap<String, String>>();
        itemListArray = new ArrayList<HashMap<String, String>>();
        //add meat
        categoryMap = new HashMap<String, String>();
        categoryMap.put("category", "meat");
        categoryListArray.add(categoryMap);
        //add drink
        categoryMap = new HashMap<String, String>();
        categoryMap.put("category", "drink");
        categoryListArray.add(categoryMap);
        //add grains
        categoryMap = new HashMap<String, String>();
        categoryMap.put("category", "grains");
        categoryListArray.add(categoryMap);
        //add snack
        categoryMap = new HashMap<String, String>();
        categoryMap.put("category", "snack");
        categoryListArray.add(categoryMap);
        //add bread
        categoryMap = new HashMap<String, String>();
        categoryMap.put("category", "bread");
        categoryListArray.add(categoryMap);
        //add cake
        categoryMap = new HashMap<String, String>();
        categoryMap.put("category", "cake");
        categoryListArray.add(categoryMap);
        //add fruit
        categoryMap = new HashMap<String, String>();
        categoryMap.put("category", "fruit");
        categoryListArray.add(categoryMap);
        //add vegetables
        categoryMap = new HashMap<String, String>();
        categoryMap.put("category", "vegetables");
        categoryListArray.add(categoryMap);
        //add others
        categoryMap = new HashMap<String, String>();
        categoryMap.put("category", "others");
        categoryListArray.add(categoryMap);

        categoryListAdapter = new SimpleAdapter(getActivity(), categoryListArray, R.layout.list_view1, categoryHEAD, dataCategory);
        categoryList.setAdapter(categoryListAdapter);

        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pos = position;
                HashMap<String, String> map = (HashMap<String, String>) categoryList.getItemAtPosition(pos);
                String category = map.get("category");
                foodcategory.setText(category);
                GetFoodAsyncTask getFood = new GetFoodAsyncTask();
                getFood.execute(category);
            }
        });
        //post consumption
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pos = position;
                HashMap<String, String> map = (HashMap<String, String>) itemList.getItemAtPosition(pos);
                String item = map.get("item");
                String username = userinfo.getString("username","Unknown");
                PostConsumptionAsyncTask postconsumption = new PostConsumptionAsyncTask();
                postconsumption.execute(item,username);
            }
        });

        searchfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodname = addfood.getText().toString();
                EdamamAsyncTask eda = new EdamamAsyncTask();
                eda.execute(foodname);
                googleAsyncTask google = new googleAsyncTask();
                google.execute(foodname + " food");
            }
        });
        //post food to Food table
        submitfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category;
                String name;
                Double calamount;
                Double fat;
                try{
                    category = foodcategory.getText().toString();
                    name = addfood.getText().toString();
                    calamount = Double.parseDouble(userinfo.getString("foodCal","0"));
                    fat = Double.parseDouble(userinfo.getString("foodFat","0"));
                }catch (Exception e){
                    Toast.makeText(getActivity(),"Miss something!!",Toast.LENGTH_LONG).show();
                    return;
                }
                Food food = new Food(category,name,calamount,fat);
                PostFoodAsyncTask postfood = new PostFoodAsyncTask();
                postfood.execute(food);
            }
        });


        return vDisplayDiet;
    }

    private class GetFoodAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... param) {
            return RestConnect.findFoodByCategory(param[0]);
        }

        @Override
        protected void onPostExecute(String food) {
            String foodreturn = food;
            JSONArray foodarray = new JSONArray();
            itemList = vDisplayDiet.findViewById(R.id.list_view2);
            itemListArray = new ArrayList<HashMap<String, String>>();
            try {
                foodarray = new JSONArray(foodreturn);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (foodarray.length() > 0) {
                for (int i = 0; i < foodarray.length(); i++) {
                    try {
                        JSONObject foodobject = foodarray.getJSONObject(i);
                        itemMap = new HashMap<String, String>();
                        itemMap.put("item", foodobject.getString("name"));
                        itemListArray.add(itemMap);
                    } catch (JSONException e) {
                        break;
                    }
                    itemListAdapter = new SimpleAdapter(getActivity(), itemListArray, R.layout.list_view2, itemHEAD, dataItem);
                    itemList.setAdapter(itemListAdapter);
                }
            } else {
                itemMap = new HashMap<String, String>();
                itemMap.put("item", "");
                itemListArray.add(itemMap);
                itemListAdapter = new SimpleAdapter(getActivity(), itemListArray, R.layout.list_view2, itemHEAD, dataItem);
                itemList.setAdapter(itemListAdapter);
            }
            //add meat


            //addfood.setText(gson.getAdapter().);
        }
    }

    private class EdamamAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... param) {
            String methodPath = "https://api.edamam.com/api/food-database/parser?ingr=" + param[0] + "&app_id=741bceec&app_key=8b8402fe7277e52dbbb84c564c6f9d55";
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


        @Override
        protected void onPostExecute(String food) {
            String foodreturn = food;
            JSONArray foodarray = new JSONArray();
            JSONObject foodparsed = new JSONObject();
            String foodinfo = "";
            String kcal = "";
            String foodname = "";
            String fat = "";
            SharedPreferences userinfo = getActivity().getSharedPreferences("userinfo",0);
            SharedPreferences.Editor editor = userinfo.edit();

            try {
                foodparsed = new JSONObject(food);
                foodinfo = foodparsed.getString("parsed");
                foodarray = new JSONArray(foodinfo);
                foodparsed = foodarray.getJSONObject(0);
                foodparsed = foodparsed.getJSONObject("food");
                String imageurl = foodparsed.getString("image");
                ImagesyncTask image = new ImagesyncTask();
                image.execute(imageurl);
                //result.setText(foodparsed.toString());
                foodname = foodparsed.getString("label");
                editor.putString("foodname",foodname);
                foodparsed = foodparsed.getJSONObject("nutrients");
                kcal = foodparsed.getString("ENERC_KCAL");
                fat = foodparsed.getString("FAT");
                editor.putString("foodCal",kcal);
                editor.putString("foodFat",fat);
                editor.commit();
                addfood.setText(userinfo.getString("foodname","Unknown!"));
            } catch (JSONException e) {
                e.printStackTrace();
                addfood.setText("Can't find it!");
            }

        }
    }

    private class ImagesyncTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... param) {
            URL url = null;
            HttpURLConnection conn = null;
            String textResult = "";
            InputStream is;
            Bitmap bitmap = null;
//Making HTTP request
            try {
                url = new URL(param[0]);
//open the connection
                conn = (HttpURLConnection) url.openConnection();
//set the timeout
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setUseCaches(true);
//set the connection method to GET
                conn.setRequestMethod("GET");
//add http headers to set your response type to json
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
//Read the response
                is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
                return bitmap;
            }
        }


            @Override
            protected void onPostExecute (Bitmap bitmap){
                Drawable pic = new BitmapDrawable(bitmap);
                foodimage.setImageDrawable(pic);
            }
        }

    private class googleAsyncTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            String API_KEY = "AIzaSyDOXLpxNWweK6skr8otP036yUfz32P4Hiw";
            String SEARCH_ID_cx = "011607959216105655751:uzxwwxikydc";
            String keyword = strings[0];
            String[] params = new String[]{"num"};
            String[] values = new String[]{"1"};
            keyword = keyword.replace(" ", "+");
            URL url = null;
            HttpURLConnection connection = null;

            String textResult = "";
            String query_parameter="";
            if (params!=null && values!=null){
                for (int i =0; i < params.length; i ++){
                    query_parameter += "&";
                    query_parameter += params[i];
                    query_parameter += "=";
                    query_parameter += values[i];
                }
            }
            try {
                url = new URL("https://www.googleapis.com/customsearch/v1?key="+
                        API_KEY+ "&cx="+ SEARCH_ID_cx + "&q="+ keyword + query_parameter);
                connection = (HttpURLConnection)url.openConnection();
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                Scanner scanner = new Scanner(connection.getInputStream());
                while (scanner.hasNextLine()) {
                    textResult += scanner.nextLine();
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                connection.disconnect();
            }
            return getSnippet(textResult);
            //return textResult;
            }

        @Override
        protected void onPostExecute (String textResult){
            result.setText(textResult);
        }

            }

    public String getSnippet(String result){
        String snippet = null;
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if(jsonArray != null && jsonArray.length() > 0) {
                snippet =jsonArray.getJSONObject(0).getString("snippet");
            }
        }catch (Exception e){
            e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        return snippet;
    }

    private class PostFoodAsyncTask extends AsyncTask<Food, Void, String> {
        @Override
        protected String doInBackground(Food... food) {
            RestConnect.postNewFood(food[0]);
            return "food success!";
        }
        @Override
        protected void onPostExecute(String response) {
            Toast.makeText(getActivity(),"Post to database successfully!",Toast.LENGTH_LONG).show();
        }
    }

    private class PostConsumptionAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... param) {
            //foodname,username
            RestConnect.postNewConsumption(param[0],param[1]);
            return "credential success!";
        }
        @Override
        protected void onPostExecute(String response) {
            Toast.makeText(getActivity(),"Post consumption to database successfully!",Toast.LENGTH_LONG).show();
        }
    }

    }

