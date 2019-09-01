package com.example.calorietrackerass;


//import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class DisplayMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;


    public DisplayMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        //要用getChildFragmentManager，不用getactivity
        final SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        return v;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        if (getActivity() != null) {
//                SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
//                        .findFragmentById(R.id.map);
//                if (mapFragment != null) {
//                    mapFragment.getMapAsync(this);
//                }
//            }
//        }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        Toast.makeText(getActivity(),"here",Toast.LENGTH_LONG).show();
//        mMap = googleMap;
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        final SharedPreferences userinfo = getActivity().getSharedPreferences("userinfo",0);
        String address = userinfo.getString("address","");
        ArrayList<String> location = new ArrayList<>();
        AddMarkerAsyncTask add = new AddMarkerAsyncTask();
        add.execute(address);
        mMap = googleMap;

    }

    private class AddMarkerAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String...param) {
            return RestConnect.findByAddress(param[0]);
        }

        @Override
        protected void onPostExecute(String result)
        {
            SharedPreferences userinfo = getActivity().getSharedPreferences("userinfo",0);
            String address = userinfo.getString("address","unknown");
            String latitude = "";
            String longitute = "";
            String locationString = "";
            try {
                JSONObject geo = new JSONObject(result);
                String resultArray = geo.getString("results");
                JSONObject resultobject = new JSONArray(resultArray).getJSONObject(0);
                JSONObject locationobject = resultobject.getJSONObject("geometry").getJSONObject("location");
                latitude = locationobject.getString("lat");
                longitute = locationobject.getString("lng");
                locationString = latitude+","+longitute;
            } catch (JSONException e) {
                e.printStackTrace();
            }
// Add a marker in Sydney and move the camera
            LatLng home = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitute));
            mMap.addMarker(new MarkerOptions().position(home).title(address).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))).showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(home));
            mMap.animateCamera((CameraUpdateFactory.newLatLngZoom(home, 15)));
            AddParkAsyncTask park = new AddParkAsyncTask();
            park.execute(locationString);
        }
    }

    private class AddParkAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String...param) {
            return RestConnect.findParkByLocation(param[0]);
        }

        @Override
        protected void onPostExecute(String result)
        {
            SharedPreferences userinfo = getActivity().getSharedPreferences("userinfo",0);
            String address = userinfo.getString("address","unknown");
            String latitude = "";
            String longitute = "";
            String name = "";
            try {
                JSONObject geo = new JSONObject(result);
                String resultArray = geo.getString("results");
                int resultlength = resultArray.length();
                for(int i = 0; i < resultlength; i++)
                {
                    JSONObject resultobject = new JSONArray(resultArray).getJSONObject(i);
                    name = resultobject.getString("name");
                    JSONObject locationobject = resultobject.getJSONObject("geometry").getJSONObject("location");
                    latitude = locationobject.getString("lat");
                    longitute = locationobject.getString("lng");
                    LatLng park = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitute));
                    //add park marker
                    mMap.addMarker(new MarkerOptions().position(park).title(name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))).showInfoWindow();
                    mMap.animateCamera((CameraUpdateFactory.newLatLngZoom(park, 15)));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



}

