package com.kinglyl.library.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.lcodecore.tkrefreshlayout.IBottomView;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;

/**
 * PackageName：cn.ewhale.huaxinebought.widget
 * 使用时记得给子布局加上android:overScrollMode="never"   ScrollView  recycleView ListView GridView这些列表类
 * 还有更多的操作查询父类
 */
public class CoustomRefreshView extends TwinklingRefreshLayout {

    private RefreshViewHolder headView;
    private IBottomView footView;

    public CoustomRefreshView(Context context) {
        super(context, null);
        initView();
    }

    public CoustomRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        initView();
    }

    public CoustomRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        headView = new RefreshViewHolder(getContext());
        footView = new LoadingView(getContext());
        setOverScrollBottomShow(false);
        setOverScrollTopShow(false);
        addHeadView(headView);
        addFootView(footView);
    }

    //设置刷新头布局
    public void addHeadView(RefreshViewHolder headView) {
        this.headView = headView;
        setHeaderView(headView);
    }

    //设置加载底部布局
    public void addFootView(IBottomView footView) {
        this.footView = footView;
        setBottomView(footView);
    }

    //关闭刷新
    public void finishRefresh() {
        finishRefreshing();
    }

    //关闭加载更多
    public void finishloadingMore() {
        finishLoadmore();
    }

    //同时关闭加载更多下拉刷新布局
    public void finishRefreshLoadingMore() {
        finishRefreshing();
        finishLoadmore();
    }

    //是否禁止下拉刷新操作
    public void enabelRefresh(boolean enable) {
        setEnableRefresh(enable);
    }

    //是否禁止上拉加载操作
    public void enableLoadMore(boolean enable) {
        setEnableLoadmore(enable);
    }


    public void onLoad(int size) {
        finishRefreshLoadingMore();
        if (size < 20) {
            enableLoadMore(false);
        } else {
            enableLoadMore(true);
        }
    }
}
