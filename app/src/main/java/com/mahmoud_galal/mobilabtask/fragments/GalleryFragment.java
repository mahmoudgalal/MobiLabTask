/*
 * Copyright (c) 2019 Mahmoud Galal.
 * Mahmoudgalal57@yahoo.com
 */

package com.mahmoud_galal.mobilabtask.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mahmoud_galal.mobilabtask.R;
import com.mahmoud_galal.mobilabtask.Utils.Constants;
import com.mahmoud_galal.mobilabtask.Utils.Utils;
import com.mahmoud_galal.mobilabtask.adapters.GalleryImagesAdapter;
import com.mahmoud_galal.mobilabtask.model.Gallery;
import com.mahmoud_galal.mobilabtask.model.Image;

/**
 * ALL images in the selected Gallery {@link Fragment} .
 */
public class GalleryFragment extends BaseFragment implements GalleryImagesAdapter.OnItemClickedListener {

    private RecyclerView imagesRecyclerView;
    private ProgressBar progressBar;
    private RecyclerView.LayoutManager currentLayoutManager;
    private GalleryImagesAdapter adapter;
    private Gallery galleryItem ;


    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imagesRecyclerView = view.findViewById(R.id.gallery_images_recycler_view);
        progressBar = view.findViewById(R.id.progressBar);
        init();
    }

    private void init(){
        galleryItem = getArguments().getParcelable(Constants.GALLERY_ITEM_KEY);

        currentLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false);
        DividerItemDecoration dividerItemDecoration =  new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL);
        imagesRecyclerView.addItemDecoration(dividerItemDecoration);
        imagesRecyclerView.setHasFixedSize(false);
        imagesRecyclerView.setLayoutManager(currentLayoutManager);

        adapter = new GalleryImagesAdapter(galleryItem.images);
        adapter.setOnItemClickedListener(this);
        imagesRecyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //we don't have filter here
        menu.removeItem(R.id.filter_menu_item);
    }

    @Override
    protected void changeListView(int viewType) {
        switch (viewType){
            case SHOW_AS_LIST:
                currentLayoutManager = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL,false);
                break;
            case SHOW_AS_GRID:
                currentLayoutManager = new GridLayoutManager(getContext(),currentGridSpan,
                        LinearLayoutManager.VERTICAL,false);
                break;
            case SHOW_AS_STAGGERED_GRID:
                currentLayoutManager = new StaggeredGridLayoutManager(currentGridSpan,
                        LinearLayoutManager.VERTICAL);
                break;
        }

        imagesRecyclerView.setLayoutManager(currentLayoutManager);
        imagesRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(View clickedView, Image item, int position) {
        // Show selected image
        ImageViewerFragment fragment = new ImageViewerFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.IMAGE_ITEM_KEY,item);
        fragment.setArguments(args);

        getFragmentManager().beginTransaction().
                replace(R.id.main_container,
                        fragment,ImageViewerFragment.class.getSimpleName())
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}
