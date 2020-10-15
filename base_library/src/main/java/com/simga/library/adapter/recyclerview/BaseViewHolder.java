package com.simga.library.adapter.recyclerview;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    private OnItemListener itemListener;
    public Context mContext;
    public List<T> mData;

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(view -> {
            if (itemListener != null) {
                itemListener.onItem(view, getLayoutPosition());
            }
        });
    }

    public void setOnItemClickListener(OnItemListener itemClickListener) {
        this.itemListener = itemClickListener;
    }

    public abstract void build(T object, int position);

}
