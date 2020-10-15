package com.simga.library.adapter.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<T> mData;
    private int resource;
    protected OnItemListener mItemListener;
    protected Context mContext;

    public RecyclerAdapter(List<T> data, int resource) {
        super();
        this.mData = data;
        this.resource = resource;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(
                resource, parent, false);
        BaseViewHolder holder = holder(view, viewType);
        holder.mContext = this.mContext;
        holder.mData = this.mData;
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setOnItemClickListener(mItemListener);
        if (mData.get(position) != null) {
            holder.build(mData.get(position), position);
        }
    }

    public abstract BaseViewHolder holder(View view, int viewType);

    public void setOnItemClickListener(OnItemListener itemClickListener) {
        this.mItemListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
