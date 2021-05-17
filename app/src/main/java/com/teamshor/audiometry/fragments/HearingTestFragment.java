/*
 * This class contains main operation of hearing test
 * rendering of audiogram
 *
 * @version Build (6 June 2015)
 * @author Junaid Malik
 */
package com.teamshor.audiometry.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.teamshor.audiometry.sourcefiles.FrequencyGenerator;
import com.teamshor.audiometry.R;
import com.teamshor.audiometry.databinding.FragmentHearingtestBinding;


public class HearingTestFragment extends
		Fragment {

	public HearingTestFragment() {

	}
	public static FragmentHearingtestBinding binding;
	public static TextView currentHZ;
	public static Activity activity;
	
	public static boolean LEFTEAR= SelectEarFragment.LEFT_sw,
	RIGHTEAR= SelectEarFragment.RIGHT_sw;
	
	private static final String TAG="Frequency";
	private static final String TAG1="Audio";
	private static final String TAG2="Saving";

	public static boolean tone_playing,performs_first_time;
	
	private FrequencyGenerator frequencygen = new FrequencyGenerator(); // create FreqGen
	public  int currentFreq = 0, HEAR=0,NOT_HEAR=0,flag=0,DB_LEVEL=25,min=100,current_not_heard=0;
	public static double progressval=12.1;

	public Integer[] frequencies = { 1000,1500,2000,3000,4000,6000,500,250 };
	public String[] leftear_db = new String[frequencies.length],
			rightear_db = new String[frequencies.length];
	public float[] freqleft = new float[frequencies.length],
			freqright = new float[frequencies.length];
	public int leftinit = -1, rightinit = -1;



	public View onCreateView (LayoutInflater inflater,
							  ViewGroup container,
							  Bundle savedInstanceState) {
		binding = FragmentHearingtestBinding.inflate(inflater, container, false);
		View view = binding.getRoot();

		tone_playing=true; performs_first_time=true;
		activity= getActivity(); currentHZ=binding.txtProgress;
		
		changedEar();
		performOperation();

		binding.buttonHear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

					HEAR++;
					Log.d(TAG1,"HEAR= "+HEAR);
					do_main();


			}
		});

		binding.buttonCanthear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					NOT_HEAR++;
					Log.d(TAG1,"NOT HEAR= "+NOT_HEAR);
					do_main();

			}
		});

		binding.txtReset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				currentFreq = 0; HEAR=0;NOT_HEAR=0;flag=0;DB_LEVEL=25;min=100;current_not_heard=0;leftinit = -1;rightinit = -1;
				progressval=12.1;
				tone_playing=true; performs_first_time=true;
				LEFTEAR= SelectEarFragment.LEFT_sw; RIGHTEAR= SelectEarFragment.RIGHT_sw;
				changedEar();
				performOperation();
			}
		});

		return view;


	}
	public void performOperation()
	{
		try {

			Log.d(TAG,"CurrentFREQ:-"+currentFreq);
			Log.d(TAG,"play button");


			currentHZ.setText(frequencies[currentFreq].toString()+"hz \n"+DB_LEVEL+"DB");
			FrequencyGenerator.frequencyOfTone = frequencies[currentFreq];
			frequencygen.genTone();
			Log.d(TAG1,"DB_LEVEL="+DB_LEVEL);
			frequencygen.playSound(DB_LEVEL);
			initbuttons();



		} 
		catch (ArrayIndexOutOfBoundsException exception) {
			finishTest();
		}
	}
	public void do_main()
	{
		flag++;
		Log.d(TAG1,"flag="+flag);

		if(flag ==3 || HEAR ==2 || NOT_HEAR==2)
		{
			if(HEAR > NOT_HEAR)
				perform_hear();
			else
				perform_nothear();
			performOperation();
			init();
		}
		else
			performOperation();
	}
	public void perform_hear()
	{
		min=DB_LEVEL;
		DB_LEVEL=DB_LEVEL-10;
		Log.d(TAG1,"can hear, DB_LEVEL="+DB_LEVEL+" current_not_heard= "+current_not_heard);
		if(DB_LEVEL >0 && DB_LEVEL <min && DB_LEVEL> current_not_heard)
		{
			currentHZ.setText(frequencies[currentFreq].toString()+"hz \n"+DB_LEVEL+"DB");
		}
		else
		{

			Log.d(TAG2,"save");
			Log.d(TAG2,"min while saving= "+min);
			save();
			next_Freq();
		}
	}
	public void perform_nothear()
	{
		current_not_heard=DB_LEVEL;
		DB_LEVEL=DB_LEVEL+5;

		Log.d(TAG1,"not hear, DB_LEVEL="+DB_LEVEL+" current_not_heard= "+current_not_heard);

		if(DB_LEVEL >0 && min >DB_LEVEL)
		{
			currentHZ.setText(frequencies[currentFreq].toString()+"hz \n"+DB_LEVEL+"DB");

		}
		else
		{

			Log.d(TAG2,"save");
			Log.d(TAG2,"min while saving= "+min);
			save();
			next_Freq();
		}
	}
	public void init()
	{
		flag=0;
		HEAR=0;
		NOT_HEAR=0;

	}
	public void next_Freq()
	{
		min=100;
		if(!performs_first_time)
		{
			if(LEFTEAR)
				DB_LEVEL=25+ Integer.parseInt(rightear_db[currentFreq]);
			else
				DB_LEVEL=25+ Integer.parseInt(leftear_db[currentFreq]);
		}
		else
			DB_LEVEL=25;

		current_not_heard=0;
		currentHZ.setText(frequencies[currentFreq].toString()+"hz \n"+DB_LEVEL+"DB");
		progressval+=11.1;
		binding.progressBar.setProgress((int)progressval);
		currentFreq++;
	}
	public void save() {
		Log.d(TAG, "inside init(str)");
		if (LEFTEAR) {
			leftinit++;
			freqleft[leftinit] = (float) (frequencies[currentFreq]);
			leftear_db[leftinit]= String.valueOf(min);
			Log.d(TAG, "leftinit " + leftinit + "freqleft " + freqleft[leftinit]+ "leftear_db "+leftear_db[leftinit]);

		} else {
			Log.d(TAG1, "inside init(right)");
			rightinit++;
			freqright[rightinit] = (float) (frequencies[currentFreq]);
			rightear_db[rightinit]= String.valueOf(min);
			Log.d(TAG, "rightinit " + rightinit + "freqright " + freqright[rightinit]+"rightear_db "+rightear_db[rightinit]);
		}
	}





	public void finishTest() {
		if(!performs_first_time) {

			FrequencyGenerator.audioTrack.release();
			FrequencyGenerator.audioTrack = null;
			FrequencyGenerator.ts.cancel();
			tone_playing=true;
			getActivity().getSupportFragmentManager().beginTransaction().remove(new HearingTestFragment()).commit();

			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.audio_fragment,
					new FinishTestFragment(rightear_db,freqright,leftear_db,freqleft))
					.commit();

		}
		else
		{

			Log.d(TAG,"Finish, else");
			FrequencyGenerator.audioTrack.stop();
			RIGHTEAR=!RIGHTEAR;
			LEFTEAR=!LEFTEAR;
			performs_first_time=false;
			changedEar();
			performOperation();


		}
	}
	public void changedEar()
	{
		currentFreq=0;
		if(!performs_first_time)
		{
			if(LEFTEAR)
				DB_LEVEL=25+ Integer.parseInt(rightear_db[currentFreq]);
			else
				DB_LEVEL=25+ Integer.parseInt(leftear_db[currentFreq]);

		}
		else
		{
			DB_LEVEL=25;
		}
		tone_playing=true;
		progressval=11.1;
		if(LEFTEAR) {
			binding.progressBar.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
			binding.hearingDesc.setText(R.string.left_describe);
			binding.progressBar.setProgress((int)progressval);
			binding.viewL.setVisibility(View.VISIBLE);
			binding.viewR.setVisibility(View.INVISIBLE);
		}
		else
		{
			binding.progressBar.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
			binding.hearingDesc.setText(R.string.right_describe);
			binding.progressBar.setProgress((int)progressval);
			binding.viewR.setVisibility(View.VISIBLE);
			binding.viewL.setVisibility(View.INVISIBLE);
		}
	}

	@SuppressLint("ResourceAsColor")
	public static void initbuttons() {

		if(tone_playing)
		{
			binding.startT3.setText("The tone is playing.");
			binding.buttonHear.setClickable(false);
			binding.buttonCanthear.setClickable(false);
			binding.buttonHear.setBackgroundResource(R.drawable.btn_start_no);
			binding.buttonCanthear.setBackgroundResource(R.drawable.btn_start_no);
			binding.buttonHear.setTextColor(Color.GRAY);
			binding.buttonCanthear.setTextColor(Color.GRAY);
			tone_playing=false;
			if(RIGHTEAR)
				binding.rightSound.setVisibility(View.VISIBLE);
			else
				binding.leftSound.setVisibility(View.VISIBLE);

		}
		else
		{
			binding.startT3.setText("Had you heard the tone?");
			binding.buttonHear.setClickable(true);
			binding.buttonCanthear.setClickable(true);
			binding.buttonHear.setBackgroundResource(R.drawable.btn_positive);
			binding.buttonCanthear.setBackgroundResource(R.drawable.btn_negative);
			binding.buttonHear.setTextColor(Color.WHITE);
			binding.buttonCanthear.setTextColor(Color.WHITE);
			tone_playing=true;
			if(RIGHTEAR)
				binding.rightSound.setVisibility(View.INVISIBLE);
			else
				binding.leftSound.setVisibility(View.INVISIBLE);
		}

	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("HearingTest", "onDestroy called");
		try {
			FrequencyGenerator.audioTrack.release();
			FrequencyGenerator.audioTrack = null;

		} catch (Exception e) {
			Log.e("HearingTest", "onDestroy error: " + e.toString());
		}
	}

	/**
	 * Method onPause when we pause activity we also stop any tones from playing
	 */
	@Override
	public void onPause() {
		super.onPause();
		Log.d("HearingTest", "onPause called");
		try {
			FrequencyGenerator.audioTrack.release();
			FrequencyGenerator.audioTrack = null;
			/*progressval=11.1;
			LEFTEAR=false;
			RIGHTEAR=true;*/
		} catch (Exception e) {
			Log.e("HearingTest", "onPause error: " + e.toString());

		}
	}


} // end of class