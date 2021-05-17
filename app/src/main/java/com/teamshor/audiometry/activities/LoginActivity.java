package com.teamshor.audiometry.activities;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.RequestQueue;

import com.android.volley.toolbox.Volley;
import com.teamshor.audiometry.R;
import com.teamshor.audiometry.databinding.ActivityLoginBinding;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "8ILiMw1Pxzc-fuIaBTybaB0sMLH0GAxdo8tyOOINOn";
        String id = String.format("%04d", new Random().nextInt(10000));

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG", getString(R.string.permission_not_granted));
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        }


        binding.activate.setOnClickListener(v ->{
                if (TextUtils.isEmpty(binding.idName.getText().toString())) {
                    binding.idName.setError("Invalid name");
                    return;
                }
            String phone_Num = "+918208179953";
            String send_msg = "Hey,"+binding.idName.getText().toString()+" has requested to open the app. OTP- "+id;
        try {
                SmsManager sms = SmsManager.getDefault(); // using android SmsManager
                sms.sendTextMessage(phone_Num, null, send_msg, null, null); // adding number and text
            binding.idName.setVisibility(View.INVISIBLE);
            binding.login.setVisibility(View.VISIBLE);
            binding.password.setVisibility(View.VISIBLE);
            binding.activate.setVisibility(View.GONE);
            } catch (Exception e) {
                Toast.makeText(this, "Sms not Send", Toast.LENGTH_SHORT).show();
                Log.d("TAG",e.getMessage());
            }
        });

        binding.login.setOnClickListener(v1 -> {
            String text= binding.password.getText().toString();
            if(text.equals(id))
            {
                startActivity(new Intent(LoginActivity.this, Intro_Signin.class));finish();

            }
            else
            {
                binding.password.setError("Input correct OTP");
            }
        });




    }
}