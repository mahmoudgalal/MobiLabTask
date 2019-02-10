/*
 * Copyright (c) 2019 Mahmoud Galal.
 * Mahmoudgalal57@yahoo.com
 */

package com.mahmoud_galal.mobilabtask.fragments;


import android.app.AlertDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.mahmoud_galal.mobilabtask.BuildConfig;
import com.mahmoud_galal.mobilabtask.R;
import com.mahmoud_galal.mobilabtask.Utils.FilterDialogManager;
import static com.mahmoud_galal.mobilabtask.Utils.FilterDialogManager.*;

/**
 * A Base class Fragment for the Galleries and Gallery images Fragment  {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment implements FilterDialogManager.OnFilterConfirmed {

    protected FilterDialogManager filterDialogManager ;
    protected final int SHOW_AS_GRID = 0;
    protected final int SHOW_AS_STAGGERED_GRID = 1;
    protected final int SHOW_AS_LIST = 2;
//List view as mode
    protected int listShowMode = SHOW_AS_LIST;

    protected final int GRID_SPAN_PORT = 2;
    protected final int GRID_SPAN_LAND = 3;
    protected int currentGridSpan = GRID_SPAN_PORT;
//Device orientation
    protected int currentOrientation = Configuration.ORIENTATION_PORTRAIT;
    private boolean firstTime = false;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        if(!firstTime) {
            int orientation = getActivity().getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                currentOrientation = Configuration.ORIENTATION_PORTRAIT;
                currentGridSpan = GRID_SPAN_PORT;
                listShowMode = SHOW_AS_LIST;
            } else {
                currentOrientation = Configuration.ORIENTATION_LANDSCAPE;
                currentGridSpan = GRID_SPAN_LAND;
                listShowMode = SHOW_AS_STAGGERED_GRID;
            }
            changeListView(listShowMode);
            firstTime = true ;
        }

    }

    /**
     * Invoked when  a List View Mode change is required.
     * @param viewType
     */
    protected void changeListView(int viewType){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return null;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        filterDialogManager = new FilterDialogManager(getContext());
        filterDialogManager.setOnFilterConfirmedListener(this);
    }

    /**
     * About box invoked
     */
    protected void onAboutDialogRequested(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("About..")
                .setMessage("This is a tech task designed for MobiLab Solutions\n" +
                        "Copyright (c) 2019 Mahmoud Galal\n"+
                "Support: MahmoudGalal57@yahoo.com\n"+
                "Version:"+BuildConfig.VERSION_CODE
                ).setPositiveButton("Ok",null)
                .show();
    }
    /**
     * Filter dialog invoked
     */
    protected void onFilterDialogRequested(){
        filterDialogManager.showDialog();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu,menu);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT ){
            currentOrientation = Configuration.ORIENTATION_PORTRAIT;
            currentGridSpan = GRID_SPAN_PORT;
            listShowMode = SHOW_AS_LIST ;
        }else{
            currentOrientation = Configuration.ORIENTATION_LANDSCAPE ;
            currentGridSpan = GRID_SPAN_LAND;
            listShowMode = SHOW_AS_STAGGERED_GRID ;
        }
        changeListView(listShowMode);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about_menu_item:
                onAboutDialogRequested();
                break;
            case R.id.filter_menu_item:
                onFilterDialogRequested();
                break;
            case R.id.as_list:
                listShowMode = SHOW_AS_LIST;
                changeListView(SHOW_AS_LIST);
                break;
            case R.id.as_grid:
                listShowMode = SHOW_AS_GRID;
                changeListView(SHOW_AS_GRID);
                break;
            case R.id.as_stag_grid:
                listShowMode = SHOW_AS_STAGGERED_GRID;
                changeListView(SHOW_AS_STAGGERED_GRID);
                break;
        }
        return true;
    }

    /**
     * Called when a filter is applied
     * @param filterObject filter data
     */
    @Override
    public void onFilterConfirmed(FilterObject filterObject) {
    }
}
