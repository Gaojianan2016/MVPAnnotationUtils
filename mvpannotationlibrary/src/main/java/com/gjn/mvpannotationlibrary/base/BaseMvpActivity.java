package com.gjn.mvpannotationlibrary.base;

/**
 * @author gjn
 * @time 2018/8/1 16:57
 */

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements IMvpView {

    protected MvpBindAnnotations mvpBindAnnotations;

    @Override
    protected void init() {
        super.init();
        mvpBindAnnotations = MvpBindAnnotations.getInstance(mActivity);
    }

    protected P getPresenter() {
        return getPresenter(0);
    }

    protected P getPresenter(int i) {
        return mvpBindAnnotations.getPresenter(i);
    }

    @Override
    protected void onDestroy() {
        mvpBindAnnotations.detachedAll();
        super.onDestroy();
    }

    @Override
    public void showProgressUI(boolean isShow) {

    }

    @Override
    public void error(Throwable t) {

    }
}
