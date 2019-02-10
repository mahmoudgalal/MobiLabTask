/*
 * Copyright (c) 2019 Mahmoud Galal.
 * Mahmoudgalal57@yahoo.com
 */

package com.mahmoud_galal.mobilabtask.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator;
import com.github.piasy.biv.view.BigImageView;
import com.mahmoud_galal.mobilabtask.R;
import com.mahmoud_galal.mobilabtask.Utils.Constants;
import com.mahmoud_galal.mobilabtask.model.Image;

/**
 * A simple Image Viewer{@link Fragment} fragment.
 */
public class ImageViewerFragment extends Fragment {

    private BigImageView bigImageView ;
    private Image currentImg ;


    public ImageViewerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentImg = getArguments().getParcelable(Constants.IMAGE_ITEM_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_viewer,
                container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //No need for an actionbar here
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bigImageView = view.findViewById(R.id.mBigImage);
        init();
    }

    private void init(){
        bigImageView.setTapToRetry(true);
        bigImageView.setProgressIndicator(new ProgressPieIndicator());
        bigImageView.showImage(Uri.parse(currentImg.link));
    }
}
