package com.example.tangdan.myapplication.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tangdan.myapplication.R;
import com.example.tangdan.myapplication.base.BaseActivity;
import com.example.tangdan.myapplication.bean.Constants;
import com.example.tangdan.myapplication.bean.MyUser;
import com.example.tangdan.myapplication.bean.UserBean;
import com.example.tangdan.myapplication.helper.BmobDbHelper;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisterPage extends BaseActivity {
    private static final String TAG = "RegisterPage";
    private EditText mEditRegisterAccount;
    private EditText mEditRegisterPassword;
    private Button mBtnRegisterConfirm;
    private UserBean mUserBean;
    private MyUser mMyUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        BmobDbHelper.getInstance().init(this);
        init();
    }

    private void init() {
        mEditRegisterAccount = findViewById(R.id.edit_register_account);
        mEditRegisterPassword = findViewById(R.id.edit_register_password);
        mBtnRegisterConfirm = findViewById(R.id.btn_register_confirm);
        mUserBean = new UserBean();
        mMyUser = new MyUser();

        mBtnRegisterConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mEditRegisterAccount.getText().toString()) &&
                        !TextUtils.isEmpty(mEditRegisterPassword.getText().toString())) {
                    final String account = mEditRegisterAccount.getText().toString();
                    final String password = mEditRegisterPassword.getText().toString();

                    BmobQuery<MyUser> query = new BmobQuery<>();
                    query.addWhereEqualTo("mUserAccount", account).findObjects(new FindListener<MyUser>() {
                        @Override
                        public void done(List<MyUser> list, BmobException e) {
                            if (e == null) {
                                if (list.size() == 0) {
                                    mMyUser.setmUserAccount(account);
                                    mMyUser.setmUserPassword(password);
                                    mMyUser.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if (e == null) {
                                                Log.d(TAG, "云端保存用户账号和密码成功");
                                                Log.d(TAG, "mMyUser.getObjectId()" + "  :" + mMyUser.getObjectId());
                                                Toast.makeText(RegisterPage.this,"注册成功！",Toast.LENGTH_SHORT).show();
//                                                Intent intent=new Intent(RegisterPage.this,WelcomePage.class);
//                                                intent.putExtra(USER_ACCOUNT_REGISTER,s);
//                                                RegisterPage.this.setResult(RESULT_SEND,intent);
                                            } else {
                                                Log.d(TAG, "云端保存用户账号和密码失败" + e.getErrorCode());
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(RegisterPage.this, "云端中该账号已注册！", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Log.d(TAG,"云端保存时出现错误"+e.getErrorCode());
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterPage.this, "输入的账号或者密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
