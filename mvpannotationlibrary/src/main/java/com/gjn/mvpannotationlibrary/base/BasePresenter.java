package com.gjn.mvpannotationlibrary.base;

import android.app.Activity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author gjn
 * @time 2018/8/1 16:51
 */

public class BasePresenter<V extends IMvpView, M extends IMvpModel<V>> implements IMvpPresenter<V, M> {

    private V v;
    private M m;
    private Activity activity;

    @Override
    public void onAttached(Activity activity, V view) {
        this.activity = activity;
        v = view;
        createM();
        if (m != null) {
            m.onBind(activity, v);
        }
    }

    private void createM() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        if (type != null) {
            Type[] types = type.getActualTypeArguments();
            if (m == null) {
                Class<M> clazz = (Class<M>) types[1];
                try {
                    m = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDetached() {
        if (m != null) {
            m.unBind();
        }
        activity = null;
        m = null;
        v = null;
    }

    @Override
    public boolean isAttached() {
        return v != null && m != null;
    }

    @Override
    public M getModel() {
        return m;
    }

    @Override
    public V getMvpView() {
        return v;
    }

    @Override
    public Activity getActivity() {
        return activity;
    }

}
