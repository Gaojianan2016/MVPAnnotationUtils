package com.gjn.mvpannotationutils;

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.gjn.mvpannotationlibrary.base.BaseDialogFragment;
import com.gjn.mvpannotationlibrary.base.BaseMvpActivity;
import com.gjn.mvpannotationlibrary.utils.BindPresenter;
import com.gjn.mvpannotationlibrary.utils.BindPresenters;

@BindPresenters({MainPresenter.class})
public class MainActivity extends BaseMvpActivity implements IMainView {

    @BindPresenter
    MainPresenter presenter;

    @Override
    protected void init() {
        super.init();
        dialogFragment = BaseDialogFragment.newInstance(new AlertDialog.Builder(mActivity)
                .setView(new TextView(mActivity)));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.tv_main)).setText("第一个页面");

        findViewById(R.id.tv_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.success();
            }
        });

        findViewById(R.id.btn_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNextActivity(MainActivity2.class);
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
