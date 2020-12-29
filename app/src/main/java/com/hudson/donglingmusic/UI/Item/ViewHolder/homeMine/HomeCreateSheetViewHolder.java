package com.hudson.donglingmusic.UI.Item.ViewHolder.homeMine;

import android.view.View;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.ViewHolder.AbsViewHolder;
import com.hudson.donglingmusic.common.Utils.ToastUtils;
import com.hudson.donglingmusic.common.Utils.asyncUtils.AsyncException;
import com.hudson.donglingmusic.common.Utils.asyncUtils.AsyncTask;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoInBackground;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoOnFail;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoOnSuccess;
import com.hudson.donglingmusic.controller.ModuleManager;
import com.hudson.donglingmusic.customClass.SheetCreateDialog;
import com.hudson.donglingmusic.entity.SheetDetail;

/**
 * Created by Hudson on 2020/3/27.
 */
public class HomeCreateSheetViewHolder extends AbsViewHolder<Void> implements View.OnClickListener {
    private SheetCreateDialog mDialog;

    public HomeCreateSheetViewHolder(final View itemView) {
        super(itemView);
        itemView.findViewById(R.id.tv_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDialog == null){
                    mDialog = new SheetCreateDialog(itemView.getContext());
                    mDialog.setOnOkClickListener(HomeCreateSheetViewHolder.this);
                }
                mDialog.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        // save sheet
        mDialog.dismiss();
        new AsyncTask<Boolean>().useDbThreadPool().doInBackground(new IDoInBackground<Boolean>() {
            @Override
            public Boolean run() throws AsyncException {
                SheetDetail sheetDetail = new SheetDetail();
                sheetDetail.setTitle(mDialog.getSheetName());
                return ModuleManager.getDbModule().getUserDb().insertOrUpdate(sheetDetail);
            }
        }).doOnSuccess(new IDoOnSuccess<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                ToastUtils.showToast(R.string.common_save_success);
            }
        }).doOnFail(new IDoOnFail() {
            @Override
            public void onFail(Throwable e) {
                e.printStackTrace();
                ToastUtils.showToast(R.string.common_save_failed);
            }
        }).execute();
    }
}
