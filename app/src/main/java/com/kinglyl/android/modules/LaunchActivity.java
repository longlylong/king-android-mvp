package com.kinglyl.android.modules;


import android.Manifest;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.kinglyl.android.R;
import com.kinglyl.android.modules.main.MainActivity;
import com.kinglyl.android.modules.user.activity.LoginActivity;
import com.kinglyl.library.activity.BaseActivity;
import com.kinglyl.library.activity.BaseImageView;
import com.kinglyl.library.mvp.BasePresenter;

import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class LaunchActivity extends BaseActivity<BasePresenter> implements EasyPermissions.PermissionCallbacks {

    private static final int REQUEST_CODE_PERMISSION = 10002;

    @BindView(R.id.launch_image)
    BaseImageView baseImageView;

    @Override
    protected int getViewId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initBundle(Bundle bundle) {

    }

    @Override
    public void initUI(Bundle savedInstanceState) {
        requestPermissionsMain();

        baseImageView.display("https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    private void gotoNext() {
        postDelayed(() -> {
            if (isLogin()) {
                MainActivity.open(mContext);
            } else {
                LoginActivity.open(mContext);
            }
            finish();
        }, 500);
    }

    @AfterPermissionGranted(REQUEST_CODE_PERMISSION)
    private void requestPermissionsMain() {
        String[] perms = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE};
        if (EasyPermissions.hasPermissions(mContext, perms)) {
            gotoNext();
        } else {
            EasyPermissions.requestPermissions(this, "申请获取相关权限",
                    REQUEST_CODE_PERMISSION, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
//        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
//            new AppSettingsDialog.Builder(this).setTitle("权限申请")
//                    .setRationale("申请获取相关权限")
//                    .setPositiveButton(getString(R.string.comfir))
//                    .setNegativeButton(getString(R.string.cancle))
//                    .build()
//                    .show();
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        gotoNext();
    }

    @Override
    protected boolean isUseBaseTitleBar() {
        return false;
    }
}
