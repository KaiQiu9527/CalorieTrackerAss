package com.example.calorietrackerass;

//import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import SQlite.Step;

public class MainFragment extends Fragment implements View.OnClickListener {
    View vMain;
    TextClock tv_clock;
    TextView welcome;
    EditText tv_goal;
    String username;
    Integer caloriegoal;
    CardView goal;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_main, container, false);
        SharedPreferences userinfo = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = userinfo.edit();
        username = userinfo.getString("username","null");
        caloriegoal = userinfo.getInt("calgoal",0);
        tv_clock = (TextClock) vMain.findViewById(R.id.tv_clock);
        tv_goal = vMain.findViewById(R.id.tv_cal_goal2);
        welcome = vMain.findViewById(R.id.tv_welcome);
        welcome.setText("Welcome!" + username);
        goal = vMain.findViewById(R.id.card2);
        tv_goal.setText(""+caloriegoal);
        goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                caloriegoal = Integer.parseInt(tv_goal.getText().toString());
                tv_goal.setText(""+caloriegoal);
                editor.putInt("calgoal",caloriegoal);
                editor.commit();
                Toast.makeText(getActivity(),"Submit!",Toast.LENGTH_LONG).show();
                MainActivity.setCaloriegoal(caloriegoal);
            }
        });
        return vMain;
    }

    @Override
    public void onClick(View v) {

    }


}
