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
        mvpBindAnnotations = MvpBindAnnotations.newInstance(mActivity);
    }

    protected P getPresenter() {
        return mvpBindAnnotations.getPresenter();
    }

    protected P getPresenter(int i) {
        return mvpBindAnnotations.getPresenter(i);
    }

    @Override
    protected void onDestroy() {
        mvpBindAnnotations.detachedPresenter();
        super.onDestroy();
    }

    @Override
    public void showProgressUI(boolean isShow) {
        if (isShow) {
            showDialog(dialogFragment);
        }else {
            dismissDialog();
        }
    }

    @Override
    public void error(Throwable t) {}
}
