package com.eyck.fxreading.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eyck.fxreading.R;
import com.eyck.fxreading.model.entity.Constant;
import com.eyck.fxreading.model.entity.Item;
import com.eyck.fxreading.view.activity.DetailActivity;
import com.eyck.fxreading.view.activity.VideoDetailActivity;
import com.eyck.fxreading.view.activity.VoiceDetailActivity;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eyck on 2017/9/12.
 */

public class ArtRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int FOOTER_TYPE = 1001;
    private static final int CONTENT_TYPE = 1002;
    private List<Item> artList = new ArrayList<>();
    private Context context;
    private boolean hasMore=true;
    private boolean error=false;

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ArtRecycleViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_footer, parent, false);
            return new FooterViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_art, parent, false);
            return new ArtHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return FOOTER_TYPE;
        }
        return CONTENT_TYPE;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(position + 1 == getItemCount()) {
            if(artList.size() == 0) {
                return;
            }
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            if(error) {
                error = false;
                footerHolder.avi.setVisibility(View.GONE);
                footerHolder.noMoreTx.setVisibility(View.GONE);
                footerHolder.errorTx.setVisibility(View.VISIBLE);
                footerHolder.errorTx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            }
            if (hasMore){
                footerHolder.avi.setVisibility(View.VISIBLE);
                footerHolder.noMoreTx.setVisibility(View.GONE);
                footerHolder.errorTx.setVisibility(View.GONE);
            }else {
                footerHolder.avi.setVisibility(View.GONE);
                footerHolder.noMoreTx.setVisibility(View.VISIBLE);
                footerHolder.errorTx.setVisibility(View.GONE);
            }
        }else{
            ArtHolder artHolder = (ArtHolder) holder;
            final Item item = artList.get(position);
            artHolder.authorTv.setText(item.getAuthor());
            artHolder.titleTv.setText(item.getTitle());
            Glide.with(context).load(item.getThumbnail()).centerCrop().into(artHolder.imageIv);
            artHolder.typeContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int model = Integer.valueOf(item.getModel());
                    Intent intent=null;
                    switch (model){
                        case 1://
                            intent= new Intent(context, DetailActivity.class);
                            break;
                        case 3://
                            intent= new Intent(context, VoiceDetailActivity.class);
                            break;
                        case 2://
                            intent= new Intent(context, VideoDetailActivity.class);
                            break;
                    }
                    if (intent != null){
                        intent.putExtra(Constant.ITEM,item);
                        Logger.i("position==",position);
                        Log.i("TAG", "position=="+position);
                        context.startActivity(intent);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return artList.size() + 1;
    }

    public void setArtList(List<Item> artList) {
        int position = artList.size() - 1;
        this.artList.addAll(artList);
        notifyItemChanged(position);
    }

    public void replaceAllData(List<Item> artList) {
        this.artList.clear();
        this.artList.addAll(artList);
        notifyDataSetChanged();
    }

    public String getLastItemId() {
        if (artList.size() == 0) {
            return "0";
        }
        Item item = artList.get(artList.size() - 1);
        return item.getId();
    }

    public String getLastItemCreateTime() {
        if (artList.size() == 0) {
            return "0";
        }
        Item item = artList.get(artList.size() - 1);
        return item.getCreate_time();
    }

    static class ArtHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image_iv)
        ImageView imageIv;
        @Bind(R.id.arrow_iv)
        ImageView arrowIv;
        @Bind(R.id.title_tv)
        TextView titleTv;
        @Bind(R.id.author_tv)
        TextView authorTv;
        @Bind(R.id.type_container)
        RelativeLayout typeContainer;

        public ArtHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.avi)
        AVLoadingIndicatorView avi;
        @Bind(R.id.nomore_tx)
        TextView noMoreTx;
        @Bind(R.id.error_tx)
        TextView errorTx;
        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
