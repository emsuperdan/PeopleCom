package com.example.tangdan.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tangdan.myapplication.R;
import com.example.tangdan.myapplication.bean.Constants;

import static com.example.tangdan.myapplication.bean.Constants.Register.USER_ACCOUNT_REGISTER;
import static com.example.tangdan.myapplication.bean.Constants.Register.USER_PASSWORD_REGISTER;

public class SettingFragment extends Fragment implements View.OnClickListener {
    private TextView mShowAccountText;
    private TextView mAccountExit;

    private String mAccount;
    private String mPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        mShowAccountText = getActivity().findViewById(R.id.txt_show_account);
        mAccountExit = getActivity().findViewById(R.id.txt_user_exit);

        mShowAccountText.setOnClickListener(this);
        mAccountExit.setOnClickListener(this);

        Intent intent = getActivity().getIntent();
        mAccount = intent.getStringExtra(USER_ACCOUNT_REGISTER);
        mPassword = intent.getStringExtra(USER_PASSWORD_REGISTER);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_show_account:
                Intent intent = new Intent(getActivity(), UserAccountTextViewActivity.class);
                intent.putExtra(Constants.Store.USER_ACCOUNT, mAccount);
                intent.putExtra(Constants.Store.USER_PASSWORD, mPassword);
                startActivity(intent);
                break;
            case R.id.txt_user_exit:
                Intent intent1 = new Intent(getActivity(), WelcomePage.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }
}
