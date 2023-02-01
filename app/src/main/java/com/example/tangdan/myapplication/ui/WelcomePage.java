package com.example.tangdan.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tangdan.myapplication.R;
import com.example.tangdan.myapplication.base.BaseActivity;
import com.example.tangdan.myapplication.bean.MyUser;
import com.example.tangdan.myapplication.helper.BmobDbHelper;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.example.tangdan.myapplication.bean.Constants.Register.USER_ACCOUNT_REGISTER;
import static com.example.tangdan.myapplication.bean.Constants.Register.USER_PASSWORD_REGISTER;
import static com.example.tangdan.myapplication.bean.Constants.Store.USER_OBJECT_ID;

public class WelcomePage extends BaseActivity {
    private static final String TAG = "WelcomePage";
    private EditText mEditAccountText;
    private EditText mEditPasswordText;
    private Button mLoginButton;
    private Button mRegisterButton;
    private TextView textView;

    private MyUser mMyUser;
    private String mAccount;
    private String mPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        BmobDbHelper.getInstance().init(this);
        init();
    }

    private void init() {
        mEditAccountText = findViewById(R.id.edit_account);
        mEditPasswordText = findViewById(R.id.edit_password);
        mLoginButton = findViewById(R.id.btn_login);
        mRegisterButton = findViewById(R.id.btn_register);
//        textView=findViewById(R.id.txt_forget_password);

        mMyUser = new MyUser();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mEditAccountText.getText().toString()) &&
                        !TextUtils.isEmpty(mEditPasswordText.getText().toString())) {
                    mAccount = mEditAccountText.getText().toString();
                    mPassword = mEditPasswordText.getText().toString();

                    //进行账号密码验证
                    BmobQuery<MyUser> query = new BmobQuery<>();
                    query.addWhereEqualTo("mUserAccount", mAccount).findObjects(new FindListener<MyUser>() {
                        @Override
                        public void done(List<MyUser> list, BmobException e) {
                            if (e == null) {
                                Log.d(TAG, "查询云端成功");
                                if (list.size() == 0) {
                                    Log.d(TAG, "云端中无此账号存在");
                                    Toast.makeText(WelcomePage.this, "账号或密码错误！", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d(TAG, "云端中有此账号");
                                    mMyUser = list.get(0);
                                    if (!mMyUser.getmUserPassword().equals(mPassword)) {
                                        Log.d(TAG, "密码错误");
                                        Toast.makeText(WelcomePage.this, "密码错误请重试！", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d(TAG, "登录成功");
                                        Toast.makeText(WelcomePage.this, "登录成功！", Toast.LENGTH_SHORT).show();
                                        mAccount = mMyUser.getmUserAccount();
                                        mPassword = mMyUser.getmUserPassword();
                                        Intent intent = new Intent(WelcomePage.this, PeopleCom.class);
                                        intent.putExtra(USER_ACCOUNT_REGISTER, mAccount);
                                        intent.putExtra(USER_PASSWORD_REGISTER, mPassword);
                                        intent.putExtra(USER_OBJECT_ID,mMyUser.getObjectId());
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            } else {
                                Toast.makeText(WelcomePage.this,"阿欧，好像网络不行",Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "查询云端失败" + e.getErrorCode());
                            }
                        }
                    });
                } else {
                    Toast.makeText(WelcomePage.this, "账号或密码不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomePage.this, RegisterPage.class);
//                startActivityForResult(intent, RESULT_GET);
                startActivity(intent);
            }
        });

//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_GET && requestCode == RESULT_SEND && data != null) {
//            mUserAccount = data.getStringExtra(USER_ACCOUNT_REGISTER);
//        }
//    }
}
