package com.teamshor.audiometry.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.teamshor.audiometry.R;
import com.teamshor.audiometry.ResultModal;
import com.teamshor.audiometry.Results;
import com.teamshor.audiometry.databinding.FragmentFilledtestBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FilledTestFragment extends Fragment {

    // creating variables for our ui components.
    private EditText courseNameEdt, courseDescEdt;

    // variable for our adapter class and array list
    public static Results adapter;
    public static ArrayList<ResultModal> courseModalArrayList;
    FragmentFilledtestBinding binding;
    public static Activity activity;


    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentFilledtestBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        activity=getActivity();
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.audio_fragment,new ImpNoticeFragment()).commit();

            }
        });
        binding.btnAboutTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.audio_fragment,new AboutearFragment()).commit();

            }
        });
        binding.btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.audio_fragment,new InfoFragment()).commit();

            }
        });
        

        // initializing our variables.

        // calling method to load data
        // from shared prefs.
        loadData();

        // calling method to build
        // recycler view.
        buildRecyclerView();

        return  view;
    }

    private void buildRecyclerView() {
        // initializing our adapter class.
        adapter = new Results(courseModalArrayList, getActivity());
        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setHasFixedSize(true);
        // setting layout manager to our recycler view.
        binding.recyclerView.setLayoutManager(manager);
        // setting adapter to our recycler view.
        binding.recyclerView.setAdapter(adapter);
        binding.txtReports.setText(adapter.getItemCount()+" Reports");
    }

    private void loadData() {
        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MODELS", MODE_PRIVATE);

        // creating a variable for gson.
        Gson gson = new Gson();

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        String json = sharedPreferences.getString("items", null);

        // below line is to get the type of our array list.
        Type type = new TypeToken<ArrayList<ResultModal>>() {}.getType();

        // in below line we are getting data from gson
        // and saving it to our array list
        courseModalArrayList = gson.fromJson(json, type);

        // checking below if the array list is empty or not
        if (courseModalArrayList == null) {
            // if the array list is empty
            // creating a new array list.
            courseModalArrayList = new ArrayList<>();
        }
    }

    private void saveData() {
        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // creating a new variable for gson.
        Gson gson = new Gson();

        // getting data from gson and storing it in a string.
        String json = gson.toJson(courseModalArrayList);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("courses", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();

        // after saving data we are displaying a toast message.
        Toast.makeText(getActivity(), "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
    }
}
