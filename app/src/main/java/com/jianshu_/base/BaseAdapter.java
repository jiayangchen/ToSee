package com.jianshu_.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jmw on 2016/4/5.
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {

    protected Context mContext;
    protected List<T> mDatas;

    public BaseAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas == null ? null : mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getNewView(position, parent);
        }
        initView(position, convertView, parent);
        return convertView;
    }

    /**
     * 获得新的view
     *
     * @param position
     * @param parent
     * @return
     */
    public abstract View getNewView(int position, ViewGroup parent);

    /**
     * 初始化view
     *
     * @param position
     * @param convertView
     * @param parent
     */
    public abstract void initView(int position, View convertView, ViewGroup parent);
}
