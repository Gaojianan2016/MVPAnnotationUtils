package com.gjn.mvpannotationlibrary.base;

/**
 * @author gjn
 * @time 2018/8/2 10:40
 */

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment implements IMvpView {

    protected MvpBindAnnotations mvpBindAnnotations;

    @Override
    protected void init() {
        super.init();
        mvpBindAnnotations = MvpBindAnnotations.newInstance(mActivity, mFragment);
    }

    protected P getPresenter() {
        return mvpBindAnnotations.getPresenter();
    }

    protected P getPresenter(int i) {
        return mvpBindAnnotations.getPresenter(i);
    }

    @Override
    public void onDestroy() {
        mvpBindAnnotations.detachedPresenter();
        super.onDestroy();
    }

    @Override
    public void showProgressUI(boolean isShow) {

    }

    @Override
    public void error(Throwable t) {

    }
}
