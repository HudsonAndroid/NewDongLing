package com.hudson.donglingmusic.UI.Item.TabContentView.home;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.Item.OnItemClickListener;
import com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner.tabContent.BaseAsyncTabContent;
import com.hudson.donglingmusic.UI.View.ViewPagerWithIndicator.ViewPager.AutoSwitchViewPager;
import com.hudson.donglingmusic.UI.View.ViewPagerWithIndicator.indicator.PathPointIndicator;
import com.hudson.donglingmusic.UI.activity.BillboardDetailActivity;
import com.hudson.donglingmusic.UI.adapter.HeaderPicAdapter;
import com.hudson.donglingmusic.UI.adapter.HotBillboardAdapter;
import com.hudson.donglingmusic.entity.Billboard;
import com.hudson.donglingmusic.entity.HomePic;
import com.hudson.donglingmusic.entity.HotPageResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hudson on 2020/2/29.
 */
public class HomeHotPage extends BaseAsyncTabContent<HotPageResult> {
    private static final int HEADER_PIC_SIZE = 8;
    private static final int BILLBOARD_SIZE = 6;
    private AutoSwitchViewPager mViewPager;
    private PathPointIndicator mIndicator;
    private HotBillboardAdapter mAdapter;

    public HomeHotPage(Context context) {
        super(context);
    }

    @Override
    protected void initContent(ConstraintLayout parent) {
        final Context context = parent.getContext();
        View root = LayoutInflater.from(context).inflate(R.layout.item_page_home_first, parent);
        mViewPager = (AutoSwitchViewPager) root.findViewById(R.id.vp_banner);
        mIndicator = (PathPointIndicator) root.findViewById(R.id.pp_indicator);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.rv_content);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        mAdapter = new HotBillboardAdapter(new OnItemClickListener<Billboard>() {
            @Override
            public void onItemClick(Billboard item, int position) {
                Billboard.BillboardInfo billboardInfo = item.getBillboardInfo();
                if(billboardInfo != null){
                    BillboardDetailActivity.start(context, billboardInfo.getBillboardType());
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected HotPageResult getDataBackground() {
        return HotPageResult.fetch(HEADER_PIC_SIZE,BILLBOARD_SIZE,mForceServer);
    }

    @Override
    protected void doOnSuccess(HotPageResult data) {
        attachHeaderPic(data.getHomePic());
        attachBillboard(data.getBillboards());
    }

    private void attachBillboard(List<Billboard> billboards) {
        mAdapter.refreshList(billboards);
    }

    private void attachHeaderPic(HomePic homePic){
        if(homePic != null){
            List<HomePic.PicItem> pics = homePic.getPics();
            if(pics != null && pics.size() != 0){
                int size = pics.size();
                List<View> list = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    ImageView imageView = new ImageView(getContext());
                    Glide.with(getContext()).load(pics.get(i).getUrl()).into(imageView);
                    final int index = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(),"你点击了"+index,Toast.LENGTH_LONG).show();
                        }
                    });
                    list.add(imageView);
                }
                mViewPager.setAdapter(new HeaderPicAdapter(list));
                mIndicator.setCount(size);
                mViewPager.attachIndicator(mIndicator);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewPager.destroy();
    }
}
