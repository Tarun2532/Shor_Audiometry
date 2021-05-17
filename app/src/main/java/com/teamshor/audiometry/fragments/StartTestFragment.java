package com.teamshor.audiometry.fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.teamshor.audiometry.R;
import com.teamshor.audiometry.databinding.FragmentStarttestBinding;

public class StartTestFragment extends Fragment {
    private static final String TAG = "StartTest";
    private MusicIntentReceiver myReceiver;
    private SettingsContentObserver myReceiver2;
    FragmentStarttestBinding binding;
    private AudioManager audioManager;


    public StartTestFragment()
    {

    }

    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentStarttestBinding.inflate(inflater, container, false);
        View view = binding.getRoot();




        myReceiver = new MusicIntentReceiver();

        //checking audiolevel
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        int n= audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) -
                audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        Log.d(TAG,"value:- "+n);
        binding.styleCheck2.setChecked(n == 0);

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.styleCheck.isChecked() && binding.styleCheck2.isChecked()) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.audio_fragment, new SelectEarFragment()).commit();

                    //startActivity(new Intent(StartTest.this, HearingTestActivity.class));
                }
                else
                    Toast.makeText(getActivity(), "Please complete the operations", Toast.LENGTH_SHORT).show();
            }
        });
        Handler handler=new Handler();
        myReceiver2=new SettingsContentObserver(getActivity(),handler);

        binding.textTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.termsandconditionsgenerator.com/live.php?token=iQcsYW0W5lksNezULTSOWPjoQLLtRniE"));
                startActivity(intent);
            }
        });
        checkUI();
        return view;

    }
    @SuppressLint("ResourceAsColor")
    public void checkUI()
    {
        if(binding.styleCheck.isChecked() && binding.styleCheck2.isChecked())
        {
            binding.buttonNext.setTextColor(Color.WHITE);
            binding.buttonNext.setBackgroundResource(R.drawable.btn_start);
        }
        else
        {
            binding.buttonNext.setTextColor(Color.WHITE);
            binding.buttonNext.setBackgroundResource(R.drawable.btn_start_no);
        }
    }




    @Override public void onResume() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        getActivity().registerReceiver(myReceiver, filter);
        getActivity().getContentResolver().registerContentObserver
                (android.provider.Settings.System.CONTENT_URI, true,myReceiver2 );
        super.onResume();
    }

    private class MusicIntentReceiver extends BroadcastReceiver {
        @Override public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        // StartTest.headphone=false;
                        Log.d(TAG, "Headset is unplugged");
                        binding.styleCheck.setChecked(false);
                        checkUI();
                        break;
                    case 1:
                        // StartTest.headphone=true;
                        Log.d(TAG, "Headset is plugged");
                        binding.styleCheck.setChecked(true);
                        checkUI();
                        break;
                    default:
                        Log.d(TAG, "I have no idea what the headset state is");
                }
            }
        }
    }

    @Override public void onPause() {
        getActivity().unregisterReceiver(myReceiver);
        getActivity().getContentResolver().unregisterContentObserver(myReceiver2);
        super.onPause();
    }
    public class SettingsContentObserver extends ContentObserver {

        public SettingsContentObserver(Context context, Handler handler) {
            super(handler);
        }

        @Override
        public boolean deliverSelfNotifications() {
            return true;
        }

        @Override
        public void onChange(boolean selfChange) {
            //int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            int n= audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) -
                    audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            binding.styleCheck2.setChecked(n == 0);
            checkUI();


        }
    }

}


