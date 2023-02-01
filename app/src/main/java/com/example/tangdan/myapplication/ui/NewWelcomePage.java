package com.example.tangdan.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.WindowManager;

import com.example.tangdan.myapplication.R;
import com.example.tangdan.myapplication.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class NewWelcomePage extends BaseActivity {
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
