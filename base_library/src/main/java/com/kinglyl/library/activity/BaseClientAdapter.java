package com.kinglyl.library.activity;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kinglyl.library.R;

import java.util.List;

/**
 * 适配器的基类
 * 集成了空数据
 * loading
 * 错误
 * Created by King on 2018/3/8 0008.
 */

public abstract class BaseClientAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    private View mEmptyView;
    private TextView mEmptyText;
    private View mLoadingBar;

    public BaseClientAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    public abstract void convert(@NonNull BaseViewHolder helper, T item);

    public void initEmptyView(Activity activity) {
        mEmptyView = activity.getLayoutInflater().inflate(R.layout.view_empty, this.getRecyclerView(), false);
        mEmptyText = mEmptyView.findViewById(R.id.empty_text);
        mLoadingBar = mEmptyView.findViewById(R.id.empty_loading_bar);
    }

    public void setLoadingDataView() {
        mLoadingBar.setVisibility(View.VISIBLE);
        mEmptyText.setText("加载中...");
        setEmptyView(mEmptyView);
    }

    void setMaybeNoDataView() {
        if (getData().size() == 0) {
            mLoadingBar.setVisibility(View.GONE);
            mEmptyText.setText("没数据哦");
            setEmptyView(mEmptyView);
        }
    }

    public void setErrorView() {
        mLoadingBar.setVisibility(View.GONE);
        mEmptyText.setText("加载失败");
        setEmptyView(mEmptyView);
    }
}
