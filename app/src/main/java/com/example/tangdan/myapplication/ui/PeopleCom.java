package com.example.tangdan.myapplication.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tangdan.myapplication.R;
import com.example.tangdan.myapplication.base.BaseActivity;

public class PeopleCom extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "PeopleCom";
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private ImageView mHomePageBtn;
    private ImageView mSettingBtn;

    private MainFragment mMainFragment;
    private SettingFragment mSettingFragment;

    public void init() {
        mHomePageBtn = findViewById(R.id.btn_homepage);
        mSettingBtn = findViewById(R.id.btn_setting);
        mFragmentManager = getSupportFragmentManager();
        selectFragment(0);

        mHomePageBtn.setOnClickListener(this);
        mSettingBtn.setOnClickListener(this);
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (mMainFragment != null) {
            fragmentTransaction.hide(mMainFragment);
        }
        if (mSettingFragment != null) {
            fragmentTransaction.hide(mSettingFragment);
        }
    }

    private void selectFragment(int index) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        hideAllFragment(mFragmentTransaction);
        switch (index) {
            case 0:
                if (mMainFragment == null) {
                    mMainFragment = new MainFragment();
                    mFragmentTransaction.add(R.id.container, mMainFragment);
                }
                mFragmentTransaction.show(mMainFragment);
                break;
            case 1:
                if (mSettingFragment == null) {
                    mSettingFragment = new SettingFragment();
                    mFragmentTransaction.add(R.id.container, mSettingFragment);
                }
                mFragmentTransaction.show(mSettingFragment);
                break;
            default:
                break;
        }
        mFragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private long mPressTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mPressTime > 500) {
                Toast.makeText(PeopleCom.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                mPressTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_homepage:
                selectFragment(0);
                break;
            case R.id.btn_setting:
                selectFragment(1);
                break;
            default:
                break;
        }
    }
}
