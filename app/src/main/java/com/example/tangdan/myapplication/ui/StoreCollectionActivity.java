package com.example.tangdan.myapplication.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tangdan.myapplication.R;
import com.example.tangdan.myapplication.base.BaseActivity;
import com.example.tangdan.myapplication.base.BaseAdapter;
import com.example.tangdan.myapplication.bean.StoreBean;
import com.example.tangdan.myapplication.helper.BmobDbHelper;

import java.util.ArrayList;
import java.util.Set;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.QueryListener;

import static com.example.tangdan.myapplication.bean.Constants.SharedPreference.SHAREDPREFERENCE_NAME;
import static com.example.tangdan.myapplication.bean.Constants.SharedPreference.STORE_COLLECTION;

public class StoreCollectionActivity extends BaseActivity {
    private static final String TAG = "StoreCollectionActivity";
    private ArrayList<StoreBean> mStoreList;
    private TextView textView;
    private BaseAdapter mAdapter;
    private ListView mListView;
    private Set<String> mHashSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storecollection);
        BmobDbHelper.getInstance().init(this);
        init();
        getSharedPreferencesData();
        refreshData();

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final StoreBean storeBean = mStoreList.get(position);

                View deleteView = LayoutInflater.from(StoreCollectionActivity.this).inflate(R.layout.dialog_delete_collection, null);
                final AlertDialog dialog = new AlertDialog.Builder(StoreCollectionActivity.this).create();
                dialog.setView(deleteView);
                dialog.show();

                Button deleteBtn = deleteView.findViewById(R.id.btn_delete_fromcollection);
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        SharedPreferences sp = getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        Set<String> mHashSet = sp.getStringSet(STORE_COLLECTION, null);
                        mHashSet.remove(storeBean.getmCopyStoreId());
                        editor.clear();
                        editor.putStringSet(STORE_COLLECTION, mHashSet);
                        editor.commit();
                        getSharedPreferencesData();
                        refreshData();
                        Toast.makeText(StoreCollectionActivity.this, "成功从收藏中删除！", Toast.LENGTH_SHORT).show();
                    }
                });

                return true;
            }
        });
    }

    private void getSharedPreferencesData() {
        SharedPreferences sp = getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        mHashSet = sp.getStringSet(STORE_COLLECTION, null);
    }

    private void init() {
        mListView = findViewById(R.id.list_collection);
        textView = findViewById(R.id.txt_empty_list);

        mStoreList = new ArrayList<>();
        mAdapter = new BaseAdapter(this, mStoreList);
        mListView.setAdapter(mAdapter);
    }

    private void refreshData() {
//        for (int i = 0; i < mCollectedObjectId.length; i++) {
//            getBmobStoreNameById(mCollectedObjectId[i]);
//        }
        if (mHashSet == null || mHashSet.size() == 0) {
            mListView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        } else {
            mListView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            mStoreList.clear();
            for (String s : mHashSet) {
                getBmobStoreNameById(s);
            }
        }
    }

//    private String[] mCollectedObjectId = {"0VMTAAAo", "2ShaMMMc", "1EoHKKKW", "FxztCCCD"};

    private void getBmobStoreNameById(String Id) {
        BmobQuery<StoreBean> query = new BmobQuery<>();
        query.getObject(Id, new QueryListener<StoreBean>() {
            @Override
            public void done(StoreBean storeBean, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "          " + storeBean.getmStoreName() + "     " + storeBean.getObjectId());
                    final StoreBean bean = new StoreBean();
                    bean.setmStoreName(storeBean.getmStoreName());
                    bean.setmCopyStoreId(storeBean.getObjectId());
                    mStoreList.add(bean);
                    mAdapter.notifyDataSetChanged();

                    BmobFile file = storeBean.getmStorePicUri();
                    if (file != null) {
                        file.download(new DownloadFileListener() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Log.d(TAG, "商家图片下载成功" + s);
                                    bean.setmStorePic(BitmapFactory.decodeFile(s));
                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    Log.d(TAG, "商家图片下载失败" + e.getErrorCode());
                                }
                            }

                            @Override
                            public void onProgress(Integer integer, long l) {

                            }
                        });
                    }
                } else {
                    Log.d(TAG, "查询失败" + e.getErrorCode());
                    Toast.makeText(StoreCollectionActivity.this,"阿欧，好像网络不行",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
