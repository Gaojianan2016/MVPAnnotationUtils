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

import com.gjn.mvpannotationlibrary.utils.MvpLog;
import com.gjn.mvpannotationlibrary.utils.ToastUtils;
import com.gjn.mvpannotationlibrary.utils.ViewUtils;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author gjn
 * @time 2018/7/27 14:26
 */

public abstract class BaseFragment extends Fragment implements IEvent {

    protected Fragment mFragment;
    protected Activity mActivity;
    protected Bundle mBundle;
    protected View mView;
    private CopyOnWriteArrayList<BaseDialogFragment> mDialogFragments;
    private BaseDialogFragment.OnDialogCancelListener mOnDialogCancelListener;

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
            mView = inflater.inflate(getLayoutId(), container, false);
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
        mDialogFragments = new CopyOnWriteArrayList<>();
        mOnDialogCancelListener = new BaseDialogFragment.OnDialogCancelListener() {
            @Override
            public void cancel(BaseDialogFragment dialogFragment) {
                MvpLog.i("手动关闭dialog " + dialogFragment);
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
        dialogFragment.show(getChildFragmentManager(), dialogFragment.getTag());
        MvpLog.i("显示dialog " + dialogFragment);
    }

    protected void dismiss(BaseDialogFragment dialogFragment) {
        mDialogFragments.remove(dialogFragment);
        dialogFragment.dismissAllowingStateLoss();
        MvpLog.i("关闭dialog " + dialogFragment);
    }

    @Override
    public void onDestroyView() {
        for (BaseDialogFragment dialogFragment : mDialogFragments) {
            dialogFragment.clearOnDialogCancelListenerAll();
        }
        mDialogFragments.clear();
        super.onDestroyView();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();
}
