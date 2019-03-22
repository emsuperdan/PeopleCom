package com.example.tangdan.myapplication.bean;

public class UserBean {
    private String mCommentText;
    private int mScore;
    private String mUserName;
    private int mCommentImage;

    public UserBean(String commenttext, String username, int commentImage, int score) {
        this.mCommentText = commenttext;
        this.mScore = score;
        this.mUserName = username;
        this.mCommentImage = commentImage;
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
}
