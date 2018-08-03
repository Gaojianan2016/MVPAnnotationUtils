package com.gjn.mvpannotationutils;

import com.gjn.mvpannotationlibrary.base.BasePresenter;

/**
 * @author gjn
 * @time 2018/8/3 15:01
 */

public class MainPresenter extends BasePresenter<IMainView, MainModel> {

    public void success(){
        if (isAttached()) {
            getModel().success();
        }
    }
}
