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

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author gjn
 * @time 2018/7/25 17:35
 */

public abstract class BaseActivity extends AppCompatActivity implements IEvent {

    protected Context mContext;
    protected Activity mActivity;
    protected Bundle mBundle;
    private CopyOnWriteArrayList<BaseDialogFragment> mDialogFragments;
    private BaseDialogFragment.OnDialogCancelListener mOnDialogCancelListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MvpLog.d("onCreate " + getClass().getSimpleName());
        preCreate();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        mContext = this;
        mActivity = this;
        mBundle = getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras();
        AppManager.getInstance().addActivity(this);

        init();
        initDialogFragment();
        initView();
        initData();
    }

    protected void preCreate() {

    }

    protected void init() {

    }

    private void initDialogFragment() {
        mDialogFragments = new CopyOnWriteArrayList<>();
        mOnDialogCancelListener = new BaseDialogFragment.OnDialogCancelListener() {
            @Override
            public void cancel(BaseDialogFragment dialogFragment) {
                MvpLog.i("手动关闭dialog " + dialogFragment);
                mDialogFragments.remove(dialogFragment);
            }
        };
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
    public void showDialog(BaseDialogFragment dialogFragment) {
        if (dialogFragment == null) {
            MvpLog.w("mDialogFragment is null.");
            return;
        }
        dialogFragment.setOnDialogCancelListener(mOnDialogCancelListener);
        if (!mDialogFragments.contains(dialogFragment)) {
            show(dialogFragment);
        } else {
            dismissDialog(dialogFragment);
            show(dialogFragment);
        }
    }

    @Override
    public void dismissDialog(BaseDialogFragment dialogFragment) {
        if (mDialogFragments.contains(dialogFragment)) {
            dismiss(dialogFragment);
        }
    }

    @Override
    public void dismissDialogAll() {
        for (BaseDialogFragment dialogFragment : mDialogFragments) {
            dismiss(dialogFragment);
        }
        mDialogFragments.clear();
    }

    private void show(BaseDialogFragment dialogFragment) {
        mDialogFragments.add(dialogFragment);
        dialogFragment.show(getSupportFragmentManager(), dialogFragment.getTag());
        MvpLog.i("显示dialog " + dialogFragment);
    }

    private void dismiss(BaseDialogFragment dialogFragment) {
        mDialogFragments.remove(dialogFragment);
        dialogFragment.dismissAllowingStateLoss();
        MvpLog.i("关闭dialog " + dialogFragment);
    }

    @Override
    public void finish() {
        AppManager.getInstance().removeActivity(this);
        super.finish();
    }

    @Override
    protected void onDestroy() {
        MvpLog.d("onDestroy " + getClass().getSimpleName());
        mDialogFragments.clear();
        super.onDestroy();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();
}
