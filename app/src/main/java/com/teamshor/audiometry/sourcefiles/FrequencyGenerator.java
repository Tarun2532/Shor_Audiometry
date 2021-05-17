/*
 * {This class is used to run methods based on what user chooses
 * 	for the Frequency Generator }
 *
 * @version Build {1.0} (14 May 2015)
 * @author Junaid Malik
 */
package com.teamshor.audiometry.sourcefiles;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.teamshor.audiometry.fragments.HearingTestFragment;

/**
 * A class used to generate simple sine wave pure tone signal
 * Class is based on Marble Mice: Generate And Play A Tone In Android
 * www.marblemice.blogspot.ie/2010/04/generate-and-play-tone-in-android.html
 */
public class FrequencyGenerator {

	public static CountDownTimer ts;
	public final static int duration = 1; // length of tone in seconds
	public static int sampleRate = 44100; // sample rate at 44,100 times per second
	public final static int numSamples = duration * sampleRate;// in order to avoid
	// distortion
	// number of samples =
	// duration multiplied
	// by sample rate
	public final static double[] sample = new double[numSamples];
	private static final String TAG = "Frequency";
	public static double frequencyOfTone; // frequency in Hz
	public final static byte[] generatedSound = new byte[2 * numSamples]; // array of
	// genSoun =
	// 2 times
	// the
	// number of
	// samples

	public static AudioTrack audioTrack = null; // null object of type audioTrack

	public void genTone() {
		// fill out the array with samples
		for (int counter = 0; counter < numSamples; ++counter) {
			sample[counter] = Math.sin(2 * Math.PI * counter // x(t)=A Sin 2pi n
					// fa/fs
					/ (sampleRate / frequencyOfTone));
			//Log.d(TAG,"inside forLoop()");
		}
		// convert to 16 bit pcm sound array
		// assumes the sample buffer is normalised.
		int index = 0;
		// enhanced for loop going through sampa array
		for (final double doubleValue : sample) {
			// scale to maximum amplitude of 32,767
			final short value = (short) ((doubleValue * 32767));
			// in 16 bit wav PCM, first byte is the low order byte
			// 0xff00 is hex literal for the value 65,280 bitwise
			// it resets the rightmost 8 bits
			// operation, right shift to 8bits
			// 0x00ff only the rightmost 8 bits are kept
			// value is anded with 0x00FF
			// Anding with 8 zeros we are masking off 8 right MSB
			generatedSound[index++] = (byte) (value & 0x00ff);
			generatedSound[index++] = (byte) ((value & 0xff00) >>> 8);
			//Log.d(TAG,"inside foreach()");

		}
	}

	/**
	 * Audiotrack plays pcm audio buffers data is pushed to audiotrack object in
	 * streaming mode the system writes continous data using write() method this
	 * method takes three params audioData, offfsetinBYtes and sizeinBytes
	 */
	public void playSound(float DB) {
		if (audioTrack != null) {
			audioTrack.stop();
			audioTrack.release();
		}
		audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate,
				AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
				generatedSound.length, AudioTrack.MODE_STATIC);
		audioTrack.write(generatedSound, 0, generatedSound.length);
		audioTrack.getChannelConfiguration();
		//Log.d(TAG,"inside playSound()");

		try {
				float str= (float) (0.00005* Math.pow(10,((DB -5)/20)));

				if(HearingTestFragment.RIGHTEAR)
				{
					playTone(0,str);
					Log.d(TAG,"RIGHT");
				}

				else
				{
					playTone(str,0);
					Log.d(TAG,"LEFT");

				}



		} catch (Exception e) { // error message if not playable
			//Toast.makeText(getApplicationContext(), "Error playing audio",
			//Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Method plays tone at highest volume
	 */
	public void playTone() {
		playTone(0.1f, 0.1f); // this method plays tone with full
		// volume in both ear
	}


	public void playTone( float left, float right) { // overloaded playTone
		// method with volume
		// determined by user
		try {
			audioTrack.write(generatedSound, 0, generatedSound.length);// pass
			// genSound
			// into
			// write
			// method
			audioTrack.setStereoVolume(left, right); // left and
			// right
			// are set
			// by
			// user

			//audioTrack.setDualMonoMode(AudioTrack.DUAL_MONO_MODE_LL);
			audioTrack.play();
			//audioTrack.setStereoVolume(left, right);


			Log.d(TAG, "inside playtone( , )");
			stop();




				ts=new CountDownTimer(2000, 1000) {

				public void onTick(long millisUntilFinished) {
					if(millisUntilFinished <1000f)
						HearingTestFragment.initbuttons();
				}

				public void onFinish() {
					try{
						audioTrack.stop();
					}
					catch(NullPointerException e)
					{
						Toast.makeText(HearingTestFragment.activity, "Unexpected Error", Toast.LENGTH_SHORT).show();
					}

				}
			}.start();
			Log.d(TAG,"INSTANCE");

		} catch (Exception e) { // error message if not playable

			Log.d(TAG,"hua kya-------------");

		}
	}

	/**
	 * Method takes int value that is associated with selection
	 *
	 * @param earSelect
	 */


	/**
	 * Stops the audiotrack
	 */
	public void stop() {
		Log.d(TAG, "going stop(ts)");

		if (ts != null) {
			Log.d(TAG, "inside stop(ts)");
			ts.cancel();

		}
	}


}
