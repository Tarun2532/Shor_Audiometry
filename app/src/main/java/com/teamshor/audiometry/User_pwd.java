package com.teamshor.audiometry;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.auth.User;
import com.teamshor.audiometry.activities.Intro_Signin;
import com.teamshor.audiometry.databinding.ActivityLoginBinding;
import com.teamshor.audiometry.databinding.ActivityUserPwdBinding;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;

public class User_pwd extends AppCompatActivity {

    ActivityUserPwdBinding binding;
    final String[] str =new String[2];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserPwdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getpwd();



        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.username.getText().toString()) ) {
                    binding.username.setError("Enter username");
                    return;
                }
                if (TextUtils.isEmpty(binding.password.getText().toString())) {
                    binding.password.setError("Enter username");
                    return;
                }
                if(binding.password.getText().toString().equals(str[1]))
                {
                    savedata();
                    startActivity(new Intent(User_pwd.this, Intro_Signin.class));finish();

                }
                else
                {
                    binding.password.setError("Invalid username/password");
                }

            }
        });

    }
    public void getUsername()
    {
        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Users").document("entries")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                str[0]=documentSnapshot.getString("username");
            }
        });
    }
    public void getpwd()
    {
        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Users").document("entries")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                str[1]=documentSnapshot.getString("pwd");
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void savedata(){
        String reqString = Build.MANUFACTURER
                + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                + " " + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();
        HashMap<String, Object> map = new HashMap<>();

        long epoch = Instant.now().toEpochMilli();
        LocalDateTime ldt = Instant.ofEpochMilli(epoch)
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        String TandD=String.valueOf(ldt);
        map.put("Username",binding.username.getText().toString());
        map.put("Time and Date",TandD);
        map.put("Device Name:-",reqString);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .document()
                .set(map, SetOptions.merge());

    }
}

