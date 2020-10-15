package com.simga.library.adapter.recyclerview;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class MutiRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<T> mData;
    protected OnItemListener itemListener;

    public MutiRecyclerAdapter(List<T> data) {
        super();
        this.mData = data;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setOnItemClickListener(itemListener);
        if (mData.size() == 0) return;
        holder.build(mData.get(position), position);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position, List payloads) {
        holder.setOnItemClickListener(itemListener);
        if (mData.size() == 0) return;
        holder.build(mData.get(position), position);
    }

    public void setOnItemClickListener(OnItemListener itemClickListener) {
        this.itemListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        if (mData != null && mData.size() > 0) {
            return mData.size();
        }
        return 0;
    }
}
