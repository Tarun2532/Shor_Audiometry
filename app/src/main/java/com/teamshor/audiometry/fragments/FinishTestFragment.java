package com.teamshor.audiometry.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.data.Entry;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.teamshor.audiometry.ResultModal;
import com.teamshor.audiometry.Results;
import com.teamshor.audiometry.activities.BargraphActivity;
import com.teamshor.audiometry.databinding.FragmentFinishTestBinding;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;


public class FinishTestFragment extends Fragment {

    private static final String TAG ="Finish" ;
    FragmentFinishTestBinding binding;
    public static Activity activity;
    public static String Time;
    public static String Date;
    public static long epoch;
    public SharedPreferences sharedPreferences, sharedPreferences2,sharedPreferences3;
    ArrayList<Entry> audiogram_r = new ArrayList<>();
    ArrayList<Entry> audiogram_l = new ArrayList<>();
    float[] bar_r = new float[8];
    float[] bar_l = new float[8];
    long currentTimestamp;



    //RaxisData= actual Decibels(String), RyAxisData-- Frequency(FLOAT)
    //Dbright= actual           ,Raxis--Freq

    public FinishTestFragment(String[] RaxisData, float[] RyAxisData, String[] LaxisData, float[] LyAxisData) {
        int flag = 7;
        for (int i = 0; i < RaxisData.length; i++) {

            if (i <= 1) {
                audiogram_r.add(new Entry((RyAxisData[flag]), Float.parseFloat(RaxisData[flag])));
                audiogram_l.add(new Entry(LyAxisData[flag], Float.parseFloat(LaxisData[flag])));
                bar_r[i] = Float.parseFloat(RaxisData[flag]);
                bar_l[i] = Float.parseFloat(LaxisData[flag]);
                flag--;
            } else {
                audiogram_r.add(new Entry((RyAxisData[i - 2]), Float.parseFloat(RaxisData[i - 2])));
                audiogram_l.add(new Entry(LyAxisData[i - 2], Float.parseFloat(LaxisData[i - 2])));
                bar_r[i] = Float.parseFloat(RaxisData[i - 2]);
                bar_l[i] = Float.parseFloat(LaxisData[i - 2]);

            }

        }
    }


    public FinishTestFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFinishTestBinding.inflate(inflater, container, false);
        currentTimestamp = Instant.now().toEpochMilli();
        binding.buttonNxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getformat();

                sharedPreferences = getActivity().getSharedPreferences(Results.getStamp(Date,Time), MODE_PRIVATE);
                sharedPreferences2 = getActivity().getSharedPreferences("Init", MODE_PRIVATE);
                sharedPreferences3=getActivity().getSharedPreferences("Filenames",MODE_PRIVATE);
                saveData();

                getActivity().getSupportFragmentManager().beginTransaction().remove(new FinishTestFragment()).commit();
                Intent intent = new Intent(getActivity(), BargraphActivity.class);
                /*intent.putExtra("Audiogram Values Left", audiogram_l);
                intent.putExtra("Audiogram Values Right", audiogram_r);
                intent.putExtra("Bar Values Left", bar_l);
                intent.putExtra("Bar Values Right", bar_r);*/
                intent.putExtra("Date",Date);
                intent.putExtra("Time",Time);

                //courseModalArrayList.add(new ResultModal(String.valueOf(currentTimestamp),audiogram_r,audiogram_l));
                //adapter.notifyItemInserted(courseModalArrayList.size());
                //saveData();

                startActivity(intent);


                //Toast.makeText(getActivity(), "onClicked finish",Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    private void saveData() {


        Gson gson = new Gson();
        String audioleft = gson.toJson(audiogram_l);
        String audioright = gson.toJson(audiogram_r);
        String barleft = gson.toJson(bar_l);
        String barright = gson.toJson(bar_r);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Audiogram Left", audioleft);
        editor.putString("Audiogram Right", audioright);
        editor.putString("Bar Left", barleft);
        editor.putString("Bar Right", barright);
        editor.putString("Date and Time", Date+Time);
        editor.apply();

        /*String filenames=sharedPreferences3.getString("filename","");
        SharedPreferences.Editor editor2 = sharedPreferences3.edit();
        editor2.putString("filename",filenames+" "+epoch);
        editor2.apply();*/

        // after saving data we are displaying a toast message.
        sharedfilechanges();
        saveResultmodals();
    }

    public void saveResultmodals(){
        if(FilledTestFragment.courseModalArrayList == null)
        {
            FilledTestFragment.courseModalArrayList = new ArrayList<ResultModal>();
            FilledTestFragment.adapter = new Results(FilledTestFragment.courseModalArrayList, getActivity());
        }

        FilledTestFragment.courseModalArrayList.add(new ResultModal(Time, Date));
        FilledTestFragment.adapter.notifyItemInserted(FilledTestFragment.courseModalArrayList.size());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MODELS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(FilledTestFragment.courseModalArrayList);
        editor.putString("items", json);
        editor.apply();

    }

    public static void getformat() {

        epoch = Instant.now().toEpochMilli();
        LocalDateTime ldt = Instant.ofEpochMilli(epoch)
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println(ldt);
        String val=String.valueOf(ldt);
        int i=val.indexOf("T");
        Date=val.substring(0,i);
        Time=val.substring(i+1,i+6);
    }

    private void sharedfilechanges() {
        SharedPreferences.Editor editor = sharedPreferences2.edit();
        editor.putBoolean("First Time", false);
        editor.apply();

    }
}