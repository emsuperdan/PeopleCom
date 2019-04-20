package com.example.tangdan.myapplication.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class MyViewPagerAdatper extends PagerAdapter {
    private ArrayList<ImageView> mImageList;
    private ViewPager mViewPager;

    public MyViewPagerAdatper(ArrayList<ImageView> list, ViewPager viewPager){
        this.mImageList=list;
        this.mViewPager=viewPager;
    }

    //设置适配器中的数据个数，默认是无穷个数
    @Override
    public int getCount() {
        return mImageList.size();
    }

    //从缓存中拿数据
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    //如果没有缓存的话，就创建一个新的条目item
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView=mImageList.get(position%mImageList.size());
        if (imageView.getParent()!=null){
            ((ViewPager)imageView.getParent()).removeView(imageView);
        }
        mViewPager.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        mViewPager.removeView(mImageList.get(position%mImageList.size()));
    }
}
