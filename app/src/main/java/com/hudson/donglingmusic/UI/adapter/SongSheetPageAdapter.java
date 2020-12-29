package com.hudson.donglingmusic.UI.adapter;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.ViewHolder.LoadMoreViewHolder;
import com.hudson.donglingmusic.UI.Item.ViewHolder.SongSheetItemViewHolder;
import com.hudson.donglingmusic.entity.SongSheet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hudson on 2020/3/2.
 */
public class SongSheetPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_NORMAL = 1;//normal
    private static final int TYPE_BOTTOM = 2;//bottom
    private final List<SongSheet.SheetItem> mSongSheets = new ArrayList<>();
    private int mLoadMoreStatus = LoadMoreViewHolder.STATUS_HAS_MORE;

    public SongSheetPageAdapter() {
        setHasStableIds(true);
    }

    public void notifyLoadStatus(@IntRange(from = 0, to = 3)int loadMoreStatus){
        if(mLoadMoreStatus != loadMoreStatus){
            mLoadMoreStatus = loadMoreStatus;
            notifyDataSetChanged();
        }
    }

    public boolean canLoadMore(){
        return mLoadMoreStatus == LoadMoreViewHolder.STATUS_HAS_MORE
                || mLoadMoreStatus == LoadMoreViewHolder.STATUS_LOAD_FAILED;
    }

    public void updateSheet(@NonNull List<SongSheet.SheetItem> songSheets, boolean hasMore){
        if(songSheets.size() > 0){
            mSongSheets.addAll(songSheets);
            if(hasMore){
                mLoadMoreStatus = LoadMoreViewHolder.STATUS_HAS_MORE;
            }else{
                mLoadMoreStatus = LoadMoreViewHolder.STATUS_NO_MORE;
            }
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        if(type == TYPE_BOTTOM){
            return new LoadMoreViewHolder(inflater.inflate(R.layout.item_viewholder_load_more,
                    viewGroup,false));
        }else{
            return new SongSheetItemViewHolder(inflater.inflate(R.layout.item_viewholder_songsheet_normal,
                    viewGroup,false),mOnItemClickListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int itemViewType = getItemViewType(position);
        if(itemViewType == TYPE_NORMAL){
            ((SongSheetItemViewHolder)viewHolder).refreshView(mSongSheets.get(position));
        }else{
            ((LoadMoreViewHolder)viewHolder).refreshView(mLoadMoreStatus);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == mSongSheets.size()){
            return TYPE_BOTTOM;
        }else{
            return TYPE_NORMAL;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mSongSheets.size() + 1;
    }

    //每一项点击事件
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(View v, String listId, String imgUrl);
    }
}
