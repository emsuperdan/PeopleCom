package com.example.tangdan.myapplication.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tangdan.myapplication.R;
import com.example.tangdan.myapplication.bean.StoreBean;

import java.util.ArrayList;

public class BaseAdapter extends android.widget.BaseAdapter {
    private Context mContext;
    private ArrayList<StoreBean> mStoreList;

    public BaseAdapter(Context context, ArrayList<StoreBean> list) {
        this.mContext = context;
        this.mStoreList = list;
    }

    @Override
    public int getCount() {
        return mStoreList.size();
    }

    @Override
    public Object getItem(int position) {
        return mStoreList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        StoreBean storeBean = mStoreList.get(position);
        if (convertView == null) {// 当缓存池是空的，也就代表想缓存的view控件也是空的，所以要setTag往缓存池中加ViewHolder，方便后面获取holder实例控件缓存
            convertView = LayoutInflater.from(mContext).inflate(R.layout.store_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mStoreImage = convertView.findViewById(R.id.storeimage);
            viewHolder.mStoreName = convertView.findViewById(R.id.storename);

            convertView.setTag(viewHolder);
        } else {//如果不为空，就直接服用缓存池，并且viewHolder实例中的控件缓存也是不为空的
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mStoreName.setText(storeBean.getmStoreName());
        if (storeBean.getmStorePic() == null) {
            viewHolder.mStoreImage.setImageResource(R.mipmap.ic_launcher);
        } else {
            viewHolder.mStoreImage.setImageBitmap(storeBean.getmStorePic());
        }

        return convertView;
    }

    class ViewHolder {
        public TextView mStoreName;
        public ImageView mStoreImage;
    }
}
