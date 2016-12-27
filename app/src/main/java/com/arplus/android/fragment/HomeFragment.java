package com.arplus.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arplus.android.R;

import butterknife.ButterKnife;

/**
 * Created by octagon-dicky on 12/27/16.
 */

public class HomeFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_fragment_home, container, false);
        ButterKnife.inject(this, view);


        return view;
    }
}
