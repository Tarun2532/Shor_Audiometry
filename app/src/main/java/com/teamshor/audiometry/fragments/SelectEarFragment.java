package com.teamshor.audiometry.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.teamshor.audiometry.R;
import com.teamshor.audiometry.databinding.FragmentSelectearBinding;

public class SelectEarFragment extends Fragment {
    FragmentSelectearBinding binding;
    public static boolean LEFT_sw,RIGHT_sw;
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentSelectearBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        binding.btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LEFT_sw=true; RIGHT_sw=false;
                binding.switchId.setText("Left Ear");
                selection(true);
            }
        });
        binding.btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RIGHT_sw=true; LEFT_sw=false;
                binding.switchId.setText("Right Ear");
                selection(true);

            }
        });
        binding.txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection(false);
            }
        });
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.audio_fragment, new HearingTestFragment()).commit();

            }
        });
        return view;

    }
    public void selection(boolean check)
    {
        if(check)
        {
            binding.btnLeft.setVisibility(View.INVISIBLE);
            binding.btnRight.setVisibility(View.INVISIBLE);
            binding.switchId.setVisibility(View.VISIBLE);
            binding.next.setVisibility(View.VISIBLE);
        }
        else
        {
            binding.btnLeft.setVisibility(View.VISIBLE);
            binding.btnRight.setVisibility(View.VISIBLE);
            binding.switchId.setVisibility(View.INVISIBLE);
            binding.next.setVisibility(View.INVISIBLE);
        }
    }
}