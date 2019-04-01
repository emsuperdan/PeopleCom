package com.example.tangdan.myapplication.bean;

import cn.bmob.v3.BmobObject;

public class MyUser extends BmobObject {
    private String mUserAccount;
    private String mUserPassword;

    public String getmUserAccount() {
        return mUserAccount;
    }

    public void setmUserAccount(String mUserAccount) {
        this.mUserAccount = mUserAccount;
    }

    public String getmUserPassword() {
        return mUserPassword;
    }

    public void setmUserPassword(String mUserPassword) {
        this.mUserPassword = mUserPassword;
    }
}
