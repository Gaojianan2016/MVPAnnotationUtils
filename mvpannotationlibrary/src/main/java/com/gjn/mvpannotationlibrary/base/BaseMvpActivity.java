package com.gjn.mvpannotationlibrary.base;

/**
 * @author gjn
 * @time 2018/8/1 16:57
 */

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements IMvpView {

    protected MvpBindAnnotations mvpBindAnnotations;
    protected BaseDialogFragment mDialogFragment;

    @Override
    protected void init() {
        super.init();
        mvpBindAnnotations = MvpBindAnnotations.newInstance(mActivity);
    }

    protected P getPresenter() {
        return mvpBindAnnotations.getPresenterItem();
    }

    protected P getPresenter(int i) {
        return mvpBindAnnotations.getPresenterItem(i);
    }

    @Override
    protected void onDestroy() {
        mvpBindAnnotations.detachedPresenter();
        mDialogFragment = null;
        super.onDestroy();
    }

    @Override
    public void showProgressUI(boolean isShow) {
        if (isShow) {
            showDialog(mDialogFragment);
        }else {
            dismissDialog(mDialogFragment);
        }
    }

    @Override
    public void error(Throwable t) {}
}
