package com.example.tangdan.myapplication.base;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;

public abstract class BaseActivity extends Activity {
    public void initView() {
    }

    ;

    public void initData() {
    }

    ;

    public void initClickItem() {
    }

    ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initClickItem();
    }
}
