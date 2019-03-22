package com.example.tangdan.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tangdan.myapplication.R;
import com.example.tangdan.myapplication.adapter.UserComAdapter;
import com.example.tangdan.myapplication.base.BaseActivity;
import com.example.tangdan.myapplication.bean.Constants;
import com.example.tangdan.myapplication.bean.UserBean;

import java.util.ArrayList;

public class StoreDetailActivity extends BaseActivity {
    private ListView mListView;
    private ArrayList<UserBean> mUserList;
    private UserComAdapter mUserAdapter;
    private TextView mStoreDetailName;
    private ImageView mStoreDetailImage;
    private EditText mDetailComEdit;
    private EditText mDetailScoreEdit;
    private Button mSendButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_store);
        init();
    }

    public void init() {
        mListView = findViewById(R.id.user_com_list);
        mStoreDetailImage = findViewById(R.id.detail_storeimage);
        mStoreDetailName = findViewById(R.id.detail_storename);
        mDetailComEdit = findViewById(R.id.edit_detail_com);
        mDetailScoreEdit = findViewById(R.id.edit_detail_score);
        mSendButton=findViewById(R.id.detail_send);

        Intent intent = getIntent();
        mStoreDetailName.setText(intent.getStringExtra(Constants.Store.STORE_NAME));
        mStoreDetailImage.setImageResource(intent.getIntExtra(Constants.Store.STORE_IMAGE, R.mipmap.ic_launcher));

        //初始化用户评论列表的数据
        ArrayList<UserBean> list = new ArrayList<>();
        list.add(new UserBean("不好吃", "用户一", R.drawable.ic_launcher_background, 4));
        list.add(new UserBean("好吃啊", "用户二", R.drawable.ic_launcher_background, 4));
        list.add(new UserBean("还行吧", "用户三", R.drawable.ic_launcher_background, 4));
        list.add(new UserBean("不好吃", "用户四", R.drawable.ic_launcher_background, 4));
        mUserList = list;
        mUserAdapter = new UserComAdapter(this, mUserList);
        mListView.setAdapter(mUserAdapter);
    }
}
