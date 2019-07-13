package com.example.tangdan.myapplication.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tangdan.myapplication.R;
import com.example.tangdan.myapplication.adapter.MyViewPagerAdatper;
import com.example.tangdan.myapplication.base.BaseAdapter;
import com.example.tangdan.myapplication.bean.MyUser;
import com.example.tangdan.myapplication.bean.StoreBean;
import com.example.tangdan.myapplication.helper.BmobDbHelper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.QueryListener;

import static com.example.tangdan.myapplication.bean.Constants.Register.USER_ACCOUNT_REGISTER;
import static com.example.tangdan.myapplication.bean.Constants.Register.USER_PASSWORD_REGISTER;
import static com.example.tangdan.myapplication.bean.Constants.SharedPreference.SHAREDPREFERENCE_NAME;
import static com.example.tangdan.myapplication.bean.Constants.SharedPreference.STORE_COLLECTION;
import static com.example.tangdan.myapplication.bean.Constants.Store.STORE_IMAGE;
import static com.example.tangdan.myapplication.bean.Constants.Store.STORE_IMAGE_IC_LAUNCHER;
import static com.example.tangdan.myapplication.bean.Constants.Store.STORE_NAME;
import static com.example.tangdan.myapplication.bean.Constants.Store.STORE_OBJECT_ID;

public class MainFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemLongClickListener, View.OnTouchListener, AdapterView.OnItemClickListener {
    private static final String TAG = "MainFragment";
    private ListView mListView;
    private BaseAdapter mAdapter;
    private ArrayList<StoreBean> mStoreList;
    private ViewPager mViewPager;
    private MyViewPagerAdatper myViewPagerAdatper;
    private ArrayList<ImageView> mImageViews = new ArrayList<>();
    private int mPosition;
    private boolean isPlay;
    private ImageView mMeatIv;
    private ImageView mVegIv;
    private ImageView mFruitIv;
    private ImageView mSnackIv;
    private ImageView mIdcMeatIv;
    private ImageView mIdcVegIv;
    private ImageView mIdcFruitIv;
    private ImageView mIdcSnackIv;
    private Set<String> mHashSet;

    private ArrayList<String> mMeatObjectIdList;
    private ArrayList<String> mVegObjectIdList;
    private ArrayList<String> mFruitObjectIdList;
    private ArrayList<String> mSnackObjectIdList;

    private int mBannerOnePos;
    private int mBannerTwoPos;
    private int mBannerThreePos;
    private String mAccount;
    private String mPassword;

    private int flag = 0;
    private float downX;
    private float downY;

    private int indicatorFlag;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BmobDbHelper.getInstance().init(getActivity());
        init();
        autoPlayBanner();
        refreshData();

        mViewPager.setIIIIIIIIIIIIIIIInTouchListener(this);
        mMeatIv.setOnClickListener(ttthisssssssssss);
        mVegIv.setOnClickListener(this);

