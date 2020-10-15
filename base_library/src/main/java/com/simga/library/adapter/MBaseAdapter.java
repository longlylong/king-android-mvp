package com.simga.library.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.simga.library.activity.BaseActivity;

import java.util.List;

public abstract class MBaseAdapter<T> extends BaseAdapter {

    protected BaseActivity mContext;
    private LayoutInflater mInflater;
    protected List<T> mData;
    protected int mRes;

    public MBaseAdapter(Context context, List<T> data, int resourceId) {
        this.mContext = (BaseActivity) context;
        this.mData = data;
        this.mInflater = LayoutInflater.from(context);
        this.mRes = resourceId;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return convertId(position, mData.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(mRes, null, false);
            newView(convertView, position);
        }
        if (mData.get(position) != null) {
            holderView(convertView, mData.get(position), position);
        }
        return convertView;
    }

    public void setData(List<T> data) {
        this.mData = data;
    }

    public List<T> getData() {
        return mData;
    }

    /**
     * 用于覆盖，在各个其他adapter里边返回id,默认返回position
     */
    protected long convertId(int position, Object object) {
        return position;
    }

    /**
     * 第一次创建的时调用
     */
    protected abstract void newView(View convertView, int position);

    protected abstract void holderView(View convertView, T itemObject, int position);
}
