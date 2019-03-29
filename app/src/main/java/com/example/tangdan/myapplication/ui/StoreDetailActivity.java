package com.example.tangdan.myapplication.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tangdan.myapplication.R;
import com.example.tangdan.myapplication.adapter.UserComAdapter;
import com.example.tangdan.myapplication.base.BaseActivity;
import com.example.tangdan.myapplication.bean.Constants;
import com.example.tangdan.myapplication.bean.StoreBean;
import com.example.tangdan.myapplication.bean.UserBean;
import com.example.tangdan.myapplication.helper.BmobDbHelper;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class StoreDetailActivity extends BaseActivity {
    private static final String TAG = "StoreDetailActivity";
    private ListView mListView;
    private ArrayList<UserBean> mUserList;
    private UserComAdapter mUserAdapter;
    private TextView mStoreDetailName;
    private ImageView mStoreDetailImage;
    private EditText mDetailComEdit;
    private EditText mDetailScoreEdit;
    private Button mSendButton;
    private UserBean mUserBean;
    private StoreBean mStoreBean;
    private String mStoreObjectId;
    private SwipeRefreshLayout mSwipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_store);
        BmobDbHelper.getInstance().init(this);
        init();
        queryData(mStoreObjectId);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mDetailComEdit.getText().toString()) &&
                        !TextUtils.isEmpty(mDetailScoreEdit.getText().toString()) &&
                        isInteger(mDetailScoreEdit.getText().toString())) {
                    String comment = mDetailComEdit.getText().toString();
                    int score = Integer.valueOf(mDetailScoreEdit.getText().toString());

                    mUserBean.setmCommentText(comment);
                    mUserBean.setmScore(score);
                    mStoreBean.setObjectId(mStoreObjectId);
                    mUserBean.setLinkToStore(mStoreBean);
                    mUserBean.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Log.d(TAG, s + "      " + "上传成功");
                                Toast.makeText(StoreDetailActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                                mDetailComEdit.setText("");
                                mDetailScoreEdit.setText("");
                            } else {
                                Log.d(TAG, "上传失败" + s);
                            }
                        }
                    });
                } else {
                    Toast.makeText(StoreDetailActivity.this, "输入有误请重新输入", Toast.LENGTH_SHORT).show();
                    mDetailComEdit.setText("");
                    mDetailScoreEdit.setText("");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mUserList.clear();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        queryData(mStoreObjectId);
                        mSwipe.setRefreshing(false);
                    }
                },1500);
            }
        });
    }

    private boolean isInteger(String str) {
        if (str.length() > 1) {
            return false;
        }
        try {
            Integer.valueOf(str);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void queryData(String StoreBeanId) {
        mStoreBean.setObjectId(StoreBeanId);
        new BmobQuery<UserBean>().addWhereEqualTo("linkToStore",
                new BmobPointer(mStoreBean)).findObjects(new FindListener<UserBean>() {
            @Override
            public void done(List<UserBean> list, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "查询成功");
                    for (int i = 0; i < list.size(); i++) {
                        mUserList.add(list.get(i));
                    }
                    mUserAdapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "    " + "查询失败");
                }
            }
        });
    }

    public void init() {
        mListView = findViewById(R.id.user_com_list);
        mStoreDetailImage = findViewById(R.id.detail_storeimage);
        mStoreDetailName = findViewById(R.id.detail_storename);
        mDetailComEdit = findViewById(R.id.edit_detail_com);
        mDetailScoreEdit = findViewById(R.id.edit_detail_score);
        mSendButton = findViewById(R.id.detail_send);
        mSwipe=findViewById(R.id.swipe);

        Intent intent = getIntent();
        mStoreDetailName.setText(intent.getStringExtra(Constants.Store.STORE_NAME));
        mStoreDetailImage.setImageResource(intent.getIntExtra(Constants.Store.STORE_IMAGE, R.mipmap.ic_launcher));
        mStoreObjectId = intent.getStringExtra(Constants.Store.STORE_OBJECT_ID);

        mUserList = new ArrayList<>();
        mUserAdapter = new UserComAdapter(this, mUserList);
        mListView.setAdapter(mUserAdapter);
        mSwipe.setColorSchemeColors(Color.YELLOW);

        mUserBean = new UserBean();
        mStoreBean = new StoreBean();
    }
}
