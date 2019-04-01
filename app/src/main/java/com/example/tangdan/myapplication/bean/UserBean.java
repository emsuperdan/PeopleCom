package com.example.tangdan.myapplication.bean;

import android.graphics.Bitmap;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;


//只有集成BmobUser 才能用手机号来注册 ，，，，，，但是也可以不用手机号，用户直接用假造的账号密码字符串注册，这样也是可以的

public class UserBean extends BmobObject {
    private String mCommentText;
    private int mScore;
    private String mUserAccount;
    private String mUserPassword;
    private StoreBean linkToStore;
//    private MyUser mLinkToUserAccount;
    private BmobFile mPicUrl;
    private Bitmap picDecodeUrl;

    public void setmScore(int mScore) {
        this.mScore = mScore;
    }

    public void setmCommentText(String mCommentText) {
        this.mCommentText = mCommentText;
    }

    public void setmUserAccount(String mUserAccount) {
        this.mUserAccount = mUserAccount;
    }

    public int getmScore() {
        return mScore;
    }

    public String getmCommentText() {
        return mCommentText;
    }

    public String getmUserAccount() {
        return mUserAccount;
    }

    public StoreBean getLinkToStore() {
        return linkToStore;
    }

    public void setLinkToStore(StoreBean linkToStore) {
        this.linkToStore = linkToStore;
    }

    public String getmUserPassword() {
        return mUserPassword;
    }

    public void setmUserPassword(String mUserPassword) {
        this.mUserPassword = mUserPassword;
    }

    public BmobFile getmPicUrl() {
        return mPicUrl;
    }

    public void setmPicUrl(BmobFile mPicUrl) {
        this.mPicUrl = mPicUrl;
    }

    public Bitmap getPicDecodeUrl() {
        return picDecodeUrl;
    }

    public void setPicDecodeUrl(Bitmap picDecodeUrl) {
        this.picDecodeUrl = picDecodeUrl;
    }

//    public MyUser getmLinkToUserAccount() {
//        return mLinkToUserAccount;
//    }
//
//    public void setmLinkToUserAccount(MyUser mLinkToUserAccount) {
//        this.mLinkToUserAccount = mLinkToUserAccount;
//    }
}
