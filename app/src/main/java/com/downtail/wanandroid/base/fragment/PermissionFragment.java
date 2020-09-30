package com.downtail.wanandroid.base.fragment;

import androidx.annotation.NonNull;

import com.downtail.wanandroid.base.mvp.BaseContract;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * NursingWorker
 * Created by downtail on 2020/9/4
 */
public abstract class PermissionFragment<T extends BaseContract.BasePresenter> extends DaggerFragment<T> implements EasyPermissions.PermissionCallbacks,
        EasyPermissions.RationaleCallbacks {

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onRationaleDenied(int requestCode) {

    }
}
