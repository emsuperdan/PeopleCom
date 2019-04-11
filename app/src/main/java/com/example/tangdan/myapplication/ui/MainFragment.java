package com.example.tangdan.myapplication.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tangdan.myapplication.R;
import com.example.tangdan.myapplication.base.BaseAdapter;
import com.example.tangdan.myapplication.bean.StoreBean;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.QueryListener;

import static com.example.tangdan.myapplication.bean.Constants.Register.USER_ACCOUNT_REGISTER;
import static com.example.tangdan.myapplication.bean.Constants.Register.USER_PASSWORD_REGISTER;
import static com.example.tangdan.myapplication.bean.Constants.Store.STORE_IMAGE;
import static com.example.tangdan.myapplication.bean.Constants.Store.STORE_NAME;
import static com.example.tangdan.myapplication.bean.Constants.Store.STORE_OBJECT_ID;

public class MainFragment extends Fragment {
    private static final String TAG ="MainFragment" ;
    private ListView mListView;
    private BaseAdapter mAdapter;
    private ArrayList<StoreBean> mStoreList;

    private String mAccount;
    private String mPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        refreshData();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                navigateClick(position);
            }
        });
    }

    private String[] objectIdArr = {"0VMTAAAo", "2ShaMMMc", "1EoHKKKW", "FxztCCCD"};

    private void refreshData() {
        for (int i = 0; i < 4; i++) {
            getBmobStoreNameById(objectIdArr[i]);
        }
    }

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
                }
            }
        });
    }

    private void init() {
        mListView = getActivity().findViewById(R.id.storeList);

        mStoreList = new ArrayList<>();
        mAdapter = new BaseAdapter(getActivity(), mStoreList);
        mListView.setAdapter(mAdapter);

        Intent intent = getActivity().getIntent();
        mAccount = intent.getStringExtra(USER_ACCOUNT_REGISTER);
        mPassword = intent.getStringExtra(USER_PASSWORD_REGISTER);
    }

    private void navigateClick(int position) {
        StoreBean storeBean = mStoreList.get(position);
        Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
        intent.putExtra(STORE_NAME, storeBean.getmStoreName());
        intent.putExtra(STORE_OBJECT_ID, storeBean.getmCopyStoreId());
        intent.putExtra(USER_ACCOUNT_REGISTER, mAccount);

        Bitmap bitmap = storeBean.getmStorePic();
//        intent.putExtra(STORE_IMAGE, storeBean.getmStorePic());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bitmapByte = baos.toByteArray();
        intent.putExtra(STORE_IMAGE, bitmapByte);

        startActivity(intent);
    }
}
