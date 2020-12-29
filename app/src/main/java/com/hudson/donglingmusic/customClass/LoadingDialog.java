package com.hudson.donglingmusic.customClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.hudson.donglingmusic.R;

/**
 * Created by Hudson on 2020/3/17.
 */
public class LoadingDialog extends AbsDialog {

    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected View initView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.custom_view_load_state,null);
    }
}
