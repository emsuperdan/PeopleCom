package com.example.tangdan.myapplication.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tangdan.myapplication.R;
import com.example.tangdan.myapplication.bean.Constants;
import com.example.tangdan.myapplication.bean.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static com.example.tangdan.myapplication.bean.Constants.Register.USER_ACCOUNT_REGISTER;
import static com.example.tangdan.myapplication.bean.Constants.Register.USER_PASSWORD_REGISTER;
import static com.example.tangdan.myapplication.bean.Constants.Store.USER_OBJECT_ID;

public class SettingFragment extends Fragment implements View.OnClickListener {
    private final static String TAG ="SettingFragment";
    private TextView mShowAccountText;
    private TextView mAccountExit;
    private TextView mCollectionText;
    private TextView mHelpText;
    private TextView mAboutText;
    private TextView mChangePasswordText;

    private String mAccount;
    private String mPassword;
    private String mObjectId;

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
        mCollectionText = getActivity().findViewById(R.id.txt_setting_collection);
        mHelpText = getActivity().findViewById(R.id.txt_setting_help);
        mAboutText = getActivity().findViewById(R.id.txt_setting_about);
        mChangePasswordText=getActivity().findViewById(R.id.txt_change_password);

        mShowAccountText.setOnClickListener(this);
        mAccountExit.setOnClickListener(this);
        mCollectionText.setOnClickListener(this);
        mHelpText.setOnClickListener(this);
        mAboutText.setOnClickListener(this);
        mChangePasswordText.setOnClickListener(this);

        Intent intent = getActivity().getIntent();
        mAccount = intent.getStringExtra(USER_ACCOUNT_REGISTER);
        mPassword = intent.getStringExtra(USER_PASSWORD_REGISTER);
        mObjectId=intent.getStringExtra(USER_OBJECT_ID);
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
            case R.id.txt_change_password:
                View view=LayoutInflater.from(getActivity()).inflate(R.layout.dialog_modify_password,null);
                final AlertDialog dialog=new AlertDialog.Builder(getActivity()).create();
                dialog.setView(view);
                dialog.show();
                final EditText editText=view.findViewById(R.id.edt_modify_password);
                Button button=view.findViewById(R.id.btn_modify_confirm);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (!TextUtils.isEmpty(editText.getText().toString())){
                            MyUser myUser=new MyUser();
                            myUser.setmUserPassword(editText.getText().toString());
                            myUser.update(mObjectId,new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e==null){
                                        Toast.makeText(getActivity(),"修改密码成功！",Toast.LENGTH_SHORT).show();
                                    }else {
                                        Log.d(TAG,"错误代码:"+e.getErrorCode());
                                        Toast.makeText(getActivity(),"阿欧，好像网络不行",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(getActivity(),"输入的密码不能为空！",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.txt_user_exit:
                Intent intent1 = new Intent(getActivity(), WelcomePage.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                break;
            case R.id.txt_setting_collection:
                Intent intent2=new Intent(getActivity(),StoreCollectionActivity.class);
                startActivity(intent2);
                break;
            case R.id.txt_setting_help:
                break;
            case R.id.txt_setting_about:
                break;
            default:
                break;
        }
    }
}
