package com.gjn.mvpannotationutils;

import android.widget.TextView;

import com.gjn.mvpannotationlibrary.base.BaseMvpActivity;

public class MainActivity3 extends BaseMvpActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.tv_main)).setText("第3个页面");
    }

    @Override
    protected void initData() {

    }
}
