package com.gjn.mvpannotationutils;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.gjn.mvpannotationlibrary.base.BaseDialogFragment;
import com.gjn.mvpannotationlibrary.base.BaseMvpActivity;
import com.gjn.mvpannotationlibrary.utils.BindPresenter;
import com.gjn.mvpannotationlibrary.utils.BindPresenters;

@BindPresenters({MainPresenter.class, MainPresenter2.class})
public class MainActivity extends BaseMvpActivity implements IMainView, IMainView2 {

    @BindPresenter
    MainPresenter presenter;
    @BindPresenter
    MainPresenter2 presenter2;

    BaseDialogFragment dialogFragment1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.tv_main)).setText("第一个页面");

        dialogFragment1 = BaseDialogFragment.newInstance(new AlertDialog.Builder(mActivity)
                .setView(R.layout.dialog_test), 0);
    }

    private AlertDialog.Builder createBuilder(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_test, null);
        TextView textView = view.findViewById(R.id.tv_dialog);
        textView.setText("我是第二个");
        builder.setView(view);
        return builder;
    }

    @Override
    protected void initData() {
        findViewById(R.id.tv_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.success();
                show(0);
                show2(1);
                show(2);
                show2(3);
                dismiss(4);
            }
        });

        findViewById(R.id.btn_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter2.success();
                toNextActivity(MainActivity2.class);
            }
        });
    }

    private void show(int i) {
        findViewById(R.id.btn_main).postDelayed(new Runnable() {
            @Override
            public void run() {
                showDialog(dialogFragment1);
            }
        }, i * 1000);
    }

    private void show2(int i) {
        findViewById(R.id.btn_main).postDelayed(new Runnable() {
            @Override
            public void run() {
                showLoadingDialog(BaseDialogFragment.newInstance(createBuilder(mActivity)));
            }
        }, i * 1000);
    }

    private void dismiss(int i) {
        findViewById(R.id.btn_main).postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissDialogAll();
            }
        }, i * 1000);
    }


    @Override
    public void success() {
        showToast("测试成功");
    }

    @Override
    public void success2() {
        showToast("跳转测试");
    }
}
