package com.example.tangdan.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tangdan.myapplication.R;
import com.example.tangdan.myapplication.base.BaseActivity;
import com.example.tangdan.myapplication.base.BaseAdapter;
import com.example.tangdan.myapplication.bean.Constants;
import com.example.tangdan.myapplication.bean.StoreBean;

import java.util.ArrayList;

public class PeopleCom extends BaseActivity {
    private ListView mListView;
    private BaseAdapter mAdapter;
    private ArrayList<StoreBean> mStoreList;

    @Override
    public void initView() {
        mListView=findViewById(R.id.storeList);
        mAdapter=new BaseAdapter(this,new ArrayList<StoreBean>());
    }

    @Override
    public void initData() {
        mListView.setAdapter(mAdapter);
        mStoreList=null;
    }

    @Override
    public void initClickItem() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                navigateClick(position);
            }
        });
    }

    private void navigateClick(int position) {
        StoreBean storeBean = mStoreList.get(position);
        Intent intent = new Intent(PeopleCom.this, StoreDetailActivity.class);
        intent.putExtra(Constants.Store.STORE_NAME, storeBean.getmStoreName());
        intent.putExtra(Constants.Store.STORE_IMAGE, storeBean.getmStoreImage());
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
