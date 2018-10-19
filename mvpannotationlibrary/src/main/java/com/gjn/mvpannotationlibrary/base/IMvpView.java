package com.gjn.mvpannotationlibrary.base;

/**
 * @author gjn
 * @time 2018/8/1 16:47
 */

public interface IMvpView {
    void showProgressUI(boolean isShow);

    void fail(String msg);

    void error(Throwable t);
}
