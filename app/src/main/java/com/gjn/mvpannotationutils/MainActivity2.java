package com.gjn.mvpannotationutils;

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.gjn.mvpannotationlibrary.base.BaseDialogFragment;
import com.gjn.mvpannotationlibrary.base.BaseMvpActivity;

public class MainActivity2 extends BaseMvpActivity<MainPresenter> implements IMainView {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.tv_main)).setText("第二个页面");

        findViewById(R.id.tv_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().success();
            }
        });

        findViewById(R.id.btn_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextActivity(MainActivity3.class);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void success() {
        showToast("测试成功22222");
    }
}
