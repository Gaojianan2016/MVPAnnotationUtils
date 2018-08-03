package com.gjn.mvpannotationlibrary.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gjn.mvpannotationlibrary.utils.AppManager;
import com.gjn.mvpannotationlibrary.utils.ToastUtils;

/**
 * @author gjn
 * @time 2018/7/25 17:35
 */

public abstract class BaseActivity extends AppCompatActivity implements IEvent {

    protected Context mContext;
    protected Activity mActivity;
    protected Bundle mBundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        preCreate();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        mContext = this;
        mActivity = this;
        mBundle = getIntent().getExtras();

        AppManager.getInstance().addActivity(this);

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
    public void finish() {
        AppManager.getInstance().removeActivity(this);
        super.finish();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();
}
