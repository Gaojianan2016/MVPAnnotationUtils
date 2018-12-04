package com.gjn.mvpannotationlibrary.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gjn
 * @time 2018/9/29 10:19
 */

public abstract class BaseDialogFragment extends DialogFragment {

    public static final int WRAP_CONTENT = ViewPager.LayoutParams.WRAP_CONTENT;
    public static final int MATCH_PARENT = ViewPager.LayoutParams.MATCH_PARENT;

    private static final float DIMAMOUT = 0.7f;

    private boolean isCloseOnTouchOutside = false;
    private boolean isShowAnimations = false;
    private boolean isTransparent = false;
    private int windowAnimations = -1;
    private float dimAmout = DIMAMOUT;
    private int width = WRAP_CONTENT;
    private int height = WRAP_CONTENT;
    private int gravity = Gravity.CENTER;
    private OnDialogCancelListener onDialogCancelListener;
    private List<OnDialogCancelListener> onDialogCancelListeners;

    public BaseDialogFragment setOnDialogCancelListener(OnDialogCancelListener onDialogCancelListener) {
        this.onDialogCancelListener = onDialogCancelListener;
        return this;
    }

    public void clearOnDialogCancelListenerAll() {
        onDialogCancelListeners.clear();
    }

    public BaseDialogFragment addOnDialogCancelListener(OnDialogCancelListener onDialogCancelListener) {
        if (onDialogCancelListeners == null) {
            onDialogCancelListeners = new ArrayList<>();
        }
        if (!onDialogCancelListeners.contains(onDialogCancelListener) && onDialogCancelListener != null) {
            onDialogCancelListeners.add(onDialogCancelListener);
        }
        return this;
    }

    public BaseDialogFragment setShowAnimations(boolean showAnimations) {
        isShowAnimations = showAnimations;
        return this;
    }

    public BaseDialogFragment setWindowAnimations(int windowAnimations) {
        if (windowAnimations != -1) {
            setShowAnimations(true);
        }
        this.windowAnimations = windowAnimations;
        return this;
    }

    public BaseDialogFragment setCloseOnTouchOutside(boolean closeOnTouchOutside) {
        isCloseOnTouchOutside = closeOnTouchOutside;
        return this;
    }

    public BaseDialogFragment setTransparent(boolean transparent) {
        isTransparent = transparent;
        return this;
    }

    public BaseDialogFragment setDimAmout(float dimAmout) {
        this.dimAmout = dimAmout;
        return this;
    }

    public BaseDialogFragment setWidth(int width) {
        this.width = width;
        return this;
    }

    public BaseDialogFragment setHeight(int height) {
        this.height = height;
        return this;
    }

    public BaseDialogFragment setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        try {
            super.onStart();
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getLayoutId() != 0) {
            ViewHolder holder = ViewHolder.create(getActivity(), getLayoutId(), container);
            convertView(holder, this);
            return holder.getView();
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getLayoutId() == 0 && getBuilder() != null) {
            return getBuilder().create();
        }
        return super.onCreateDialog(savedInstanceState);
    }

    public abstract int getLayoutId();

    public abstract void convertView(ViewHolder holder, BaseDialogFragment dialogFragment);

    public abstract AlertDialog.Builder getBuilder();

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (onDialogCancelListener != null) {
            onDialogCancelListener.cancel(this);
        }
        if (onDialogCancelListeners != null) {
            for (OnDialogCancelListener listener : onDialogCancelListeners) {
                listener.cancel(this);
            }
        }
    }

    public interface OnDialogCancelListener {
        void cancel(BaseDialogFragment dialogFragment);
    }
}
