package com.gjn.mvpannotationlibrary.base;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.gjn.mvpannotationlibrary.utils.AnnotationsUtils;
import com.gjn.mvpannotationlibrary.utils.BindPresenter;
import com.gjn.mvpannotationlibrary.utils.BindPresenters;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gjn
 * @time 2018/8/1 18:25
 */

public class MvpBindAnnotations {
    private static final String TAG = "MvpBindAnnotations";

    private Activity activity;
    private Fragment fragment;
    private Object object;
    private Map<String, BasePresenter> presentersMap;
    private List<BasePresenter> presenters;

    private MvpBindAnnotations(Activity activity, Fragment fragment) {
        if (activity != null) {
            this.activity = activity;
            object = this.activity;
        }
        if (fragment != null) {
            this.fragment = fragment;
            object = this.fragment;
            Log.d(TAG, "bind fragment");
        } else {
            Log.d(TAG, "bind activity");
        }
        presentersMap = new HashMap<>();
        presenters = new ArrayList<>();
        savePresenters();
        bindPresenter();
        attachedPresenter();
    }

    public static MvpBindAnnotations newInstance(Activity activity) {
        return new MvpBindAnnotations(activity, null);
    }

    public static MvpBindAnnotations newInstance(Activity activity, Fragment fragment) {
        return new MvpBindAnnotations(activity, fragment);
    }

    private void savePresenters() {
        try {
            BindPresenters ps = AnnotationsUtils.getAnnotations(object, BindPresenters.class);
            if (ps != null) {
                Log.d(TAG, "save Annotations: ");
                for (Class aClass : ps.value()) {
                    String name = aClass.getCanonicalName();
                    BasePresenter bp = (BasePresenter) aClass.newInstance();
                    presentersMap.put(name, bp);
                    presenters.add(bp);
                    Log.d(TAG, "  " + bp.getClass().getSimpleName());
                }
            } else {
                if (object.getClass().getGenericSuperclass() instanceof BasePresenter) {
                    ParameterizedType type = (ParameterizedType) object.getClass().getGenericSuperclass();
                    if (type != null) {
                        Type[] types = type.getActualTypeArguments();
                        Log.d(TAG, "save Type: ");
                        for (Type tClass : types) {
                            Class aClass = (Class) tClass;
                            String name = aClass.getCanonicalName();
                            BasePresenter bp = (BasePresenter) aClass.newInstance();
                            presentersMap.put(name, bp);
                            presenters.add(bp);
                            Log.d(TAG, "  " + bp.getClass().getSimpleName());
                        }
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void bindPresenter() {
        List<Field> fields = AnnotationsUtils.getField(object, BindPresenter.class);
        for (Field field : fields) {
            String name = field.getType().getName();
            BasePresenter bp = presentersMap.get(name);
            if (bp != null) {
                try {
                    field.setAccessible(true);
                    field.set(object, bp);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void attachedPresenter() {
        for (BasePresenter presenter : getPresenters()) {
            if (presenter != null) {
                presenter.onAttached(activity, (IMvpView) (fragment != null ? fragment : activity));
            }
        }
    }

    public void detachedPresenter() {
        for (BasePresenter presenter : getPresenters()) {
            if (presenter != null) {
                presenter.onDetached();
            }
        }
    }

    public int getPresentersItem() {
        return presenters.size();
    }

    public List<BasePresenter> getPresenters() {
        return presenters;
    }

    public <P extends BasePresenter> P getPresenter() {
        return getPresenter(0);
    }

    public <P extends BasePresenter> P getPresenter(int i) {
        if (getPresentersItem() == 0) {
            return null;
        } else if (i > getPresentersItem() - 1) {
            i = getPresentersItem() - 1;
        }
        return (P) presenters.get(i);
    }
}
