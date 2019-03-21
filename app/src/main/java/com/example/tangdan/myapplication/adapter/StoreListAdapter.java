package com.example.tangdan.myapplication.adapter;

import android.content.Context;

import com.example.tangdan.myapplication.base.BaseAdapter;
import com.example.tangdan.myapplication.bean.StoreBean;

import java.util.ArrayList;

public class StoreListAdapter extends BaseAdapter {
    public StoreListAdapter(Context context, ArrayList<StoreBean> list) {
        super(context, list);
    }
}
