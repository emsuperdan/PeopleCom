package com.example.tangdan.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.tangdan.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;

public class NewWelcomePage extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_welcome);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //设置为全屏

        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(NewWelcomePage.this,WelcomePage.class);
                startActivity(intent);
                finish();
            }
        };
        Timer timer=new Timer();
        timer.schedule(task,1500);
    }
}
