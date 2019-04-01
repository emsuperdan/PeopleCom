package com.example.tangdan.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tangdan.myapplication.R;
import com.example.tangdan.myapplication.bean.UserBean;

import java.util.ArrayList;

public class UserComAdapter extends android.widget.BaseAdapter {
    private Context mContext;
    private ArrayList<UserBean> mDataList;

    public UserComAdapter(Context context, ArrayList<UserBean> list) {
        this.mContext = context;
        this.mDataList = list;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        UserBean userBean = mDataList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.user_com_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mUserComImage = convertView.findViewById(R.id.user_com_image);
            viewHolder.mUserComText = convertView.findViewById(R.id.user_com_text);
            viewHolder.mUserAccount = convertView.findViewById(R.id.username);
//            viewHolder.mUserScore = convertView.findViewById(R.id.userscore);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        viewHolder.mUserScore.setText(String.valueOf(userBean.getmScore()));
        viewHolder.mUserAccount.setText(userBean.getmUserAccount());
        viewHolder.mUserComText.setText(userBean.getmCommentText());
        if (userBean.getPicDecodeUrl()!=null){
            viewHolder.mUserComImage.setVisibility(View.VISIBLE);
        }else {
            viewHolder.mUserComImage.setVisibility(View.GONE);
        }
        viewHolder.mUserComImage.setImageBitmap(userBean.getPicDecodeUrl());

        return convertView;
    }

    class ViewHolder {
        public TextView mUserAccount;
//        public TextView mUserScore;
        public TextView mUserComText;
        public ImageView mUserComImage;
    }
}
