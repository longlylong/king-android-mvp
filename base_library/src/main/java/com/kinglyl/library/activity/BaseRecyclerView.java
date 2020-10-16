package com.kinglyl.library.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView基类
 * Created by King on 2018/3/8 0008.
 */

public class BaseRecyclerView extends RecyclerView {

    public BaseRecyclerView(Context context) {
        super(context);
        initGlobalLayoutListener();
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initGlobalLayoutListener();
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initGlobalLayoutListener();
    }

    private void initGlobalLayoutListener() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (getAdapter() instanceof BaseClientAdapter) {
                    ((BaseClientAdapter) getAdapter()).setMaybeNoDataView();
                }
            }
        });
    }

    //设置竖向的view
    public void setVerticalLayout() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        setLayoutManager(manager);
    }
}
