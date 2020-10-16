package com.kinglyl.library.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kinglyl.library.R;
import com.kinglyl.library.account.Account;
import com.kinglyl.library.account.AccountManager;
import com.kinglyl.library.base.BasePresenter;
import com.kinglyl.library.base.BaseView;
import com.kinglyl.library.base.PresenterDispatch;
import com.kinglyl.library.base.PresenterProviders;
import com.kinglyl.library.utils.ToastManager;
import com.kinglyl.library.widget.statuslayout.OnStatusChildClickListener;
import com.kinglyl.library.widget.statuslayout.StatusLayoutManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {

    protected BaseActivity mContext;

    private boolean mIsCreated;
    private View mRootView;
    private PresenterProviders mPresenterProviders;
    private PresenterDispatch mPresenterDispatch;
    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = (BaseActivity) getActivity();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        mPresenterProviders = PresenterProviders.inject(this);
        mPresenterDispatch = new PresenterDispatch(mPresenterProviders);

        mPresenterDispatch.attachView(getActivity(), this);
        mPresenterDispatch.onCreatePresenter(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mPresenterDispatch == null) return;
        mPresenterDispatch.onSaveInstanceState(outState);
    }

    protected T getPresenter() {
        if (mPresenterProviders.getPresenterStore().getSize() <= 0) {
            return null;
        }
        return mPresenterProviders.getPresenter(0);
    }

    public PresenterProviders getPresenterProviders() {
        return mPresenterProviders;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return setContentView(inflater, getViewId());
    }

    /**
     * 调用该办法可避免重复加载UI
     */
    public View setContentView(LayoutInflater inflater, int resId) {
        if (mRootView == null) {
            mRootView = inflater.inflate(resId, null);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mIsCreated) {
            firstInit(savedInstanceState);
        }
        initBundle(savedInstanceState);
        initData();
        initListener();
        mIsCreated = true;
    }

    /**
     * 初始化状态栏
     */
    public void initStatus(View viewId) {
        if (viewId == null) return;
        StatusLayoutManager statusLayoutManager = new StatusLayoutManager.Builder(viewId)
                .setDefaultEmptyClickViewVisible(false)
                .setDefaultErrorClickViewVisible(false)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        onEmptyClick(view);
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        onErrorClick(view);
                    }

                    @Override
                    public void onCustomerChildClick(View view) {
                        onCustomerClick(view);
                    }
                }).build();
    }

    public void onEmptyClick(View view) {
    }

    public void onErrorClick(View view) {
    }

    public void onCustomerClick(View view) {
    }

    protected void finish() {
        mContext.finish();
    }

    public void finishResult(Intent intent) {
        mContext.finishResult(intent);
    }

    public void startActivity(Bundle bundle, Class<?> target) {
        mContext.startActivity(bundle, target);
    }

    public void startForResult(Bundle bundle, int requestCode, Class<?> target) {
        Intent intent = new Intent(mContext, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        mContext.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    protected boolean isLogin() {
        return getAccount().isLogin();
    }

    /**
     * 只在第一次初始化fragment时调用，只会调用一次
     */
    protected abstract void firstInit(Bundle savedInstanceState);

    protected abstract int getViewId();

    /**
     * 每次以replace方式切换fragment时都会调用，调用多次
     */
    protected abstract void initBundle(Bundle savedInstanceState);

    protected abstract void initData();

    protected abstract void initListener();

    @Override
    public void onDestroy() {
        mPresenterDispatch.onDestroyPresenter();
        mUnbinder.unbind();
        super.onDestroy();
    }

    public void showToast(final Object message) {
        mContext.runOnUiThread(() -> ToastManager.show(mContext, message));
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void closeLoading() {
    }

    @Override
    public void onHttpError(int code, String message) {
        showToast(message);
    }

    @Override
    public Account getAccount() {
        return AccountManager.getInstance().getAccount();
    }
}