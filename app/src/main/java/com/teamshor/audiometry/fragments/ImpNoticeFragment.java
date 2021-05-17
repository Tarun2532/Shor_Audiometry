package com.teamshor.audiometry.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamshor.audiometry.R;
import com.teamshor.audiometry.databinding.FragmentImpNoticeBinding;


public class ImpNoticeFragment extends Fragment {


    FragmentImpNoticeBinding binding;
    public ImpNoticeFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentImpNoticeBinding.inflate(inflater, container, false);
        binding.switchId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.audio_fragment,new StartTestFragment()).commit();

            }
        });
        return binding.getRoot();
    }
}