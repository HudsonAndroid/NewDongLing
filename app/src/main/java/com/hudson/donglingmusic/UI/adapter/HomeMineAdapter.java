package com.hudson.donglingmusic.UI.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.ViewHolder.homeMine.ContentTitleViewHolder;
import com.hudson.donglingmusic.UI.Item.ViewHolder.homeMine.HeaderViewHolder;
import com.hudson.donglingmusic.UI.Item.ViewHolder.homeMine.HomeCreateSheetViewHolder;
import com.hudson.donglingmusic.UI.Item.ViewHolder.homeMine.HomeSheetViewHolder;
import com.hudson.donglingmusic.entity.SheetDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的 tab页面的adapter
 * Created by Hudson on 2020/3/21.
 */
public class HomeMineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CONTENT_TITLE = 1;
    private static final int TYPE_SHEET = 2;
    private static final int TYPE_CREATE = 3;
    private final List<SheetDetail> mCreateSheets = new ArrayList<>();

    public void setSheets(List<SheetDetail> sheets){
        if(sheets != null && sheets.size()>0){
            mCreateSheets.clear();
            mCreateSheets.addAll(sheets);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        if(type == TYPE_HEADER){
            return new HeaderViewHolder(inflate.inflate(R.layout.item_view_holder_home_mine_header,parent,false));
        }else if(type == TYPE_CONTENT_TITLE){
            return new ContentTitleViewHolder(inflate.inflate(R.layout.item_viewholder_content_title,parent,false));
        }else if(type == TYPE_SHEET){
            return new HomeSheetViewHolder(inflate.inflate(R.layout.item_viewholder_home_sheet,parent,false));
        }else{
            return new HomeCreateSheetViewHolder(inflate.inflate(R.layout.item_viewholder_home_create_sheet,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_SHEET){
            ((HomeSheetViewHolder)viewHolder).refreshView(mCreateSheets.get(position - 2));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_HEADER;
        }else if(position == 1){
            return TYPE_CONTENT_TITLE;
        }else if(position == getItemCount() - 1){
            return TYPE_CREATE;
        }else{
            return TYPE_SHEET;
        }
    }

    @Override
    public int getItemCount() {
        return 3  + mCreateSheets.size();
    }
}
