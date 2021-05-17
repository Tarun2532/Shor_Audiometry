package com.teamshor.audiometry.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.teamshor.audiometry.R;
import com.teamshor.audiometry.activities.TestActivity;
import com.teamshor.audiometry.databinding.FragmentAboutteamBinding;

public class AboutearFragment extends Fragment {
    FragmentAboutteamBinding binding;
    public static boolean LEFT_sw,RIGHT_sw;
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentAboutteamBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.btnHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getActivity(), TestActivity.class));
            }
        });


        return view;

    }


}