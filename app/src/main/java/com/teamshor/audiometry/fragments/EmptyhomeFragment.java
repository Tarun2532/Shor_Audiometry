package com.teamshor.audiometry.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.teamshor.audiometry.R;
import com.teamshor.audiometry.databinding.FragmentEmptyhomeBinding;

public class EmptyhomeFragment extends Fragment {
    FragmentEmptyhomeBinding binding;
    public static boolean LEFT_sw,RIGHT_sw;
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentEmptyhomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.btnAboutTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.audio_fragment,new AboutearFragment()).commit();

            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.audio_fragment,new ImpNoticeFragment()).commit();

            }
        });
        binding.btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.audio_fragment,new InfoFragment()).commit();

            }
        });

        return view;

    }

}