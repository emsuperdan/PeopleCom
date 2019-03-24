package com.example.tangdan.myapplication.helper;

import android.content.Context;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

public class BmobDbHelper {
    public static final String APPLICATION_ID= "8e3bea0f25c7a57b55c15e9e14384de1";

    private BmobDbHelper(){

    }

    private static class Holder{
        private static final BmobDbHelper bmobDbHelper = new BmobDbHelper();
    }

    public static BmobDbHelper getInstance(){
        return Holder.bmobDbHelper;
    }

    public void init(Context context) {
        BmobConfig config = new BmobConfig.Builder(context).setApplicationId(APPLICATION_ID).build();
        Bmob.initialize(config);
    }
}
