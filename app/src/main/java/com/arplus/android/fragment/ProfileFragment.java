package com.arplus.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.arplus.android.R;
import com.arplus.android.common.Constant;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by octagon-dicky on 12/23/16.
 */

public class ProfileFragment extends BaseFragment {

    @InjectView(R.id.iv_avatar)
    ImageView ivAvatar;
    @InjectView(R.id.tv_username)
    TextView tvUsername;
    @InjectView(R.id.tv_bio)
    TextView tvBio;
    @InjectView(R.id.tv_creations)
    TextView tvCreations;
    @InjectView(R.id.tv_creations_count)
    TextView tvCreationsCount;
    @InjectView(R.id.tv_followers)
    TextView tvFollowers;
    @InjectView(R.id.tv_followers_count)
    TextView tvFollowersCount;
    @InjectView(R.id.tv_following)
    TextView tvFollowing;
    @InjectView(R.id.tv_following_count)
    TextView tvFollowingCount;
    @InjectView(R.id.gv_creations_list)
    GridView gvCreationList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_fragment_profile, container, false);
        ButterKnife.inject(this, view);


        return view;
    }

}
