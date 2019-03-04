package com.gjn.mvpannotationutils;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gjn.mvpannotationlibrary.base.BaseMvpActivity;
import com.gjn.mvpannotationlibrary.utils.AppManager;
import com.gjn.mvpannotationlibrary.utils.BindPresenter;
import com.gjn.mvpannotationlibrary.utils.BindPresenters;
import com.gjn.mvpannotationlibrary.utils.MvpLog;
import com.shoumi.easydialogfragmentlibrary.NormalDFragment;
import com.shoumi.easydialogfragmentlibrary.base.BaseDFragment;
import com.shoumi.easydialogfragmentlibrary.base.IDFragmentConvertView;

@BindPresenters({MainPresenter.class, MainPresenter2.class})
public class MainActivity extends BaseMvpActivity implements IMainView, IMainView2 {

    @BindPresenter
    MainPresenter presenter;
    @BindPresenter
    MainPresenter2 presenter2;

    BaseDFragment dialogFragment1;
    BaseDFragment dialogFragment2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.tv_main)).setText("第一个页面");

        dialogFragment1 = NormalDFragment.newInstance(R.layout.dialog_test);
        dialogFragment2 = NormalDFragment.newInstance(R.layout.dialog_test, new IDFragmentConvertView() {
            @Override
            public void convertView(com.shoumi.easydialogfragmentlibrary.base.ViewHolder holder, DialogFragment dialogFragment) {
                TextView textView = holder.findView(R.id.tv_dialog);
                textView.setText("我是第二个dialog");
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("woshidierge");
                    }
                });
            }
        });
        dialogFragment2.setDimAmout(0);
        dialogFragment2.setWidth(BaseDFragment.MATCH_PARENT);
    }

    private AlertDialog.Builder createBuilder(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(R.layout.dialog_test);
        return builder;
    }

    private AlertDialog.Builder createBuilder2(Activity activity) {
        //自定义view请不要使用AlertDialog.Builder生成
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_test, null);
        TextView textView = view.findViewById(R.id.tv_dialog);
        textView.setText("我是第二个dialog啊");
        builder.setView(view);
        return builder;
    }

    @Override
    protected void initData() {

        for (Activity activity : AppManager.getActivities()) {
            MvpLog.e("name = " + activity.getClass().getSimpleName());
        }

        findViewById(R.id.tv_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.success();
                show(0);
                show2(2);
                show(4);
                show2(6);
                dismissAll(8);
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
                showDialog(dialogFragment2);
            }
        }, i * 1000);
    }

    private void dismissAll(int i) {
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
