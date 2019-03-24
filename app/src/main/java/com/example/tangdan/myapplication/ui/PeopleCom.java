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
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class PeopleCom extends BaseActivity {
    private static final String TAG = "PeopleCom";
    private ListView mListView;
    private BaseAdapter mAdapter;
    private ArrayList<StoreBean> mStoreList;
    private NavigationView mNavigationView;

    public void init() {
        ArrayList<StoreBean> list = new ArrayList<>();
//        list.add(new StoreBean(getBmobStoreNameById("obQ2eeek"), R.drawable.ic_launcher_background));
//        list.add(new StoreBean(getBmobStoreNameById("wzAS999H"), R.drawable.ic_launcher_background));
//        list.add(new StoreBean(getBmobStoreNameById("jQfG777l"), R.drawable.ic_launcher_background));
//        list.add(new StoreBean(getBmobStoreNameById("cANF888B"), R.drawable.ic_launcher_background));
        mStoreList=list;
        mListView = findViewById(R.id.storeList);
        mNavigationView = findViewById(R.id.navigation_view);

        mAdapter = new BaseAdapter(this, mStoreList);
        mListView.setAdapter(mAdapter);


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
        BmobDbHelper.getInstance().init(this);
        init();
        getBmobStoreNameById();
    }

    private String mStorename =null;

    private void getBmobStoreNameById(){
        BmobQuery<StoreBean> query=new BmobQuery<>();
        query.findObjects(new FindListener<StoreBean>() {
            @Override
            public void done(List<StoreBean> list, BmobException e) {
                int n=list.size();
                for (int i=0;i<n;i++){
                    mStoreList.add(list.get(n));
                }
                mAdapter.notifyDataSetChanged();
            }
        });
//        new BmobQuery<StoreBean>().getObject(Id, new QueryListener<StoreBean>() {
//            @Override
//            public void done(StoreBean storeBean, BmobException e) {
//                if (e==null){
//                    Log.d(TAG,"          "+storeBean.getmStoreName());
//                    mStorename =storeBean.getmStoreName();
//                }else {
//                    Log.d(TAG,"查询失败");
//                }
//            }
//        });
    }
}
