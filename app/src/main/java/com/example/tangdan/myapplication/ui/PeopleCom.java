package com.example.tangdan.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tangdan.myapplication.R;
import com.example.tangdan.myapplication.base.BaseActivity;
import com.example.tangdan.myapplication.base.BaseAdapter;
import com.example.tangdan.myapplication.bean.Constants;
import com.example.tangdan.myapplication.bean.StoreBean;
import com.example.tangdan.myapplication.helper.BmobDbHelper;

import java.util.ArrayList;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class PeopleCom extends BaseActivity {
    private static final String TAG = "PeopleCom";
    private ListView mListView;
    private BaseAdapter mAdapter;
    private ArrayList<StoreBean> mStoreList;
    private NavigationView mNavigationView;

    public void init() {
        mListView = findViewById(R.id.storeList);
        mNavigationView = findViewById(R.id.navigation_view);

        mStoreList=new ArrayList<>();
        mAdapter = new BaseAdapter(this, mStoreList);
        mListView.setAdapter(mAdapter);

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
        intent.putExtra(Constants.Store.STORE_OBJECT_ID,storeBean.getmCopyStoreId());
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BmobDbHelper.getInstance().init(this);
        init();
        refreshData();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                navigateClick(position);
            }
        });
    }
    private String[] objectIdArr ={"0VMTAAAo","2ShaMMMc","1EoHKKKW","FxztCCCD"};

    private void refreshData() {
        for (int i=0;i<4;i++){
            getBmobStoreNameById(objectIdArr[i]);
        }
    }

    private void getBmobStoreNameById(String Id){
        new BmobQuery<StoreBean>().getObject(Id, new QueryListener<StoreBean>() {
            @Override
            public void done(StoreBean storeBean, BmobException e) {
                if (e==null){
                    Log.d(TAG,"          "+storeBean.getmStoreName()+"     "+storeBean.getObjectId());
                    StoreBean bean=new StoreBean();
                    bean.setmStoreName(storeBean.getmStoreName());
                    bean.setmStoreImage(R.mipmap.ic_launcher);
                    bean.setmCopyStoreId(storeBean.getObjectId());
                    mStoreList.add(bean);
                    mAdapter.notifyDataSetChanged();
                }else {
                    Log.d(TAG,"查询失败"+e.getErrorCode());
                }
            }
        });
    }
}
