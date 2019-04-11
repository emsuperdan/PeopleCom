package com.example.tangdan.myapplication.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tangdan.myapplication.R;
import com.example.tangdan.myapplication.adapter.UserComAdapter;
import com.example.tangdan.myapplication.base.BaseActivity;
import com.example.tangdan.myapplication.bean.MyUser;
import com.example.tangdan.myapplication.bean.StoreBean;
import com.example.tangdan.myapplication.bean.UserBean;
import com.example.tangdan.myapplication.helper.BmobDbHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

import static com.example.tangdan.myapplication.bean.Constants.Register.USER_ACCOUNT_REGISTER;
import static com.example.tangdan.myapplication.bean.Constants.Store.STORE_IMAGE;
import static com.example.tangdan.myapplication.bean.Constants.Store.STORE_NAME;
import static com.example.tangdan.myapplication.bean.Constants.Store.STORE_OBJECT_ID;

public class StoreDetailActivity extends BaseActivity {
    private static final String TAG = "StoreDetailActivity";
    private static final int REUSLT_SELECT_PIC = 1;

    private ListView mListView;
    private ArrayList<UserBean> mUserList;
    private UserComAdapter mUserAdapter;
    private TextView mStoreDetailName;
    private ImageView mStoreDetailImage;
    private EditText mDetailComEdit;
    private EditText mDetailScoreEdit;
    private Button mSendButton;
    private String mStoreObjectId;
    private SwipeRefreshLayout mSwipe;
    private ImageView mSendPicButton;

    private ImageView mDialogShowPic;

    private UserBean mUserBean;
    private StoreBean mStoreBean;
    private MyUser mMyUser;
    private RegisterPage mRegisterPage;
    private String mUserAccountName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_store);
        BmobDbHelper.getInstance().init(this);
        init();
        queryData(mStoreObjectId);

        mSendPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REUSLT_SELECT_PIC);
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // &&!TextUtils.isEmpty(mDetailScoreEdit.getText().toString()) && isInteger(mDetailScoreEdit.getText().toString())
                if (!TextUtils.isEmpty(mDetailComEdit.getText().toString())) {
                    String comment = mDetailComEdit.getText().toString();
//                    int score = Integer.valueOf(mDetailScoreEdit.getText().toString());

                    mUserBean.setmCommentText(comment);
                    mStoreBean.setObjectId(mStoreObjectId);
                    mUserBean.setLinkToStore(mStoreBean);
                    mUserBean.setmUserAccount(mUserAccountName);

                    //图片进行上传
                    if (mPicPath != null) {
                        final BmobFile file = new BmobFile(new File(mPicPath));
                        file.uploadblock(new UploadFileListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(StoreDetailActivity.this, "图片上传成功", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "图片上传成功" + "   " + file.getFileUrl());
                                    mUserBean.setmPicUrl(file);
                                    notifySaveToCloud();
                                } else {
                                    Log.d(TAG, "图片上传失败" + e.getErrorCode());
                                }
                            }
                        });
                        flag = true;
                    }

                    if (!flag) {
                        mUserBean.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Log.d(TAG, s + "      " + "上传成功");
                                    Toast.makeText(StoreDetailActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                                    mDetailComEdit.setText("");
//                                mDetailScoreEdit.setText("");
                                } else {
                                    Log.d(TAG, "上传失败" + s);
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(StoreDetailActivity.this, "评论不能为空！", Toast.LENGTH_SHORT).show();
                }

                mPicPath = null;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserBean userBean = mUserList.get(position);
                if (userBean.getPicDecodeUrl() != null) {
                    View showDialogView = LayoutInflater.from(StoreDetailActivity.this).inflate(R.layout.dialog_showpic, null);
                    ImageView showBigImage = showDialogView.findViewById(R.id.dialog_showpic_image);
                    final AlertDialog dialog = new AlertDialog.Builder(StoreDetailActivity.this).create();
                    showBigImage.setImageBitmap(userBean.getPicDecodeUrl());
                    dialog.setView(showDialogView);
                    dialog.show();

                    showDialogView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            }
        });

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View firstView = view.getChildAt(firstVisibleItem);
                //当firstItem是第0位，或者firstView为空（数据为空）或者listview最上面top一个item恰好等于0
                if (firstVisibleItem == 0 && (firstView == null || firstView.getTop() == 0)) {
                    mSwipe.setEnabled(true);
                } else {
                    mSwipe.setEnabled(false);
                }
            }
        });

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
                }, 1500);
            }
        });
    }

    private void notifySaveToCloud() {
        mUserBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.d(TAG, s + "      " + "上传成功");
                    Toast.makeText(StoreDetailActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                    mDetailComEdit.setText("");
//                                mDetailScoreEdit.setText("");
                } else {
                    Log.d(TAG, "上传失败" + s);
                }
            }
        });
    }

    private String mPicPath = null;
    private boolean flag = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REUSLT_SELECT_PIC && resultCode == RESULT_OK && data != null) {
            Uri picUri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(picUri, projection, null, null, null);
            cursor.moveToFirst();
            int column = cursor.getColumnIndex(projection[0]);
            mPicPath = cursor.getString(column);
            cursor.close();
        }
    }

    //
