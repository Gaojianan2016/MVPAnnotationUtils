package com.gjn.mvpannotationlibrary.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gjn
 * @time 2018/8/1 17:14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindPresenters {
    Class<?>[] presenters();
}
