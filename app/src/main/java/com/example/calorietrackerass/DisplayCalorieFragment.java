package com.example.calorietrackerass;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import SQlite.Step;
import SQlite.StepDatabase;
import SQlite.User;

/*
The calorie consumed is stored in String because the SharedPreference doesn't support Double
 */


public class DisplayCalorieFragment extends Fragment {
    View vDisplayCalorie;
    String username;
    String today;
    EditText tv_cal_goal;
    TextView tv_step_now;
    TextView tv_cal_consumed;
    TextView tv_cal_burned;
    TextView tv_food_eaten;
    TextView tv_cal_remain;
    Button updateCalorie;
    StepDatabase sd;
    User user;
    Report report;
    CardView card_cal_goal;
    TextView food_eaten;
    Button submit;
    int stepnow;
    int calgoal;
    double calconsumed;
    double calremain;
    //String today;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vDisplayCalorie = inflater.inflate(R.layout.fragment_calorie, container, false);
        sd = Room.databaseBuilder(getActivity(),
                StepDatabase.class, "StepDatabase")
                .fallbackToDestructiveMigration()
                .build();
        card_cal_goal = vDisplayCalorie.findViewById(R.id.card_cal1);
        tv_cal_goal = vDisplayCalorie.findViewById(R.id.tv_cal_goal2);
        tv_step_now = vDisplayCalorie.findViewById(R.id.tv_cal_nowstep2);
        tv_cal_remain = vDisplayCalorie.findViewById(R.id.tv_cal_remain2);
        tv_cal_consumed = vDisplayCalorie.findViewById(R.id.tv_cal_consume2);
        tv_cal_burned = vDisplayCalorie.findViewById(R.id.tv_cal_burned2);
        updateCalorie = vDisplayCalorie.findViewById(R.id.update);
        submit = vDisplayCalorie.findViewById(R.id.submitreport);
        SharedPreferences userinfo = getActivity().getSharedPreferences("userinfo",0);
        SharedPreferences.Editor editor = userinfo.edit();
        stepnow = userinfo.getInt("stepnow",0);
        username = userinfo.getString("username","unknown");
        calgoal = userinfo.getInt("calgoal",0);
        tv_cal_goal.setText(""+calgoal);
        tv_step_now.setText(""+stepnow);
        //ReadDatabase getNowStep = new ReadDatabase();
        //getNowStep.execute(username);
        //need consumption
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        today = sdf.format(Calendar.getInstance().getTime());
        editor.putString("today",today);
        editor.commit();
        FindUseridTask findUseridTask = new FindUseridTask();
        findUseridTask.execute(username);
        FindConsume findConsume = new FindConsume();
        findConsume.execute(userinfo.getInt("userid",0));
        FindBmr findBmr = new FindBmr();
        findBmr.execute(userinfo.getInt("userid",0));

        card_cal_goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calgoal = Integer.parseInt(tv_cal_goal.getText().toString());
                editor.putInt("calgoal",calgoal);
                editor.commit();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostReport postReport = new PostReport();
                postReport.execute();
            }
        });

        return vDisplayCalorie;
    }

//    private class ReadDatabase extends AsyncTask<String, Void, Integer> {
//        @Override
//        protected Integer doInBackground(String...params) {
////            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
////            String todayDate = sdf.format(today);
//            Integer stepNow = 0;
//            try{
//                stepNow = sd.StepDao().getTotalStepThatDay(today,params[0]);
//            }catch (Exception e){
//                stepNow = 0;
//            }
//
//            return stepNow;
//        }

//        @Override
//        protected void onPostExecute(Integer step) {
//            final SharedPreferences userinfo = getActivity().getSharedPreferences("userinfo", 0);
//            final SharedPreferences.Editor editor = userinfo.edit();
////            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
////            String todayDate = sdf.format(today);
//            editor.putInt("stepnow",step);
//            editor.commit();
//            tv_step_now.setText(""+step);
//        }
//    }

    private class FindUseridTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String...params) {
            Integer userid = RestConnect.findUserIDByUsername(params[0]);
            return userid;
        }

        @Override
        protected void onPostExecute(Integer userid) {
            SharedPreferences userinfo = getActivity().getSharedPreferences("userinfo",0);
            SharedPreferences.Editor editor = userinfo.edit();
            editor.putInt("userid",userid);
            editor.commit();
        }
    }

    private class FindConsume extends AsyncTask<Integer, Void, Double> {
        @Override
        protected Double doInBackground(Integer...params) {
            double calconsumed = RestConnect.findConsumption(params[0],today);
            return calconsumed;
        }

        @Override
        protected void onPostExecute(Double result) {
            SharedPreferences userinfo = getActivity().getSharedPreferences("userinfo",0);
            SharedPreferences.Editor editor = userinfo.edit();
            editor.putString("calconsume",""+result);
            editor.commit();
            tv_cal_consumed.setText(""+result);
            calconsumed = result;
        }
    }

    private class FindBmr extends AsyncTask<Integer, Void, Double> {
        @Override
        protected Double doInBackground(Integer...params) {
            double bmr = RestConnect.findbmr(params[0]);
            return bmr;
        }

        @Override
        protected void onPostExecute(Double bmr) {
            FindCalBurned findCalBurned = new FindCalBurned();
            findCalBurned.execute(bmr);
        }
    }

    private class FindCalBurned extends AsyncTask<Double, Void, Double> {
        @Override
        protected Double doInBackground(Double...params) {
            SharedPreferences userinfo = getActivity().getSharedPreferences("userinfo",0);
            double calperstep = RestConnect.findcalperstep(userinfo.getInt("userid",0));
            double totalCalBurned = params[0] + calperstep * userinfo.getInt("stepnow",0);
            return totalCalBurned;
        }

        @Override
        protected void onPostExecute(Double totalCalBurned) {
            SharedPreferences userinfo = getActivity().getSharedPreferences("userinfo",0);
            tv_cal_burned.setText(""+totalCalBurned);
            double calremain = userinfo.getInt("calgoal",0) + totalCalBurned - Double.parseDouble(userinfo.getString("calconsume","0"));
            tv_cal_remain.setText(""+ calremain);
            SharedPreferences.Editor editor = userinfo.edit();
            editor.putString("calburned",""+totalCalBurned);
            editor.commit();
        }
    }

    private class PostReport extends AsyncTask<Void, Void, Report> {
        @Override
        protected Report doInBackground(Void...param) {
            Gson gson = new Gson();
            SharedPreferences userinfo = getActivity().getSharedPreferences("userinfo",0);
            User user =gson.fromJson(RestConnect.findUserById(userinfo.getInt("userid",0)),User.class);
            Report report = new Report(Double.parseDouble(tv_cal_goal.getText().toString()), Double.parseDouble(tv_cal_burned.getText().toString()),
                    Double.parseDouble(tv_cal_consumed.getText().toString()),Integer.parseInt(tv_step_now.getText().toString()),user);
            RestConnect.postReport(report);
            return report;
        }

        @Override
        protected void onPostExecute(Report report) {
            Toast.makeText(getActivity(),"Submit successfully!",Toast.LENGTH_LONG).show();
        }
    }


}