//    private boolean isInteger(String str) {
//        if (str.length() > 1) {
//            return false;
//        }
//        try {
//            Integer.valueOf(str);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    private void queryData(String StoreBeanId) {
        mStoreBean.setObjectId(StoreBeanId);
        BmobQuery<UserBean> query = new BmobQuery<>();
        query.addWhereEqualTo("linkToStore", new BmobPointer(mStoreBean));
        query.findObjects(new FindListener<UserBean>() {
            @Override
            public void done(List<UserBean> list, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "查询成功");
                    for (int i = 0; i < list.size(); i++) {
                        final UserBean userBean = list.get(i);
//                        userBean.setmUserAccount(mUserAccountName);
                        mUserList.add(userBean);

                        BmobFile picFile = userBean.getmPicUrl();
                        if (picFile != null) {
                            picFile.download(new DownloadFileListener() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        Log.d(TAG, "图片查询下载成功");
                                        userBean.setPicDecodeUrl(BitmapFactory.decodeFile(s));
                                        mUserAdapter.notifyDataSetChanged();
                                    } else {
                                        Log.d(TAG, "图片查询下载错误" + e.getErrorCode());
                                    }
                                }

                                @Override
                                public void onProgress(Integer integer, long l) {

                                }
                            });
                        }
                    }
                    mUserAdapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "    " + "查询失败" + "   " + e.getErrorCode());
                }
            }
        });
    }

    public void init() {
        mListView = findViewById(R.id.user_com_list);
        mStoreDetailImage = findViewById(R.id.detail_storeimage);
        mStoreDetailName = findViewById(R.id.detail_storename);
        mDetailComEdit = findViewById(R.id.edit_detail_com);
//        mDetailScoreEdit = findViewById(R.id.edit_detail_score);
        mSendButton = findViewById(R.id.detail_send);
        mSendPicButton = findViewById(R.id.btn_sendpic);
        mSwipe = findViewById(R.id.swipe);
        mDialogShowPic = findViewById(R.id.user_com_image);

        Intent intent = getIntent();
        mStoreDetailName.setText(intent.getStringExtra(STORE_NAME));
//        mStoreDetailImage.setImageResource(intent.getIntExtra(STORE_IMAGE, R.mipmap.ic_launcher));
        byte[] bis = intent.getByteArrayExtra(STORE_IMAGE);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
        mStoreDetailImage.setImageBitmap(bitmap);
        mStoreObjectId = intent.getStringExtra(STORE_OBJECT_ID);
        mUserAccountName = intent.getStringExtra(USER_ACCOUNT_REGISTER);

        mUserList = new ArrayList<>();
        mUserAdapter = new UserComAdapter(this, mUserList);
        mListView.setAdapter(mUserAdapter);
        mSwipe.setColorSchemeColors(Color.YELLOW);

        mUserBean = new UserBean();
        mStoreBean = new StoreBean();
        mMyUser = new MyUser();
        mRegisterPage = new RegisterPage();
    }
}
