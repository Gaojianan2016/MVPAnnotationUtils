package com.gjn.mvpannotationlibrary.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gjn.easydialoglibrary.EasyDFragmentManager;
import com.gjn.easydialoglibrary.base.BaseDFragment;
import com.gjn.mvpannotationlibrary.utils.ToastUtils;
import com.gjn.mvpannotationlibrary.utils.ViewUtils;

/**
 * @author gjn
 * @time 2018/7/27 14:26
 */

public abstract class BaseFragment extends Fragment implements IEvent {

    protected Fragment mFragment;
    protected Activity mActivity;
    protected Bundle mBundle;
    protected View mView;
    protected EasyDFragmentManager manager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        preCreate();
        super.onCreate(savedInstanceState);

        mFragment = this;
        mActivity = getActivity();
        mBundle = getArguments() == null ? new Bundle() : getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            manager = new EasyDFragmentManager(this);
            mView = inflater.inflate(getLayoutId(), container, false);
            init();
            initView();
            initData();
        }
        ViewUtils.removeParent(mView);
        return mView;
    }

    protected void preCreate() {
    }

    protected void init() {
    }


    public final <T extends View> T findViewById(int id) {
        return mView.findViewById(id);
    }

    @Override
    public void showNextActivity(Class<?> cls) {
        Intent intent = new Intent(mActivity, cls);
        startActivity(intent);
    }

    @Override
    public void showNextActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(mActivity, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void toNextActivity(Class<?> cls) {
        showNextActivity(cls);
        mActivity.finish();
    }

    @Override
    public void toNextActivity(Class<?> cls, Bundle bundle) {
        showNextActivity(cls, bundle);
        mActivity.finish();
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(mActivity, msg);
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
    public void showOnlyDialog(BaseDFragment dialogFragment) {
        manager.showOnlyDialog(dialogFragment);
    }

    @Override
    public void dismissDialogAll() {
        manager.clearDialog();
    }

    @Override
    public void onDestroyView() {
        dismissDialogAll();
        super.onDestroyView();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();
}
