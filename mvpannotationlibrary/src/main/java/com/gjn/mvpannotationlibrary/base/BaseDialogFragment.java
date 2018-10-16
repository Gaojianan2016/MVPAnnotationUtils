package com.gjn.mvpannotationlibrary.base;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author gjn
 * @time 2018/9/29 10:19
 */

public class BaseDialogFragment extends DialogFragment {

    private AlertDialog.Builder builder;
    private boolean isShowAnimations = false;
    private boolean isTransparent = false;
    private int windowAnimations = -1;
    private int width = ViewPager.LayoutParams.WRAP_CONTENT;
    private int height = ViewPager.LayoutParams.WRAP_CONTENT;
    private int gravity = Gravity.CENTER;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //无法点击屏幕取消Dialog
        getDialog().setCanceledOnTouchOutside(false);
        Window window = getDialog().getWindow();
        if (window != null) {
            if (isTransparent) {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = gravity;
            if (windowAnimations > 0 && isShowAnimations) {
                params.windowAnimations = windowAnimations;
            }
            params.width = width;
            params.height = height;
            window.setAttributes(params);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (builder != null) {
            return builder.create();
        }
        return super.onCreateDialog(savedInstanceState);
    }

    public static BaseDialogFragment newInstance(AlertDialog.Builder builder){
        return newInstance(builder, false,
                ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT);
    }

    public static BaseDialogFragment newInstance(AlertDialog.Builder builder, boolean isTransparent){
        return newInstance(builder, isTransparent,
                ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT);
    }

    public static BaseDialogFragment newInstance(AlertDialog.Builder builder, int width, int height){
        return newInstance(builder, false, width, height);
    }

    public static BaseDialogFragment newInstance(AlertDialog.Builder builder, boolean isTransparent,
                                                 int width, int height){
        return new DialogBuilder()
                .builder(builder)
                .isTransparent(isTransparent)
                .width(width)
                .height(height)
                .create();
    }

    public static class DialogBuilder{
        private BaseDialogFragment fragment;

        public DialogBuilder() {
            fragment = new BaseDialogFragment();
        }

        public DialogBuilder builder(AlertDialog.Builder builder){
            fragment.builder = builder;
            return this;
        }

        public DialogBuilder width(int width){
            fragment.width = width;
            return this;
        }

        public DialogBuilder height(int height){
            fragment.height = height;
            return this;
        }

        public DialogBuilder isTransparent(boolean isTransparent){
            fragment.isTransparent = isTransparent;
            return this;
        }

        public DialogBuilder gravity(int gravity){
            fragment.gravity = gravity;
            return this;
        }

        public DialogBuilder windowAnimations(int style){
            fragment.windowAnimations = style;
            return this;
        }

        public DialogBuilder isShowAnimations(boolean b){
            fragment.isShowAnimations = b;
            return this;
        }

        public BaseDialogFragment create(){
            return fragment;
        }
    }
}
