package com.example.tangdan.myapplication.bean;

import cn.bmob.v3.BmobObject;

public class StoreBean extends BmobObject {
    private String mStoreName;
    private int mStoreImage;

    public StoreBean(String storeName, int storeImage) {
        this.mStoreImage = storeImage;
        this.mStoreName = storeName;
    }

    public void setmStoreName(String mStoreName) {
        this.mStoreName = mStoreName;
    }

    public void setmStoreImage(int mStoreImage) {
        this.mStoreImage = mStoreImage;
    }

    public int getmStoreImage() {
        return mStoreImage;
    }

    public String getmStoreName() {
        return mStoreName;
    }
}
