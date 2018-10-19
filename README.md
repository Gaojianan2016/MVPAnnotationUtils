# MVPAnnotationUtils
自定义MVP注解版

```
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}


dependencies {
    implementation 'com.github.Gaojianan2016:MVPAnnotationUtils:1.0.8'
}
```

Activity1使用
```
package com.gjn.mvpannotationutils;

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
    BaseDialogFragment dialogFragment2;

    @Override
    protected void init() {
        super.init();
        mDialogFragment = BaseDialogFragment
                .newInstance(new AlertDialog.Builder(mActivity).setView(R.layout.dialog_test));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.tv_main)).setText("第一个页面");

        dialogFragment1 = BaseDialogFragment
                .newInstance(new AlertDialog.Builder(mActivity)
                        .setView(R.layout.dialog_test), 0);

        TextView textView = new TextView(mActivity);
        textView.setText("我是dialog");
        dialogFragment2 = BaseDialogFragment
                .newInstance(new AlertDialog.Builder(mActivity)
                        .setView(textView));
    }

    @Override
    protected void initData() {
        findViewById(R.id.tv_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.success();
                show(0);
                show2(1);
                show3(2);
                show(3);
                show2(4);
                show3(5);
                dismiss(6);
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
        }, i*1000);
    }

    private void show2(int i) {
        findViewById(R.id.btn_main).postDelayed(new Runnable() {
            @Override
            public void run() {
                showDialog(dialogFragment2);
            }
        }, i*1000);
    }

    private void show3(int i) {
        findViewById(R.id.btn_main).postDelayed(new Runnable() {
            @Override
            public void run() {
                showProgressUI(true);
            }
        }, i*1000);
    }

    private void dismiss(int i) {
        findViewById(R.id.btn_main).postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissDialogAll();
            }
        }, i*1000);
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
```

Activity2使用
```
package com.gjn.mvpannotationutils;

import android.view.View;

import com.gjn.mvpannotationlibrary.base.BaseMvpActivity;
import com.gjn.mvpannotationlibrary.utils.BindPresenter;
import com.gjn.mvpannotationlibrary.utils.BindPresenters;

public class MainActivity2 extends BaseMvpActivity<MainPresenter> implements IMainView {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        findViewById(R.id.tv_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().success();
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
```

MainPresenter.java
```
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
```

MainModel.java
```
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
```

IMainView.java
```
package com.gjn.mvpannotationutils;

import com.gjn.mvpannotationlibrary.base.IMvpView;

/**
 * @author gjn
 * @time 2018/8/3 15:07
 */

public interface IMainView extends IMvpView{
    void success();
}
```
