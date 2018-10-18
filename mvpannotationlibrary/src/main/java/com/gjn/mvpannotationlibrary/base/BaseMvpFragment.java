package com.gjn.mvpannotationlibrary.base;

/**
 * @author gjn
 * @time 2018/8/2 10:40
 */

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment implements IMvpView {

    protected MvpBindAnnotations mvpBindAnnotations;
    protected BaseDialogFragment mDialogFragment;

    @Override
    protected void init() {
        super.init();
        mvpBindAnnotations = MvpBindAnnotations.newInstance(mActivity, mFragment);
    }

    protected P getPresenter() {
        return mvpBindAnnotations.getPresenterItem();
    }

    protected P getPresenter(int i) {
        return mvpBindAnnotations.getPresenterItem(i);
    }

    @Override
    public void onDestroy() {
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
    public void error(Throwable t) {

    }
}
