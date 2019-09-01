package com.example.calorietrackerass;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import SQlite.Step;
import SQlite.StepDatabase;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static String username = "";
    static Step step = new Step();
    static int caloriegoal;
    private AlarmManager alarmMgr;
    private Intent alarmIntent;
    private PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences userinfo = getSharedPreferences("userinfo", 0);
        SharedPreferences.Editor editor = userinfo.edit();
        Intent intent = getIntent();
        username = userinfo.getString("username","unknown");
        //get cal goal
        GetAddressAsyncTask getaddress = new GetAddressAsyncTask();
        getaddress.execute(username);

        //intentservice
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmIntent = new Intent(this, ScheduledIntentService.class);
        pendingIntent = PendingIntent.getService(this, 0, alarmIntent, 0);
        alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(), 60*24*60*1000, pendingIntent);

        //editor.putString("username",username);
        //editor.commit();
        //bulid the database if not existed
//        InsertDatabase addDatabase = new InsertDatabase();
//        addDatabase.execute(username);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView)
                findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //getSupportActionBar().setTitle("Navigation Drawer");
        //MainFragment.commit设置主页面fragment为franment_main.xml

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
//        TextView nav_username = findViewById(R.id.nav_username);
//        TextView nav_time = findViewById(R.id.nav_time);
//        nav_time.setText(Calendar.getInstance().getTime().toString());
//        nav_username.setText(username);
    }

    private long exitTime = 0;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if( keyCode== KeyEvent.KEYCODE_HOME){
            return true;
        } else if( keyCode== KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()- exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "Press return again to log out", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                clear(getApplication());
                finish();
                System.exit(0);

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment nextFragment = null;
        switch (id) {
            case R.id.homepage:
                nextFragment = new MainFragment();
                break;
            case R.id.diet:
                nextFragment = new DisplayDietFragment();
                break;
            case R.id.account:
                nextFragment = new DisplayAccountFragment();
                break;
            case R.id.step:
                nextFragment = new DisplayStepFragment();
                break;
            case R.id.calorie:
                nextFragment = new DisplayCalorieFragment();
                break;
            case R.id.map:
                nextFragment = new DisplayMapFragment();
                break;
            case R.id.report:
                nextFragment = new DisplayReportFragment();
                break;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,
                nextFragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static String getUsername() {
        return username;
    }



    public static void setCaloriegoal(int calorie)
    {
        caloriegoal = calorie;
    }

    public static double getCaloriegoal() {
        return caloriegoal;
    }

    private class GetAddressAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String...param) {
            return RestConnect.findAddressByUsername(param[0]);
        }

        @Override
        protected void onPostExecute(String result)
        {
            String addressresult= result;
            SharedPreferences userinfo = getSharedPreferences("userinfo", 0);
            SharedPreferences.Editor editor = userinfo.edit();
            editor.putString("address",addressresult);
            editor.commit();
        }

    }

    public static void clear(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

        editor.commit();
    }


//    private class InsertDatabase extends AsyncTask<String, Void, Void> {
//        @Override
//        protected Void doInBackground(String... params) {
//            if (sd.StepDao().checkByName(username) == 0) {
//                step = new Step(username, 0, Calendar.getInstance().getTime(), 0);
//                sd.StepDao().insert(step);
//            } else {
//                step = sd.StepDao().getByName(username).get(0);
//            }
//            return null;
//        }
//    }
}


