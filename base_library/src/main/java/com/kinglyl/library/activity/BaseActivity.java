package com.kinglyl.library.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kinglyl.library.R;
import com.kinglyl.library.account.Account;
import com.kinglyl.library.account.AccountManager;
import com.kinglyl.library.mvp.BasePresenter;
import com.kinglyl.library.mvp.BaseView;
import com.kinglyl.library.mvp.PresenterDispatch;
import com.kinglyl.library.mvp.PresenterProviders;
import com.kinglyl.library.thread.WeakHandler;
import com.kinglyl.library.utils.AppManager;
import com.kinglyl.library.utils.StatusBarUtil;
import com.kinglyl.library.utils.ToastManager;
import com.kinglyl.library.widget.BGButton;
import com.kinglyl.library.widget.DialogLoading;
import com.kinglyl.library.widget.statuslayout.OnStatusChildClickListener;
import com.kinglyl.library.widget.statuslayout.StatusLayoutManager;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {

    protected BaseActivity mContext;
    protected ViewGroup bodyView;
    protected StatusLayoutManager statusLayoutManager;
    protected LinearLayout mRlTitleBar;
    protected ImageView mIvBack;
    protected ImageView mIvRight;
    protected TextView mTvTitle;
    protected BGButton mTvRight;
    protected PresenterProviders mPresenterProviders;
    protected PresenterDispatch mPresenterDispatch;
    WeakHandler weakHandler = new WeakHandler();
    private Unbinder mUnBinder;
    private DialogLoading mLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        mContext = this;
        AppManager.get().addActivity(mContext);
        setContentView(getViewId());
        if (getIntent() != null && getIntent().getExtras() != null) {
            initBundle(getIntent().getExtras());
        }
        mPresenterProviders = PresenterProviders.inject(this);
        mPresenterDispatch = new PresenterDispatch(mPresenterProviders);
        mPresenterDispatch.attachView(this, this);
        mPresenterDispatch.onCreatePresenter(savedInstanceState);
        mLoading = new DialogLoading(mContext);
        initUI(savedInstanceState);
        initData();
        initListener();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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

    /**
     * 初始化状态页面
     * 特别注意，这个状态页面在fragment里面运用不太友好，所以最好是指在fragment里面运用
     * 而整个项目中，由于时间紧迫，我只用了部分页面。
     */
    public void initStatus(View viewId) {
        if (viewId == null) return;
        statusLayoutManager = new StatusLayoutManager.Builder(viewId)
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

    /**
     * 覆写finish方法，覆盖默认方法，加入切换动画
     */
    public void finish() {
        super.finish();
        AppManager.get().removeActivity(this);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    public void finishResult(Intent intent) {
        if (intent == null) {
            setResult(RESULT_OK);
        } else {
            setResult(RESULT_OK, intent);
        }
        this.finish();
    }

    /**
     * 覆写startactivity方法，加入切换动画
     */
    public void startActivity(Bundle bundle, Class<?> target) {
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        // 动画切换
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    public void startActivity(Class<?> target) {
        startActivity(null, target);
    }

    /**
     * 带回调的跳转
     */
    public void startForResult(Bundle bundle, int requestCode, Class<?> target) {
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    public void setContentView(int layoutResID) {
        StatusBarUtil.isSetFullScreen(mContext);
        if (isSetStatusBarDarkMode()) {//设置字体为深色
            StatusBarUtil.statusBarDarkMode(mContext);
        }

        if (isUseBaseTitleBar()) {//需要公用的标题栏
            bodyView = findViewById(Window.ID_ANDROID_CONTENT);
            View contentView = LayoutInflater.from(mContext).inflate(layoutResID, null);
            View titleBarView = LayoutInflater.from(mContext).inflate(R.layout.widget_title, null);

            //下面这么做，是可以提升速度，因为没加载一层布局，就会耗一点时间，这里可以加载上一层布局
            FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            fl.topMargin = getStateBar();
            bodyView.addView(contentView, fl);

            LinearLayout.LayoutParams titleBar = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, getStateBar());
            bodyView.addView(titleBarView, titleBar);

            mUnBinder = ButterKnife.bind(this, contentView);
            mIvBack = findViewById(R.id.iv_back);
            mRlTitleBar = findViewById(R.id.rl_title);
            mTvTitle = findViewById(R.id.tv_title);
            mTvRight = findViewById(R.id.tv_right);
            mIvRight = findViewById(R.id.iv_right);
            setBackClick();
        } else {
            if (layoutResID != 0) {
                super.setContentView(layoutResID);
                mUnBinder = ButterKnife.bind(this);
            }
        }
    }

    public void setBackClick() {
        if (mIvBack != null) {
            mIvBack.setOnClickListener(v -> onBackPressed());
        }
    }

    // 设置标题
    public void setTitle(final String title) {
        runOnUiThread(() -> {
            if (mTvTitle != null) {
                mTvTitle.setText(title);
            }
        });
    }

    private int getStateBar() {
        try {
            Class c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            int statusBarHeight = this.getResources().getDimensionPixelSize(x) + (int) Math.ceil(50 * this.getResources().getDisplayMetrics().density);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int) Math.ceil(76 * this.getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onDestroy() {
        if (mUnBinder != null && mUnBinder != Unbinder.EMPTY) {
            mUnBinder.unbind();
        }
        this.mUnBinder = null;
        if (mPresenterDispatch != null) {
            mPresenterDispatch.onDestroyPresenter();
        }
        dismissLoading();
        mLoading = null;
        super.onDestroy();
    }

    public void showLoading(final boolean cancelable) {
        if (mLoading != null) {
            runOnUiThread(() -> mLoading.show(cancelable));
        }
    }

    public void dismissLoading() {
        if (mLoading != null) {
            runOnUiThread(() -> mLoading.dismiss());
        }
    }

    protected abstract int getViewId();

    protected abstract void initBundle(Bundle bundle);

    protected abstract void initUI(Bundle savedInstanceState);

    protected abstract void initData();

    protected abstract void initListener();

    /**
     * 是否设置状态栏字体图标为深色模式
     */
    protected boolean isSetStatusBarDarkMode() {
        return true;
    }

    protected boolean isUseBaseTitleBar() {
        return true;
    }

    public void showToast(int res) {
        showToast(getString(res));
    }

    public void showToast(final Object message) {
        runOnUiThread(() -> ToastManager.show(mContext, message));
    }

    public void postDelayed(Runnable runnable, long delayMillis) {
        weakHandler.postDelayed(runnable, delayMillis);
    }

    protected boolean isLogin() {
        return getAccount().isLogin();
    }

    @Override
    public void showLoading() {
        showLoading(true);
    }

    @Override
    public void closeLoading() {
        dismissLoading();
    }

    @Override
    public void onHttpError(int code, String message) {
        showToast(message);
        dismissLoading();
    }

    @Override
    public Account getAccount() {
        return AccountManager.getInstance().getAccount();
    }
}
