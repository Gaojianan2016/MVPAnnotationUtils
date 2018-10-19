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

import com.gjn.mvpannotationlibrary.utils.Log;
import com.gjn.mvpannotationlibrary.utils.ToastUtils;
import com.gjn.mvpannotationlibrary.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gjn
 * @time 2018/7/27 14:26
 */

public abstract class BaseFragment extends Fragment implements IEvent {

    protected Fragment mFragment;
    protected Activity mActivity;
    protected Bundle mBundle;
    protected View mView;
    protected boolean mIsShowLoadingDialog;
    private List<BaseDialogFragment> mDialogFragments;
    private BaseDialogFragment.OnDialogCancelListener mOnDialogCancelListener;
    private BaseDialogFragment mLoadingDialog;

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
            mView = inflater.inflate(getLayoutId(), null);
            init();
            initDialogFragment();
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

    private void initDialogFragment() {
        mDialogFragments = new ArrayList<>();
        mOnDialogCancelListener = new BaseDialogFragment.OnDialogCancelListener() {
            @Override
            public void cancel(BaseDialogFragment dialogFragment) {
                Log.i("手动关闭dialog " + dialogFragment);
                mDialogFragments.remove(dialogFragment);
            }
        };
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

    protected void showLoadingDialog(BaseDialogFragment loadingDialog) {
        if (!mIsShowLoadingDialog) {
            mIsShowLoadingDialog = true;
            mLoadingDialog = loadingDialog;
            showDialog(mLoadingDialog);
        }
    }

    protected void dismiss(BaseDialogFragment dialogFragment) {
        if (dialogFragment == mLoadingDialog) {
            mIsShowLoadingDialog = false;
        }
        Log.i("关闭dialog " + dialogFragment);
        dialogFragment.dismissAllowingStateLoss();
    }

    @Override
    public void showDialog(BaseDialogFragment dialogFragment) {
        if (dialogFragment == null) {
            Log.w("mDialogFragment is null.");
            return;
        }
        dialogFragment.setOnDialogCancelListener(mOnDialogCancelListener);
        if (!mDialogFragments.contains(dialogFragment)) {
            mDialogFragments.add(dialogFragment);
            Log.i("显示dialog " + dialogFragment);
            dialogFragment.show(getChildFragmentManager(), dialogFragment.getTag());
        }
    }

    @Override
    public void dismissDialog(BaseDialogFragment dialogFragment) {
        if (mDialogFragments.contains(dialogFragment)) {
            dismiss(dialogFragment);
            mDialogFragments.remove(dialogFragment);
        }
    }

    @Override
    public void dismissDialogAll() {
        for (BaseDialogFragment dialogFragment : mDialogFragments) {
            dismiss(dialogFragment);
        }
        mDialogFragments.clear();
    }

    @Override
    public void onDestroyView() {
        mDialogFragments.clear();
        super.onDestroyView();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();
}
