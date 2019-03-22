package com.example.tangdan.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.tangdan.myapplication.R;
import com.example.tangdan.myapplication.base.BaseActivity;
import com.example.tangdan.myapplication.bean.Constants;

public class UserAccountTextViewActivity extends BaseActivity {
    private TextView mUserAccount;
    private TextView mUserPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_user_account_textview);
        init();
    }

    public void init() {
        mUserAccount = findViewById(R.id.navigation_user_account);
        mUserPassword = findViewById(R.id.navigation_user_password);

        Intent intent = getIntent();
        mUserAccount.setText(intent.getStringExtra(Constants.Store.USER_ACCOUNT));
        mUserPassword.setText(intent.getStringExtra(Constants.Store.USER_PASSWORD));
    }
}
