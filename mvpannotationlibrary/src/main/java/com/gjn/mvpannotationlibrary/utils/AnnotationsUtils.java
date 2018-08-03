package com.gjn.mvpannotationlibrary.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gjn
 * @time 2018/8/1 14:17
 */

public class AnnotationsUtils {
    private static final String TAG = "AnnotationsUtils";

    public static boolean checkAnnotations(Object obj, Class<? extends Annotation> annotationCls){
        Class<?> clazz = obj.getClass();
        if (clazz.getAnnotations() != null) {
            if (clazz.isAnnotationPresent(annotationCls)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkAnnotations(Field field, Class<? extends Annotation> annotationCls){
        if (field.getAnnotations() != null) {
            if (field.isAnnotationPresent(annotationCls)) {
                return true;
            }
        }
        return false;
    }

    public static <T extends Annotation> T getAnnotations(Object obj, Class<T> annotationCls){
        if (checkAnnotations(obj, annotationCls)) {
            return obj.getClass().getAnnotation(annotationCls);
        }
        return null;
    }

    public static <T extends Annotation> T getFieldAnnotations(Object obj, Class<T> annotationCls){
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (checkAnnotations(field, annotationCls)) {
                return field.getAnnotation(annotationCls);
            }
        }
        return null;
    }

    public static <T extends Annotation> List<Field> getField(Object obj, Class<T> annotationCls){
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<Field> result = new ArrayList<>();
        for (Field field : fields) {
            if (checkAnnotations(field, annotationCls)) {
                result.add(field);
            }
        }
        return result;
    }

    public static <T extends Annotation> void setFieldAnnotations(Object obj, Class<T> annotationCls,
                                                                  setAnnotations setAnnotations) throws IllegalAccessException {
        setAnnotations.set(obj, getField(obj, annotationCls), getFieldAnnotations(obj, annotationCls));
    }

    public interface setAnnotations{
        void set(Object obj, List<Field> fields, Annotation annotation) throws IllegalAccessException;
    }
}
