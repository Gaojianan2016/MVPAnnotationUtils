package com.gjn.mvpannotationutils;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gjn.mvpannotationlibrary.base.BaseMvpActivity;
import com.gjn.mvpannotationlibrary.utils.AppManager;
import com.gjn.mvpannotationlibrary.utils.MvpLog;

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

        for (Activity activity : AppManager.getActivities()) {
            MvpLog.e( "name = " + activity.getClass().getSimpleName());
        }

        findViewById(R.id.tv_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getInstance().finishActivity(MainActivity2.class);
                toNextActivity(MainActivity.class);
            }
        });

        findViewById(R.id.btn_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getInstance().finishAllActivity();
            }
        });
    }
}
