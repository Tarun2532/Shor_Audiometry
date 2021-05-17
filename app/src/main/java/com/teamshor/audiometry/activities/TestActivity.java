package com.teamshor.audiometry.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.teamshor.audiometry.fragments.EmptyhomeFragment;
import com.teamshor.audiometry.R;
import com.teamshor.audiometry.databinding.ActivityTestBinding;
import com.teamshor.audiometry.fragments.FilledTestFragment;

public class TestActivity extends AppCompatActivity {
    ActivityTestBinding binding;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //getSupportFragmentManager().beginTransaction().replace(R.id.audio_fragment,new EmptyhomeFragment()).commit();


        //checking if user is first time then display empty fragment else filled fragment
        sharedPreferences=getSharedPreferences("Init",MODE_PRIVATE);
        boolean init=sharedPreferences.getBoolean("First Time",true);

        if (init)
            getSupportFragmentManager().beginTransaction().replace(R.id.audio_fragment,new EmptyhomeFragment()).commit();
        else
            getSupportFragmentManager().beginTransaction().replace(R.id.audio_fragment,new FilledTestFragment() ).commit();


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}