        mFruitIv.setOnClickListener(this);
        mSnackIv.setOnClickListener(this);

        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
    }

    private void autoPlayBanner() {
        new Thread() {
            @Override
            public void run() {
                while (!isPlay) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = new Message();
                    msg.what = 0x00;
                    mHandler.sendMessage(msg);
                    if ((mViewPager.getCurrentItem() + 1) == mImageViews.size()) {
                        mPosition = -1;
                    }
                    mPosition += 1;
                }
            }
        }.start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x00:
                    mViewPager.setCurrentItem(mPosition);
                    break;
                default:
                    break;
            }
        }
    };

    private void initBannerAdapter() {
        int[] resId = {R.drawable.store0, R.drawable.store1, R.drawable.store2};
        ImageView image;
        for (int i = 0; i < 3; i++) {
            image = new ImageView(getActivity());
            image.setImageResource(resId[i]);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageViews.add(image);
        }
        myViewPagerAdatper = new MyViewPagerAdatper(mImageViews, mViewPager);
    }

    private void refreshData() {
        for (int i = 0; i < mMeatObjectIdList.size(); i++) {
            getBmobStoreNameById(mMeatObjectIdList.get(i));
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final StoreBean storeBean = mStoreList.get(position);

        View chooseView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_item_longpress, null);
        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.setView(chooseView);
        dialog.show();
        MyButton collectBtn = chooseView.findViewById(R.id.btn_collect);
        MyButton filterBtn = chooseView.findViewById(R.id.btn_filter);
        collectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                mHashSet = sharedPreferences.getStringSet(STORE_COLLECTION, new HashSet<String>());
                mHashSet.add(storeBean.getmCopyStoreId());
                editor.clear();
                editor.putStringSet(STORE_COLLECTION, mHashSet);
                editor.commit();
                Toast.makeText(getContext(), "收藏成功", Toast.LENGTH_SHORT).show();
            }
        });
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mStoreList.remove(storeBean);
                switch (indicatorFlag) {
                    case 0:
                        mMeatObjectIdList.remove(storeBean.getmCopyStoreId());
                        break;
                    case 1:
                        mVegObjectIdList.remove(storeBean.getmCopyStoreId());
                        break;
                    case 2:
                        mFruitObjectIdList.remove(storeBean.getmCopyStoreId());
                        break;
                    case 3:
                        mSnackObjectIdList.remove(storeBean.getmCopyStoreId());
                        break;
                    default:
                        break;
                }
                mAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "已将商家从列表中删除", Toast.LENGTH_SHORT).show();
            }
        });

        return true;
    }

    @Override
    public void onClick(View v) {
        setAllWhiteIndicator();
        mStoreList.clear();
        switch (v.getId()) {
            case R.id.meat_iv:
                mIdcMeatIv.setImageResource(R.color.white);
                indicatorFlag = 0;
                for (int i = 0; i < mMeatObjectIdList.size(); i++) {
                    getBmobStoreNameById(mMeatObjectIdList.get(i));
                }
                break;
            case R.id.veg_iv:
                mIdcVegIv.setImageResource(R.color.white);
                indicatorFlag = 1;
                for (int i = 0; i < mVegObjectIdList.size(); i++) {
                    getBmobStoreNameById(mVegObjectIdList.get(i));
                }
                break;
            case R.id.fruit_iv:
                mIdcFruitIv.setImageResource(R.color.white);
                indicatorFlag = 2;
                for (int i = 0; i < mFruitObjectIdList.size(); i++) {
                    getBmobStoreNameById(mFruitObjectIdList.get(i));
                }
                break;
            case R.id.snack_iv:
                mIdcSnackIv.setImageResource(R.color.white);
                indicatorFlag = 3;
                for (int i = 0; i < mSnackObjectIdList.size(); i++) {
                    getBmobStoreNameById(mSnackObjectIdList.get(i));
                }
                break;
            default:
                break;
        }
    }

    private void init() {
        mViewPager = getActivity().findViewById(R.id.ad_banner_pager);
        initBannerAdapter();
        mListView = getActivity().findViewById(R.id.storeList);
        mMeatIv = getActivity().findViewById(R.id.meat_iv);
        mVegIv = getActivity().findViewById(R.id.fruit_iv);
        mFruitIv = getActivity().findViewById(R.id.fruit_iv);
        mSnackIv = getActivity().findViewById(R.id.snack_iv);
        mIdcMeatIv = getActivity().findViewById(R.id.indicator_meat_iv);
        mIdcVegIv = getActivity().findViewById(R.id.indicator_veg_iv);
        mIdcFruitIv = getActivity().findViewById(R.id.indicator_fruit_iv);
        mIdcSnackIv = getActivity().findViewById(R.id.indicator_snack_iv);
        mStoreList = new ArrayList<>();
        mMeatObjectIdList = new ArrayList<>();
        mVegObjectIdList = new ArrayList<>();
        mFruitObjectIdList = new ArrayList<>();
        mSnackObjectIdList = new ArrayList<>();
        mAdapter = new BaseAdapter(getActivity(), mStoreList);
        mIdcMeatIv.setImageResource(R.color.white);
        mListView.setAdapter(mAdapter);
        mViewPager.setAdapter(myViewPagerAdatper);
        mViewPager.setCurrentItem(0);

        Intent intent = getActivity().getIntent();
        mAccount = intent.getStringExtra(USER_ACCOUNT_REGISTER);
        mPassword = intent.getStringExtra(USER_PASSWORD_REGISTER);
        initListData();
    }

    private void initListData() {
        mMeatObjectIdList.add("0VMTAAAo");
        mMeatObjectIdList.add("2ShaMMMc");
        mMeatObjectIdList.add("1EoHKKKW");
        mMeatObjectIdList.add("FxztCCCD");

        mVegObjectIdList.add("2ShaMMMc");
        mVegObjectIdList.add("0VMTAAAo");
        mVegObjectIdList.add("1EoHKKKW");
        mVegObjectIdList.add("FxztCCCD");

        mFruitObjectIdList.add("0VMTAAAo");
        mFruitObjectIdList.add("2ShaMMMc");
        mFruitObjectIdList.add("FxztCCCD");
        mFruitObjectIdList.add("1EoHKKKW");

        mSnackObjectIdList.add("1EoHKKKW");
        mSnackObjectIdList.add("0VMTAAAo");
        mSnackObjectIdList.add("2ShaMMMc");
        mSnackObjectIdList.add("FxztCCCD");
    }

    private void setAllWhiteIndicator() {
        mIdcMeatIv.setImageResource(R.color.crimson);
        mIdcVegIv.setImageResource(R.color.crimson);
        mIdcFruitIv.setImageResource(R.color.crimson);
        mIdcSnackIv.setImageResource(R.color.crimson);
    }

    private void navigateClick(int position) {
        StoreBean storeBean = mStoreList.get(position);
        Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
        intent.putExtra(STORE_NAME, storeBean.getmStoreName());
        intent.putExtra(STORE_OBJECT_ID, storeBean.getmCopyStoreId());
        intent.putExtra(USER_ACCOUNT_REGISTER, mAccount);

        Bitmap bitmap = storeBean.getmStorePic();
//        intent.putExtra(STORE_IMAGE, storeBean.getmStorePic());
        if (bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bitmapByte = baos.toByteArray();
            intent.putExtra(STORE_IMAGE, bitmapByte);
        } else {
            int resId = R.mipmap.ic_launcher;
            intent.putExtra(STORE_IMAGE_IC_LAUNCHER, resId);
        }

        startActivity(intent);
    }

    private void bannerNavigateClick(String mObjectId){
        BmobQuery<StoreBean> query=new BmobQuery<>();
        query.getObject(mObjectId, new QueryListener<StoreBean>() {
            @Override
            public void done(StoreBean storeBean, BmobException e) {
                if (e==null){
                    Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                    intent.putExtra(STORE_NAME, storeBean.getmStoreName());
                    intent.putExtra(STORE_OBJECT_ID, storeBean.getObjectId());
                    intent.putExtra(USER_ACCOUNT_REGISTER, mAccount);

                    Bitmap bitmap = storeBean.getmStorePic();
//        intent.putExtra(STORE_IMAGE, storeBean.getmStorePic());
                    if (bitmap != null) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] bitmapByte = baos.toByteArray();
                        intent.putExtra(STORE_IMAGE, bitmapByte);
                    } else {
                        int resId = R.mipmap.ic_launcher;
                        intent.putExtra(STORE_IMAGE_IC_LAUNCHER, resId);
                    }

                    startActivity(intent);
                }else {
                    Log.d(TAG," 错误代码："+e.getErrorCode());
                    Toast.makeText(getActivity(),"阿欧，好像网络不行",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isPlay = true;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        navigateClick(position);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                flag = 1;
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if ((event.getX() - downX) != 0 || (event.getY() - downY) != 0) {
                    flag = 0;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (flag == 1) {
                    if (mViewPager.getCurrentItem() == 0) {
                        bannerNavigateClick("2ShaMMMc");
                    } else if (mViewPager.getCurrentItem() == 1) {
                        bannerNavigateClick("0VMTAAAo");
                    } else {
                        bannerNavigateClick("1EoHKKKW");
                    }
                }
                break;
            default:
                break;
        }
        return false;
    }
}
