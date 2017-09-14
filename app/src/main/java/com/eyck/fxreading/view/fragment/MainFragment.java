package com.eyck.fxreading.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eyck.fxreading.R;
import com.eyck.fxreading.model.entity.Item;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Eyck on 2017/9/12.
 */

public class MainFragment extends Fragment {

    @Bind(R.id.home_advertise_iv)
    ImageView homeAdvertiseIv;
    @Bind(R.id.image_iv)
    ImageView imageIv;
    @Bind(R.id.image_type)
    ImageView imageType;
    @Bind(R.id.download_start_white)
    ImageView downloadStartWhite;
    @Bind(R.id.topPanel)
    RelativeLayout topPanel;
    @Bind(R.id.type_tv)
    TextView typeTv;
    @Bind(R.id.time_tv)
    TextView timeTv;
    @Bind(R.id.typePanel)
    RelativeLayout typePanel;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.content_tv)
    TextView contentTv;
    @Bind(R.id.divider)
    View divider;
    @Bind(R.id.author_tv)
    TextView authorTv;
    @Bind(R.id.comment_tv)
    TextView commentTv;
    @Bind(R.id.like_tv)
    TextView likeTv;
    @Bind(R.id.readcount_tv)
    TextView readcountTv;
    @Bind(R.id.pager_content)
    RelativeLayout pagerContent;
    @Bind(R.id.type_container)
    LinearLayout typeContainer;

    private String title;

    public static Fragment instance(Item item){
        Fragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("item",item);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.item_main_page, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Item item = getArguments().getParcelable("item");
        int model = Integer.valueOf(item.getModel());
        if(model == 5) {
            pagerContent.setVisibility(View.GONE);
            homeAdvertiseIv.setVisibility(View.VISIBLE);
            Glide.with(this.getContext()).load(item.getThumbnail()).centerCrop().into(homeAdvertiseIv);
        }else{
            pagerContent.setVisibility(View.VISIBLE);
            homeAdvertiseIv.setVisibility(View.GONE);
            title = item.getTitle();
            Glide.with(this.getContext()).load(item.getThumbnail()).centerCrop().into(imageIv);
            commentTv.setText(item.getComment());
            likeTv.setText(item.getGood());
            readcountTv.setText(item.getView());
            titleTv.setText(item.getTitle());
            contentTv.setText(item.getExcerpt());
            authorTv.setText(item.getAuthor());
            typeTv.setText(item.getCategory());
            switch (model) {
                case 2:
                    imageType.setVisibility(View.VISIBLE);
                    downloadStartWhite.setVisibility(View.GONE);
                    imageType.setImageResource(R.drawable.library_video_play_symbol);
                    break;
                case 3:
                    imageType.setVisibility(View.VISIBLE);
                    downloadStartWhite.setVisibility(View.VISIBLE);
                    imageType.setImageResource(R.drawable.library_voice_play_symbol);
                    break;
                default:
                    downloadStartWhite.setVisibility(View.GONE);
                    imageType.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.type_container)
    public void onViewClicked() {
    }
}
