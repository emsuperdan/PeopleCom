package com.example.tangdan.myapplication.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;


// 若想改类获取objectId,createAt，可直接构造类的对象调用getObjectId和其他方法来获取即可，如在Bean类中重复声明会返回errorcode
public class StoreBean extends BmobObject {
    private String mStoreName;
    private int mStoreImage;
    private BmobRelation mapToUser;
    private String mCopyStoreId;

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

    public BmobRelation getMapToUser() {
        return mapToUser;
    }

    public void setMapToUser(BmobRelation mapToUser) {
        this.mapToUser = mapToUser;
    }

    public String getmCopyStoreId() {
        return mCopyStoreId;
    }

    public void setmCopyStoreId(String mCopyStoreId) {
        this.mCopyStoreId = mCopyStoreId;
    }
}
