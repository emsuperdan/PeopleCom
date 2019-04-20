package com.example.tangdan.myapplication.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tangdan.myapplication.R;

public class MyDialog extends Dialog implements View.OnClickListener {
    private onItemLongPressClickListener onItemLongPressClickListener;
    private Button mCollectBtn;
    private Button mFilterBtn;

    public MyDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_item_longpress);
        mCollectBtn=findViewById(R.id.btn_collect);
        mFilterBtn=findViewById(R.id.btn_filter);

        mCollectBtn.setOnClickListener(this);
        mFilterBtn.setOnClickListener(this);
    }

    public void setOnItemLongPressClickListener(onItemLongPressClickListener listener){
        this.onItemLongPressClickListener=listener;

    }

    @Override
    public void onClick(View v) {
        onItemLongPressClickListener.itemLongPressClick(v);
    }


    interface onItemLongPressClickListener{
        void itemLongPressClick(View v);
    }
}
