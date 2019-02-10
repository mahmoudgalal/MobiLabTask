/*
 * Copyright (c) 2019 Mahmoud Galal.
 * Mahmoudgalal57@yahoo.com
 */

package com.mahmoud_galal.mobilabtask.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mahmoud_galal.mobilabtask.R;
import com.mahmoud_galal.mobilabtask.Utils.Constants;
import com.mahmoud_galal.mobilabtask.Utils.FilterDialogManager;
import com.mahmoud_galal.mobilabtask.Utils.Utils;
import com.mahmoud_galal.mobilabtask.adapters.GalleriesAdapter;
import com.mahmoud_galal.mobilabtask.model.Gallery;
import com.mahmoud_galal.mobilabtask.viewmodels.GalleriesViewModel;

import java.util.List;

/**
 * A List of All Galleries {@link Fragment} .
 */
public class AllGalleriesFragment extends BaseFragment implements GalleriesAdapter.OnItemClickedListener {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private RecyclerView.LayoutManager currentLayoutManager;
    private GalleriesAdapter adapter;
    private GalleriesViewModel viewModel;
    private boolean isLoading = false;

    public AllGalleriesFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_galleries, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.galleries_recycler_view);
        progressBar = view.findViewById(R.id.progressBar);
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView.setLayoutManager(null);
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

        recyclerView.setLayoutManager(currentLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void init(){

        if(currentLayoutManager == null)
        currentLayoutManager = new GridLayoutManager(getContext(),currentGridSpan,
                LinearLayoutManager.VERTICAL,false);
        DividerItemDecoration dividerItemDecoration =  new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(currentLayoutManager);

        viewModel = ViewModelProviders.of(this).get(GalleriesViewModel.class);
        viewModel.items.observe(this, new Observer<List<Gallery>>() {
            @Override
            public void onChanged(@Nullable List<Gallery> newsItems) {
                adapter.setItems(newsItems);
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"Data loaded ",
                        Toast.LENGTH_LONG ).show();
            }
        });
        viewModel.errorMsg.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(getContext(),"Error:"+s,Toast.LENGTH_LONG).show();
            }
        });
        viewModel.loading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean loading) {
                isLoading = loading;
                progressBar.setVisibility(loading?View.VISIBLE:View.GONE);

            }
        });
            adapter = new GalleriesAdapter(viewModel.items.getValue());
            adapter.setOnItemClickedListener(this);
            recyclerView.setAdapter(adapter);

            if (!Utils.isInternetConnectionExist(getContext())) {
                Toast.makeText(getContext(), "No internet connection,try later .. ",
                        Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                return;
            }
            //We might already have data ,so ignore recreation and use the existing instance
            if(adapter.getItemCount()<=0) {
                FilterDialogManager.FilterObject filterObject = filterDialogManager.getCurrentFilterObject();
                viewModel.load(filterObject.selectedSection, filterObject.selectedSortOption,
                        filterObject.selectedWindowOption, filterObject.showViral);
            }
    }

    @Override
    public void onFilterConfirmed(FilterDialogManager.FilterObject filterObject) {
        super.onFilterConfirmed(filterObject);
        //Filtering requires internet connetion
        if (!Utils.isInternetConnectionExist(getContext())) {
            Toast.makeText(getContext(), "No internet connection,try later .. ",
                    Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        isLoading = true ;
        progressBar.setVisibility(View.VISIBLE);
        viewModel.load(filterObject.selectedSection, filterObject.selectedSortOption,
                filterObject.selectedWindowOption, filterObject.showViral);
    }

    @Override
    public void onItemClicked(View clickedView, Gallery item, int position) {
        if(item.images != null && item.images.size()>0){
            //show gallery contents....
            GalleryFragment fragment =  new GalleryFragment();
            Bundle args = new Bundle();
            args.putParcelable(Constants.GALLERY_ITEM_KEY,item);
            fragment.setArguments(args);

            getFragmentManager().beginTransaction().
                    replace(R.id.main_container,
                            fragment,GalleryFragment.class.getSimpleName())
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    }
}
