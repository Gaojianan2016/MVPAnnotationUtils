package com.gjn.mvpannotationlibrary.utils;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author gjn
 * @time 2018/11/2 18:05
 */

public class ReflexUtils {
    private static final String TAG = "ReflexUtils";

    public static <T> Class<T> getClass(String name) {
        Class<T> clz = null;
        try {
            clz = (Class<T>) Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clz;
    }

    public static <T> T createObj(String name) {
        Class<T> clz = getClass(name);
        return createObj(clz, null, null);
    }

    public static <T> T createObj(String name, Class<?>[] parameterTypes, Object[] initargs) {
        Class<T> clz = getClass(name);
        return createObj(clz, parameterTypes, initargs);
    }

    public static <T> T createObj(Class<T> clz) {
        return createObj(clz, null, null);
    }

    public static <T> T createObj(Class<T> clz, Class<?>[] parameterTypes, Object[] initargs) {
        if (clz == null) {
            Log.e(TAG, "class is null.");
            return null;
        }
        try {
            Constructor constructor = clz.getConstructor(parameterTypes);
            T t = (T) constructor.newInstance(initargs);
            return t;
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "找不到方法", e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "非法访问", e);
        } catch (InstantiationException e) {
            Log.e(TAG, "实例化失败", e);
        } catch (InvocationTargetException e) {
            Log.e(TAG, "调用异常", e);
        }
        return null;
    }

    public static Object doDeclaredMethod(Object o, String name) {
        return doDeclaredMethod(o, name, null, null);
    }

    public static Object doDeclaredMethod(Object o, String name, Class<?>[] parameterTypes, Object[] args) {
        if (o == null) {
            Log.e(TAG, "Object is null.");
            return null;
        }
        try {
            Method method = o.getClass().getDeclaredMethod(name, parameterTypes);
            if (!"public".equals(Modifier.toString(method.getModifiers()))) {
                method.setAccessible(true);
            }
            return method.invoke(o, args);
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "找不到方法", e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "非法访问", e);
        } catch (InvocationTargetException e) {
            Log.e(TAG, "调用异常", e);
        }
        return null;
    }

    public static void setDeclaredField(Object o, String name, Object value) {
        if (o == null) {
            Log.e(TAG, "Object is null.");
            return;
        }
        try {
            Field field = o.getClass().getDeclaredField(name);
            setField(o, field, value);
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "找不到参数", e);
        }
    }

    public static void setField(Object o, String name, Object value) {
        if (o == null) {
            Log.e(TAG, "Object is null.");
            return;
        }
        try {
            Field field = o.getClass().getField(name);
            setField(o, field, value);
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "找不到参数", e);
        }
    }

    public static void setField(Object o, Field field, Object value) {
        if (o == null) {
            Log.e(TAG, "Object is null.");
            return;
        }
        try {
            if (!"public".equals(Modifier.toString(field.getModifiers()))) {
                field.setAccessible(true);
            }
            field.set(o, value);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "非法访问", e);
        }
    }
}
