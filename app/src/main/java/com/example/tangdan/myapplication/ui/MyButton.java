package com.example.tangdan.myapplication.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class MyButton extends android.support.v7.widget.AppCompatButton{
    private OnClickCollectListener mCollectListener;
    private OnClickFilterListener mFilterListener;
    private String collectionObjectId;
    private String filterObjectId;
    private String mObjectId;

    public MyButton(Context context) {
        super(context, null);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCollectListener(OnClickCollectListener mListener, String objectId) {
        this.mCollectListener = mListener;
        this.collectionObjectId = objectId;
    }

    public void setFilterListener(OnClickFilterListener listener, String objectId) {
        this.mFilterListener = listener;
        this.filterObjectId = objectId;
    }

//    public void setCollectObjectId(String objectId){
//        this.collectionObjectId = objectId;
//    }
//
//    public void setFilterObjectId(String filterObjectId) {
//        this.filterObjectId = filterObjectId;
//    }

    @Override
    public void setOnClickListener(View.OnClickListener listener) {
        super.setOnClickListener(listener);
            if (mCollectListener!=null){
                mCollectListener.setCollectPosition(collectionObjectId);
            }
            if (filterObjectId!=null){
                mFilterListener.setFilterPosition(filterObjectId);
            }
    }

    public String getmObjectId() {
        return mObjectId;
    }

    public void setmObjectId(String mObjectId) {
        this.mObjectId = mObjectId;
    }


    interface OnClickCollectListener {
        void setCollectPosition(String objectId);
    }

    interface OnClickFilterListener {
        void setFilterPosition(String objectId);
    }
}


