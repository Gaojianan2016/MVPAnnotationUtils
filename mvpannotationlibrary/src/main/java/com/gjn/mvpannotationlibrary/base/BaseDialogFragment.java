package com.gjn.mvpannotationlibrary.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author gjn
 * @time 2018/9/29 10:19
 */

public class BaseDialogFragment extends DialogFragment {

    private static final int WRAP_CONTENT = ViewPager.LayoutParams.WRAP_CONTENT;

    private static final float DIMAMOUT = 1.0f;

    private AlertDialog.Builder builder;
    private boolean isCloseOnTouchOutside = false;
    private boolean isShowAnimations = false;
    private boolean isTransparent = false;
    private int windowAnimations = -1;
    private float dimAmout = DIMAMOUT;
    private int width = WRAP_CONTENT;
    private int height = WRAP_CONTENT;
    private int gravity = Gravity.CENTER;
    private OnDialogCancelListener onDialogCancelListener;

    public static BaseDialogFragment newInstance(AlertDialog.Builder builder) {
        return newInstance(builder, DIMAMOUT);
    }

    public static BaseDialogFragment newInstance(AlertDialog.Builder builder, boolean isTransparent) {
        return newInstance(builder, isTransparent, DIMAMOUT);
    }

    public static BaseDialogFragment newInstance(AlertDialog.Builder builder, float dimAmout) {
        return newInstance(builder, false, dimAmout);
    }

    public static BaseDialogFragment newInstance(AlertDialog.Builder builder, boolean isTransparent,
                                                 float dimAmout) {
        return newInstance(builder, isTransparent, dimAmout, Gravity.CENTER, WRAP_CONTENT, WRAP_CONTENT);
    }

    public static BaseDialogFragment newInstance(AlertDialog.Builder builder, boolean isTransparent,
                                                 float dimAmout, int gravity, int width, int height) {
        BaseDialogFragment instance = new BaseDialogFragment();
        instance.builder = builder;
        instance.isTransparent = isTransparent;
        instance.dimAmout = dimAmout;
        instance.gravity = gravity;
        instance.width = width;
        instance.height = height;
        return instance;
    }

    public void setOnDialogCancelListener(OnDialogCancelListener onDialogCancelListener) {
        this.onDialogCancelListener = onDialogCancelListener;
    }

    public void setShowAnimations(boolean showAnimations) {
        isShowAnimations = showAnimations;
    }

    public void setWindowAnimations(int windowAnimations) {
        if (windowAnimations != -1) {
            setShowAnimations(true);
        }
        this.windowAnimations = windowAnimations;
    }

    public void setCloseOnTouchOutside(boolean closeOnTouchOutside) {
        isCloseOnTouchOutside = closeOnTouchOutside;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().setCanceledOnTouchOutside(isCloseOnTouchOutside);
        Window window = getDialog().getWindow();
        if (window != null) {
            if (isTransparent) {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            WindowManager.LayoutParams params = window.getAttributes();
            if (dimAmout != DIMAMOUT) {
                params.dimAmount = dimAmout;
            }
            if (windowAnimations != -1 && isShowAnimations) {
                params.windowAnimations = windowAnimations;
            }
            params.width = width;
            params.height = height;
            params.gravity = gravity;
            window.setAttributes(params);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (builder != null) {
            return builder.create();
        }
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (onDialogCancelListener != null) {
            onDialogCancelListener.cancel(this);
        }
    }

    public interface OnDialogCancelListener {
        void cancel(BaseDialogFragment dialogFragment);
    }
}
