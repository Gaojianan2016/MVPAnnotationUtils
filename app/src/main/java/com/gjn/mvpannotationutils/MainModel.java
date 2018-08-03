package com.gjn.mvpannotationutils;

import com.gjn.mvpannotationlibrary.base.BaseModel;

/**
 * @author gjn
 * @time 2018/8/3 15:08
 */

public class MainModel extends BaseModel<IMainView> {

    public void success(){
        getMvpView().success();
    }
}
