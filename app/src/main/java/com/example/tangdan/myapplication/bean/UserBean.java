package com.example.tangdan.myapplication.bean;

import cn.bmob.v3.BmobObject;

public class UserBean extends BmobObject {
    private String mCommentText;
    private int mScore;
    private String mUserName;
    private int mCommentImage;
    private StoreBean linkToStore;

//    public UserBean(String commenttext, String username, int commentImage, int score) {
//        this.mCommentText = commenttext;
//        this.mScore = score;
//        this.mUserName = username;
//        this.mCommentImage = commentImage;
//    }

    public void setmCommentImage(int mCommentImage) {
        this.mCommentImage = mCommentImage;
    }

    public void setmScore(int mScore) {
        this.mScore = mScore;
    }

    public void setmCommentText(String mCommentText) {
        this.mCommentText = mCommentText;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public int getmScore() {
        return mScore;
    }

    public String getmCommentText() {
        return mCommentText;
    }

    public String getmUserName() {
        return mUserName;
    }

    public int getmCommentImage() {
        return mCommentImage;
    }

    public StoreBean getLinkToStore() {
        return linkToStore;
    }

    public void setLinkToStore(StoreBean linkToStore) {
        this.linkToStore = linkToStore;
    }
}
