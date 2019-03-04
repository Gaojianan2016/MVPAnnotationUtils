package com.gjn.mvpannotationlibrary.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gjn.mvpannotationlibrary.utils.AppManager;
import com.gjn.mvpannotationlibrary.utils.MvpLog;
import com.gjn.mvpannotationlibrary.utils.ToastUtils;
import com.shoumi.easydialogfragmentlibrary.DFragmentManager;
import com.shoumi.easydialogfragmentlibrary.base.BaseDFragment;

/**
 * @author gjn
 * @time 2018/7/25 17:35
 */

public abstract class BaseActivity extends AppCompatActivity implements IEvent {

    protected Context mContext;
    protected Activity mActivity;
    protected Bundle mBundle;
    private DFragmentManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MvpLog.d("onCreate " + getClass().getSimpleName());
        AppManager.getInstance().addActivity(this);
        preCreate();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        mContext = this;
        mActivity = this;
        mBundle = getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras();
        manager = new DFragmentManager(this);

        init();
        initView();
        initData();
    }

    protected void preCreate() {

    }

    protected void init() {

    }

    @Override
    public void showNextActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    @Override
    public void showNextActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void toNextActivity(Class<?> cls) {
        showNextActivity(cls);
        finish();
    }

    @Override
    public void toNextActivity(Class<?> cls, Bundle bundle) {
        showNextActivity(cls, bundle);
        finish();
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(mContext, msg);
    }

    @Override
    public void showDialog(BaseDFragment dialogFragment) {
        manager.showDialog(dialogFragment);
    }

    @Override
    public void dismissDialog(BaseDFragment dialogFragment) {
        manager.dismissDialog(dialogFragment);
    }

    @Override
    public void dismissDialogAll() {
        manager.clearDialog();
    }

    @Override
    protected void onDestroy() {
        MvpLog.d("onDestroy " + getClass().getSimpleName());
        dismissDialogAll();
        super.onDestroy();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();
}
