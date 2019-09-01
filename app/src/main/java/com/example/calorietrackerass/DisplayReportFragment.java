package com.example.calorietrackerass;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DisplayReportFragment extends Fragment {
    View vDisplayReport;
    List<HashMap<String, String>> reportListArray;
    SimpleAdapter reportListAdapter;
    ListView reportList;
    HashMap<String, String> reportMap;
    String[] reportHEAD = new String[]{"date"};
    TextView tv_report;
    int[] dataReport = new int[]{R.id.date};
    EditText datepicker;
    EditText datefrom;
    EditText dateto;
    //piechart
    PieChart mPieChart;
    BarChart mBarChart;
    ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
    PieData data;
    PieDataSet dataSet;
    ArrayList<BarEntry>  barEntries1 = new ArrayList<>();
    ArrayList<BarEntry>  barEntries2 = new ArrayList<>();
    BarDataSet barDataSet1;
    BarDataSet barDataSet2;
    BarData barData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vDisplayReport = inflater.inflate(R.layout.fragment_report, container, false);
        SharedPreferences userinfo = getActivity().getSharedPreferences("userinfo",0);
        tv_report = vDisplayReport.findViewById(R.id.tv_report);
        datepicker = vDisplayReport.findViewById(R.id.datepicker);
        datepicker.setText("Please choose the date!");
        datefrom = vDisplayReport.findViewById(R.id.from);
        dateto = vDisplayReport.findViewById(R.id.to);
        datefrom.setText("Please choose the date!");
        dateto.setText("Please choose the date!");
        mPieChart = vDisplayReport.findViewById(R.id.mPieChart);
        mBarChart = vDisplayReport.findViewById(R.id.mBarChart);

        Button submit = vDisplayReport.findViewById(R.id.choosedate);
        Button submit2 = vDisplayReport.findViewById(R.id.submit2);
        String choosedDateString;
        String to;
        String from;
        //first date picker
        datepicker.setInputType(InputType.TYPE_NULL);
        datepicker.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    showDatePickerDialog();
                }
            }
        });
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDatePickerDialog();
            }
        });
        //from date picker
        datefrom.setInputType(InputType.TYPE_NULL);
        datefrom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    Calendar c = Calendar.getInstance();
                    new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // TODO Auto-generated method stub
                            datefrom.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });
        datefrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        datefrom.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //to date picker
        dateto.setInputType(InputType.TYPE_NULL);
        dateto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    Calendar c = Calendar.getInstance();
                    new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // TODO Auto-generated method stub
                            dateto.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });
        dateto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        dateto.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindThatDayReport findThatDayReport = new FindThatDayReport();
                if(datepicker.getText().toString().equals("Please choose the date!"))
                {
                    Toast.makeText(getActivity(),"Please choose the date!",Toast.LENGTH_LONG).show();
                }
                else{
                    findThatDayReport.execute(""+userinfo.getInt("userid",0),datepicker.getText().toString());
                }

            }
        });

        submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindSomeDayReport findSomeDayReport = new FindSomeDayReport();
                if(datefrom.getText().toString().equals("Please choose the date") || dateto.getText().toString().equals("Please choose the date") || Integer.parseInt(datefrom.getText().toString().replace("-","")) > Integer.parseInt(dateto.getText().toString().replace("-","")))
                {
                    Toast.makeText(getActivity(),"Please choose the right date!",Toast.LENGTH_LONG).show();
                }
                else{
                    findSomeDayReport.execute(""+userinfo.getInt("userid",0),datefrom.getText().toString(),dateto.getText().toString());
                }

            }
        });

        return vDisplayReport;
    }

    private class FindThatDayReport extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String...params) {
            String reportstring =  RestConnect.findReportByUseridAndDate(Integer.parseInt(params[0]),params[1]);
            return reportstring;
        }

        @Override
        protected void onPostExecute(String allreportstring) {
            JSONArray reportArray = new JSONArray();
            String date = "";
            double calgoal = 0;
            double calconsume = 0;
            double calburn = 0;
            int steptaken = 0;
            double calremain = 0;
            double total  = 0;
            try {
                reportArray = new JSONArray(allreportstring);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (reportArray.length() > 0) {
                for (int i = 0; i < reportArray.length(); i++) {
                    try {
                        JSONObject reportobject = reportArray.getJSONObject(i);
                        date = reportobject.getString("date");
                        calconsume = reportobject.getDouble("consumecal");
                        calburn = reportobject.getDouble("burncal");
                        steptaken = reportobject.getInt("steptaken");
                        calgoal = reportobject.getDouble("calgoal");
                        calremain = calgoal + calburn - calconsume;
                        total = calburn +calconsume +calremain;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    entries.clear();
                    mPieChart = vDisplayReport.findViewById(R.id.mPieChart);
                    mPieChart.setUsePercentValues(true);
                    mPieChart.getDescription().setEnabled(false);
                    entries.add(new PieEntry((float)calburn, "calburn"));
                    entries.add(new PieEntry((float)calconsume, "calconsume"));
                    entries.add(new PieEntry((float)calremain, "calremain"));
                    //设置数据
                    dataSet = new PieDataSet(entries, "CALORIE");
                    dataSet.setSliceSpace(3f);
                    dataSet.setSelectionShift(5f);
                    dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    data = new PieData(dataSet);
                    mPieChart.setData(data);
                    //刷新
                    mPieChart.invalidate();
                    mPieChart.invalidate();


                Legend l = mPieChart.getLegend();
                l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                l.setOrientation(Legend.LegendOrientation.VERTICAL);
                l.setDrawInside(false);
                l.setXEntrySpace(7f);
                l.setYEntrySpace(0f);
                l.setYOffset(0f);
                // 输入标签样式
                mPieChart.setEntryLabelColor(Color.WHITE);
                mPieChart.setEntryLabelTextSize(12f);
                //tv_report.setText("calgoal:" + calgoal + " calconsume:" + calconsume + " calburn:" + calburn + " calremain:" + calremain + " steptaken:" + steptaken);
            }

                }else{
                Toast.makeText(getActivity(),"No record!",Toast.LENGTH_LONG).show();
                entries.clear();
                mPieChart = vDisplayReport.findViewById(R.id.mPieChart);
                //刷新
                mPieChart.invalidate();
                tv_report.setText("");
            }
        }
    }
//要改成barchart
    private class FindSomeDayReport extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String...params) {
            String reportstring =  RestConnect.findReportByUseridBetweenDate(Integer.parseInt(params[0]),params[1],params[2]);
            return reportstring;
        }

        @Override
        protected void onPostExecute(String allreportstring) {
            JSONArray reportArray = new JSONArray();
            String date = "";
            double calgoal = 0;
            double calconsume = 0;
            double calburn = 0;
            String reportdatestring = "";
            try {
                reportArray = new JSONArray(allreportstring);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (reportArray.length() > 0) {
                for (int i = 0; i < reportArray.length(); i++) {
                    try {
                        JSONObject reportobject = reportArray.getJSONObject(i);
                        calconsume = reportobject.getDouble("consumecal");
                        calburn = reportobject.getDouble("burncal");
                        reportdatestring = reportobject.getString("date");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mBarChart = vDisplayReport.findViewById(R.id.mBarChart);
                    barEntries1.add(new BarEntry(i, (float)calconsume));
                    barEntries2.add(new BarEntry(i, (float)calburn));
                }
            }
            barDataSet1 = new BarDataSet(barEntries1,"");
            barDataSet1.setValueTextSize(20);
            barDataSet1.setColor(Color.rgb(20,150,100));
            barDataSet2 = new BarDataSet(barEntries2,"");
            barDataSet2.setValueTextSize(20);
            barDataSet2.setColor(Color.rgb(30,30,150));
            barData = new BarData(barDataSet1,barDataSet2);
            mBarChart.setData(barData);
        }
    }

    //show date
    public void showDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                datepicker.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

    }

    }