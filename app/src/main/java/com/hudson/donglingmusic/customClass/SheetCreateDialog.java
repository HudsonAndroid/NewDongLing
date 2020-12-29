package com.hudson.donglingmusic.customClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.View.InputView;

/**
 * Created by Hudson on 2020/3/27.
 */
public class SheetCreateDialog extends AbsDialog {
    private View mOkBtn;
    private InputView mInputView;

    public SheetCreateDialog(Context context) {
        super(context);
    }

    @Override
    protected View initView(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.dialog_sheet_create, null);
        mOkBtn = root.findViewById(R.id.tv_ok);
        root.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mInputView = (InputView) root.findViewById(R.id.iv_input);
        mInputView.setMaxCount(10);
        return root;
    }

    public void setOnOkClickListener(View.OnClickListener listener){
        mOkBtn.setOnClickListener(listener);
    }

    public String getSheetName(){
        return mInputView.getInput();
    }
}
