package com.example.tangdan.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
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
    private NavigationView mNavigationView;

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        mListView = findViewById(R.id.storeList);
        mNavigationView = findViewById(R.id.navigation_view);
        ArrayList<StoreBean> list=new ArrayList<>();
        mAdapter=null;
        list.add(new StoreBean("商家一",R.drawable.ic_launcher_background));
        list.add(new StoreBean("商家二",R.drawable.ic_launcher_background));
        list.add(new StoreBean("商家三",R.drawable.ic_launcher_background));
        list.add(new StoreBean("商家四",R.drawable.ic_launcher_background));
        mAdapter = new BaseAdapter(this, list);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void initClickItem() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                navigateClick(position);
            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.main_user_account:
                        Intent intent = new Intent(PeopleCom.this, UserAccountTextViewActivity.class);
                        intent.putExtra(Constants.Store.USER_ACCOUNT, "账户ming");
                        intent.putExtra(Constants.Store.USER_PASSWORD, "密码");
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return true;
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
        initView();
        initData();
        initClickItem();
    }
}
