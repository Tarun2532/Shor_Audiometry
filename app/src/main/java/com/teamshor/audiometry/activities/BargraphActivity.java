package com.teamshor.audiometry.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.teamshor.audiometry.R;
import com.teamshor.audiometry.ResultModal;
import com.teamshor.audiometry.Results;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class BargraphActivity extends AppCompatActivity {
    float groupSpace = 0.06f;
    float barSpace = 0.02f;
    float barWidth = 0.45f;
    ArrayList<BarEntry> rightear= new ArrayList<>();
    ArrayList<BarEntry> leftear= new ArrayList<>();
    ArrayList<Entry> audiogram_r= new ArrayList<>();
    ArrayList<Entry> audiogram_l= new ArrayList<>();
    SharedPreferences sharedPreferences;

    HorizontalBarChart chart;
    Button hmpge,audiogram;
    String stamp;
    TextView l_dblevel, r_dblevel, l_description, r_description, l_category,r_category,timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bargraph);

        stamp=getIntent().getStringExtra("Date and Time");

        chart = (HorizontalBarChart) findViewById(R.id.barchart);
        hmpge= findViewById(R.id.btn_homepage);
        audiogram= findViewById(R.id.btn_audiograph);
        l_dblevel = findViewById(R.id.l_dblevel_txt);
        r_dblevel = findViewById(R.id.r_dblevel_txt);
        l_description = findViewById(R.id.l_content_txt);
        r_description= findViewById(R.id.r_content_txt);
        l_category = findViewById(R.id.l_category_txt);
        r_category = findViewById(R.id.r_category_txt);
        timestamp= findViewById(R.id.bar_timestamp);
        timestamp.setText(getIntent().getStringExtra("Date")+"\n"+getIntent().getStringExtra("Time"));
        sharedPreferences=getSharedPreferences(Results.getStamp(getIntent().getStringExtra("Date"),
                                                                getIntent().getStringExtra("Time")), MODE_PRIVATE);



        bar_process();




        hmpge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BargraphActivity.this, TestActivity.class));
            }
        });
        audiogram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*audiogram_l = (ArrayList<Entry>) getIntent().getSerializableExtra("Audiogram Values Left");
                audiogram_r = (ArrayList<Entry>) getIntent().getSerializableExtra("Audiogram Values Right");*/
                audiogram_l=Loadvalues("","Audiogram Left");
                audiogram_r=Loadvalues("","Audiogram Right");
                Intent intent=new Intent(BargraphActivity.this, AudiogramActivity.class);
                intent.putExtra("Audiogram Values Left",audiogram_l);
                intent.putExtra("Audiogram Values Right",audiogram_r);
                intent.putExtra("Date and Time",timestamp.getText().toString());
                startActivity(intent);
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(this, TestActivity.class));
    }
    private void bar_process(){

        getBarEntries();

        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(true);


        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getAxisRight().setEnabled(false);


        /*ArrayList<BarEntry> leftear = new ArrayList<>();
        ArrayList<BarEntry> rightear = new ArrayList<>();
        ArrayList<BarEntry> barEntries2 = new ArrayList<>();
        ArrayList<BarEntry> barEntries3 = new ArrayList<>();*/

        /*leftear.add(new BarEntry(1,25.00f));
        leftear.add(new BarEntry(2,10.00f));
        leftear.add(new BarEntry(3,5.00f));
        leftear.add(new BarEntry(4,10.00f));
        leftear.add(new BarEntry(5,10.00f));
        leftear.add(new BarEntry(6,25.00f));
        leftear.add(new BarEntry(7,15.00f));

        rightear.add(new BarEntry(1,35.00f));
        rightear.add(new BarEntry(2,45.00f));
        rightear.add(new BarEntry(3,45.00f));
        rightear.add(new BarEntry(4,55.00f));
        rightear.add(new BarEntry(5,65.00f));
        rightear.add(new BarEntry(6,65.00f));
        rightear.add(new BarEntry(7,55.00f));*/

        /*barEntries2.add(new BarEntry(1,900));
        barEntries2.add(new BarEntry(2,691));
        barEntries2.add(new BarEntry(3,1030));
        barEntries2.add(new BarEntry(4,382));
        barEntries2.add(new BarEntry(5,2714f));
        barEntries2.add(new BarEntry(6,5000));
        barEntries2.add(new BarEntry(7,1173f));

        barEntries3.add(new BarEntry(1,200));
        barEntries3.add(new BarEntry(2,991));
        barEntries3.add(new BarEntry(3,1830));
        barEntries3.add(new BarEntry(4,3082));
        barEntries3.add(new BarEntry(5,214));
        barEntries3.add(new BarEntry(6,5600));
        barEntries3.add(new BarEntry(7,9173));

*/

        BarDataSet barDataSet = new BarDataSet(leftear,"Left ear");
        barDataSet.setColor(Color.parseColor("#3531AF"));
        BarDataSet barDataSet1 = new BarDataSet(rightear,"Right ear");
        barDataSet1.setColors(Color.parseColor("#F64344"));
        BarDataSet barDataSet4 = new BarDataSet(leftear,"");
        barDataSet4.setColors(Color.parseColor("#F9F9FC"));
        BarDataSet barDataSet5 =  new BarDataSet(rightear,"");
        barDataSet5.setColors(Color.parseColor("#F9F9FC"));


        String[] months = new String[] {"250", "500", "1000", "1500","2000","3000","4000","6000"};
        BarData data = new BarData(barDataSet4,barDataSet5,barDataSet1,barDataSet);
        //BarData data = new BarData(barDataSet,barDataSet1);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
        chart.setData(data);

        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisLeft().setAxisMaximum(120); //added
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularityEnabled(true);
        float barSpace = 0.02f;
        float groupSpace = 0.3f;
        int groupCount = 8;
        data.setBarWidth(0.15f);
        chart.getXAxis().setAxisMinimum(0);
        chart.getDescription().setText("X AXIS:DB LEVELS \n Y AXIS:FREQUENCY");
        chart.getDescription().setTextColor(Color.BLACK);
        //chart.getXAxis().setAxisMaxValue(9000);
        chart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        chart.groupBars(0, groupSpace, barSpace); // perform the "explicit" grouping
        chart.animateXY(2000, 2000);
    }

    private void getBarEntries(){

        float[] l_values, r_values;


        l_values = Loadvalues(0f,"Bar Left");
        r_values = Loadvalues(0f,"Bar Right");
        for(int i=0;i<8;i++)
        {
            rightear.add(new BarEntry( i,r_values[i]));
            leftear.add(new BarEntry(i, l_values[i]));
        }

        calc_avg(l_values,l_category,l_dblevel,l_description);
        calc_avg(r_values,r_category,r_dblevel,r_description);


    }
    private void calc_avg(float[] str, TextView category, TextView level, TextView content)
    {
        int average; float temp=0f;
        for (float v : str) {
            temp += v;
        }
        average= (int) (temp/str.length);
        Log.d("Average",String.valueOf(average));

        if(average >90)
        {
            category.setText("Profound Hearing loss");
            content.setText("Amplified speech or devices are still  difficult or impossible to hear and understand");
            level.setText("(91+ db)");
        }
        else if(average >80)
        {
            category.setText("Severe-to-Profound Hearing Loss ");
            content.setText("Difficulties with speech, comprehension becomes impossible without amplification");
            level.setText("(80-90 dB)");
        }
        else if(average >60)
        {
            category.setText("Severe Hearing Loss");
            content.setText("Speech has to be louder than normal, group conversations are difficult");
            level.setText("(60-80 dB)");
        }
        else if(average >40)
        {
            category.setText("Moderate Hearing Loss");
            content.setText("Difficulty understanding speech, higher volume levels are required for hearing TV and radio");
            level.setText("(40-60 dB)");
        }
        else if(average >25)
        {
            category.setText("Mild Hearing Loss");
            content.setText("Difficulty hearing and understanding quiet/soft conversations, especially situations with a lot of background noise (restaurants, classrooms, etc.)");
            level.setText("(25-40 dB)");
        }
        else
        {
            category.setText("Normal Hearing");
            content.setText("No perceived hearing loss symptoms");
            level.setText("(0-25 dB)");
        }
    }
    private float[] Loadvalues(float t,String name)
    {

        float[] res;
        // creating a variable for gson.
        Gson gson = new Gson();

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        String json = sharedPreferences.getString(name, null);

        // below line is to get the type of our array list.
        Type type = new TypeToken<float[]>() {}.getType();

        // in below line we are getting data from gson
        // and saving it to our array list
        res = gson.fromJson(json, type);
        return res;
    }
    private ArrayList<Entry> Loadvalues(String t,String name)
    {

        ArrayList<Entry> res;
        // creating a variable for gson.
        Gson gson = new Gson();

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        String json = sharedPreferences.getString(name, null);

        // below line is to get the type of our array list.
        Type type = new TypeToken<ArrayList<Entry>>() {}.getType();

        // in below line we are getting data from gson
        // and saving it to our array list
        res = gson.fromJson(json, type);
        return res;
    }





}
