package com.example.tangdan.myapplication.adapter;

import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.PagerAdapter;
import java.util.ArrayList;

public class MyViewPagerAdapter extends PagerAdapter {
    private ArrayList<ImageView> mImageList;
    private ViewPager mViewPager;

    public MyViewPagerAdapter(ArrayList<ImageView> list, ViewPager viewPager){
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
