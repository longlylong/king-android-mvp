package com.king.android.modules.main;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ViewFlipper;

import com.king.android.R;
import com.king.android.receiver.HomeKeyReceiver;
import com.king.android.view.MainFootView;
import com.king.android.view.menu.MenuItem;
import com.king.android.view.menu.MenuType;
import com.king.android.view.menu.OnMenuItemClickListener;
import com.simga.library.activity.BaseActivity;
import com.simga.library.base.CreatePresenter;

import butterknife.BindView;

@CreatePresenter(presenter = MainPresenter.class)
public class MainActivity extends BaseActivity<MainPresenter> implements MainView, OnMenuItemClickListener {

    public static void open(BaseActivity<?> mContext) {
        mContext.startActivity(MainActivity.class);
    }

    @BindView(R.id.main_view_flipper)
    ViewFlipper mViewFlipper;

    @BindView(R.id.main_foot)
    MainFootView mFootView;

    private FragmentA mItemAFragment;
    private FragmentB mItemBFragment;
    private FragmentC mItemCFragment;
    private FragmentD mItemDFragment;

    private MenuType mMenuType;

    @Override
    protected int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initBundle(Bundle bundle) {
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        mFootView.setClickedBg(new MenuItem(MenuType.ItemA));
        showFragmentA();
    }

    @Override
    protected void initData() {
        HomeKeyReceiver homeKeyReceiver = new HomeKeyReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(homeKeyReceiver, intentFilter);
    }

    @Override
    protected void initListener() {
        mFootView.setOnFootClickListener(this);
    }


    @Override
    public void onClick(MenuItem menuItem) {
        mFootView.setClickedBg(menuItem);
        switch (menuItem.menuType) {
            case ItemA:
                showFragmentA();
                setTitle(getResources().getString(R.string.app_name));

                break;

            case ItemB:
                setTitle("B");
                showFragmentB();
                break;

            case ItemC:
                setTitle("C");
                showFragmentC();
                break;

            case ItemD:
                setTitle("D");
                showFragmentD();
                break;
        }
        mMenuType = menuItem.menuType;
    }

    public void showFragmentA() {
        if (MenuType.ItemA == mMenuType) {
            return;
        }

        if (mItemAFragment == null) {
            mItemAFragment = new FragmentA();
            ((FrameLayout) findViewById(R.id.main_fragment_a)).removeAllViews();
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_a, mItemAFragment).commit();
        }

        mViewFlipper.setDisplayedChild(0);
    }

    public void showFragmentB() {
        if (MenuType.ItemB == mMenuType) {
            return;
        }

        if (mItemBFragment == null) {
            mItemBFragment = new FragmentB();
            ((FrameLayout) findViewById(R.id.main_fragment_b)).removeAllViews();
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_b, mItemBFragment).commit();
        }

        mViewFlipper.setDisplayedChild(1);
    }

    public void showFragmentC() {
        if (MenuType.ItemC == mMenuType) {
            return;
        }

        if (mItemCFragment == null) {
            mItemCFragment = new FragmentC();
            ((FrameLayout) findViewById(R.id.main_fragment_c)).removeAllViews();
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_c, mItemCFragment).commit();
        }

        mViewFlipper.setDisplayedChild(2);
    }

    public void showFragmentD() {
        if (MenuType.ItemD == mMenuType) {
            return;
        }

        if (mItemDFragment == null) {
            mItemDFragment = new FragmentD();
            ((FrameLayout) findViewById(R.id.main_fragment_d)).removeAllViews();
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_d, mItemDFragment).commit();
        }
        mViewFlipper.setDisplayedChild(3);
    }
}
