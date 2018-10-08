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
    implementation 'com.github.Gaojianan2016:MVPAnnotationUtils:1.0.4'
}
```

Activity1使用
```
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

        findViewById(R.id.btn_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextActivity(MainActivity2.class);
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
