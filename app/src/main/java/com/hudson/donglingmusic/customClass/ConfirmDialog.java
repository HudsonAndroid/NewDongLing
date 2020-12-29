package com.hudson.donglingmusic.customClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hudson.donglingmusic.R;

/**
 * Created by Hudson on 2020/3/17.
 */
public class ConfirmDialog extends AbsDialog {
    private TextView mOk,mCancel,mTips;

    public ConfirmDialog(Context context) {
        super(context);
        setCancelable(false);
    }

    @Override
    protected View initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null);
        mTips = (TextView) inflate.findViewById(R.id.tv_tips);
        mOk = (TextView) inflate.findViewById(R.id.tv_ok);
        mCancel = (TextView) inflate.findViewById(R.id.tv_cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return inflate;
    }

    public void setOkClickListener(View.OnClickListener listener){
        mOk.setOnClickListener(listener);
    }

    public void setCancelClickListener(View.OnClickListener listener){
        mCancel.setOnClickListener(listener);
    }

    public void setTips(String tips){
        mTips.setText(tips);
    }

    public void setTips(int tipsId){
        mTips.setText(tipsId);
    }

    public void setOkStr(int strId){
        mOk.setText(strId);
    }

    public void setCancelStr(int strId){
        mCancel.setText(strId);
    }
}
