package com.example.tangdan.myapplication.bean;

public class StoreBean {
    private String mStoreName;
    private int mStoreImage;

    public StoreBean(String storeName, int storeImage) {
        this.mStoreImage = storeImage;
        this.mStoreName = storeName;
    }

    public int getmStoreImage() {
        return mStoreImage;
    }

    public String getmStoreName() {
        return mStoreName;
    }
}
