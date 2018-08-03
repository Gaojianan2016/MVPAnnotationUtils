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
    implementation 'com.github.Gaojianan2016:MVPAnnotationUtils:1.0.0'
}
```

Activity使用
```
package com.gjn.mvpannotationutils;

import android.view.View;

import com.gjn.mvpannotationlibrary.base.BaseMvpActivity;
import com.gjn.mvpannotationlibrary.utils.BindPresenter;
import com.gjn.mvpannotationlibrary.utils.BindPresenters;

@BindPresenters(presenters = {MainPresenter.class})
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
