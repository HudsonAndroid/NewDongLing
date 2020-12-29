package com.hudson.donglingmusic.UI.Item.ViewHolder.homeMine;

import android.view.View;
import android.widget.TextView;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.ViewHolder.AbsViewHolder;
import com.hudson.donglingmusic.entity.SheetDetail;

/**
 * Created by Hudson on 2020/3/27.
 */
public class HomeSheetViewHolder extends AbsViewHolder<SheetDetail> {
    private TextView mName;

    public HomeSheetViewHolder(View itemView) {
        super(itemView);
        mName = (TextView) itemView.findViewById(R.id.tv_sheet_name);
    }

    @Override
    public void refreshView(SheetDetail data) {
        super.refreshView(data);
        mName.setText(data.getTitle());
    }
}
