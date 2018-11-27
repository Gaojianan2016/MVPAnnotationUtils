package com.gjn.mvpannotationlibrary.base;

import android.support.v7.app.AlertDialog;

/**
 * @author gjn
 * @time 2018/11/27 11:43
 */

public class MvpDialogFragment extends BaseDialogFragment {

    private int viewId;
    private AlertDialog.Builder dialogBuilder;
    private DialogCreateListener create;

    public static MvpDialogFragment newInstance() {
        return newInstance(null);
    }

    public static MvpDialogFragment newInstance(int id, DialogCreateListener create) {
        MvpDialogFragment df = new MvpDialogFragment();
        df.viewId = id;
        df.create = create;
        return df;
    }

    public static MvpDialogFragment newInstance(AlertDialog.Builder builder) {
        MvpDialogFragment df = new MvpDialogFragment();
        df.dialogBuilder = builder;
        return df;
    }

    @Override
    public int getLayoutId() {
        return viewId;
    }

    public void setDialogBuilder(AlertDialog.Builder dialogBuilder) {
        this.dialogBuilder = dialogBuilder;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public void setCreate(DialogCreateListener create) {
        this.create = create;
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialogFragment dialogFragment) {
        if (create != null) {
            create.convertView(holder, dialogFragment);
        }
    }

    @Override
    public AlertDialog.Builder getBuilder() {
        return dialogBuilder;
    }

    public interface DialogCreateListener {
        void convertView(ViewHolder holder, BaseDialogFragment dialogFragment);
    }
}
