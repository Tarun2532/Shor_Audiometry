package com.teamshor.audiometry.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;
import com.teamshor.audiometry.R;
import com.teamshor.audiometry.sourcefiles.ViewAdapter;

public class Intro_Signin extends AppCompatActivity {
    ViewPager viewPager;
    WormDotsIndicator dot;
    ViewAdapter viewAdapter;
    public static Button btn;
    public static CheckBox checkBox;
    SharedPreferences prefs;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slides);
        viewPager= findViewById(R.id.view_pager);
        dot= findViewById(R.id.dot);
        viewAdapter= new ViewAdapter(Intro_Signin.this);
        viewPager.setAdapter(viewAdapter);
        dot.setViewPager(viewPager);
        prefs = this.getSharedPreferences(
                "SHOR", Context.MODE_PRIVATE);

        btn= findViewById(R.id.btn_disclaimer);
        checkBox=findViewById(R.id.check_understand);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().putString("OPENED", "true").apply();
                startActivity(new Intent(Intro_Signin.this, TestActivity.class));
            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked())
                {
                    btn.setClickable(true);
                    btn.setBackgroundResource(R.drawable.btn_start);
                }
                else
                {
                    btn.setClickable(false);
                    btn.setBackgroundResource(R.drawable.btn_start_no);

                }
            }
        });

    }
    public static void position4(boolean check)
    {
        if(check)
        {
            btn.setVisibility(View.VISIBLE);
            checkBox.setVisibility(View.VISIBLE);
        }
        else
        {
            btn.setVisibility(View.INVISIBLE);
            checkBox.setVisibility(View.INVISIBLE);
        }

    }

}
