package com.gjn.mvpannotationutils;

import android.view.View;

import com.gjn.mvpannotationlibrary.base.BaseMvpActivity;
import com.gjn.mvpannotationlibrary.utils.BindPresenter;
import com.gjn.mvpannotationlibrary.utils.BindPresenters;

@BindPresenters({MainPresenter.class})
public class MainActivity extends BaseMvpActivity implements IMainView {

    @BindPresenter
    MainPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        findViewById(R.id.tv_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.success();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void success() {
        showToast("测试成功");
    }
}
