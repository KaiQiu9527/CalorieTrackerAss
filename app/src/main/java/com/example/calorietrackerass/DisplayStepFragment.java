package com.example.calorietrackerass;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import SQlite.Step;
import SQlite.StepDatabase;

public class DisplayStepFragment extends Fragment {
    View vDisplayStep;
    String username;
    StepDatabase sd;
    Step step;
    Date today = Calendar.getInstance().getTime();
    TextView tv_step_goal;
    TextView tv_stepNow;
    TextView tv_stepRemain;
    EditText tv_stepInsert;
    int stepgoal;
    int stepnow;
    int stepremain;
    Button updateStep;
    Button deleteStep;
    ReadDatabase read = new ReadDatabase();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        sd = Room.databaseBuilder(getActivity(),
                StepDatabase.class, "StepDatabase")
                .fallbackToDestructiveMigration()
                .build();

        vDisplayStep = inflater.inflate(R.layout.fragment_step, container, false);
        tv_step_goal = vDisplayStep.findViewById(R.id.tv_step_goal2);
        tv_stepNow = vDisplayStep.findViewById(R.id.tv_step_now2);
        tv_stepRemain = vDisplayStep.findViewById(R.id.tv_step_remain2);
        tv_stepInsert = vDisplayStep.findViewById(R.id.tv_step_insert2);
        updateStep = vDisplayStep.findViewById(R.id.update);
        deleteStep = vDisplayStep.findViewById(R.id.delete);
        //set data
        final SharedPreferences userinfo = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = userinfo.edit();
        final ReadDatabase read = new ReadDatabase();
        username = userinfo.getString("username","");
        stepgoal = userinfo.getInt("stepgoal",0);
        tv_step_goal.setText(""+stepgoal);
        stepnow = userinfo.getInt("stepnow",0);
        tv_stepNow.setText(""+stepnow);
        stepremain = stepgoal-stepnow;
        tv_stepRemain.setText(""+stepremain);
        read.execute(username);
        //click update
        updateStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("stepgoal",Integer.parseInt(tv_step_goal.getText().toString()));
                editor.putInt("stepinsert",Integer.parseInt(tv_stepInsert.getText().toString()));
                editor.commit();
                //int stepNow = userinfo.getInt("stepnow",0);
                //int stepRemain = stepGoal - stepNow;
                int stepInsert = userinfo.getInt("stepinsert",0);
                step = new Step(username,stepInsert,today);
                final InsertDatabase insert = new InsertDatabase();
                final ReadDatabase read = new ReadDatabase();
                insert.execute(step);
                read.execute(username);
//                if (stepRemain<0)
//                    stepRemain = 0;
//                tv_stepRemain.setText(""+stepRemain);
            }
        });

        deleteStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDatabase deleteDatabase = new DeleteDatabase();
                deleteDatabase.execute(username);
                ReadDatabase readDatabase = new ReadDatabase();
                readDatabase.execute(username);
            }
        });

        return vDisplayStep;
    }

    private class InsertDatabase extends AsyncTask<Step, Void, String> {
        @Override
        protected String doInBackground(Step... params) {
                sd.StepDao().insert(step);
                //tv_stepInsert.setText("0");
            return "ok";
        }
        @Override
        protected void onPostExecute(String ok)
        {
            tv_stepInsert.setText("0");
        }
    }

    private class ReadDatabase extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String...params) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String todayDate = sdf.format(today);
            Integer todayStep = sd.StepDao().getTotalStepThatDay(todayDate,params[0]);
            return todayStep;
        }

        @Override
        protected void onPostExecute(Integer step) {
            final SharedPreferences userinfo = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = userinfo.edit();
            editor.putInt("stepnow",step);
            editor.commit();
            tv_stepNow.setText(""+step);
            tv_stepRemain.setText(""+(userinfo.getInt("stepgoal",0)-step));
        }
    }

    private class DeleteDatabase extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            sd.StepDao().deleteByName(params[0]);
            //tv_stepInsert.setText("0");
            return "ok";
        }
        @Override
        protected void onPostExecute(String ok)
        {
            Toast.makeText(getActivity(),"The database has been deleted!",Toast.LENGTH_LONG).show();
            tv_stepInsert.setText("0");
            tv_stepNow.setText("0");
        }
    }

}