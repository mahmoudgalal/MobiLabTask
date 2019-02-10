/*
 * Copyright (c) 2019 Mahmoud Galal.
 * Mahmoudgalal57@yahoo.com
 */

package com.mahmoud_galal.mobilabtask;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.fresco.FrescoImageLoader;
import com.mahmoud_galal.mobilabtask.fragments.AllGalleriesFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar myActionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///Initializing imageviewer Library
        BigImageViewer.initialize(FrescoImageLoader.with(getApplicationContext()));
        ///////////////////////////////////////////////////////////////
        setContentView(R.layout.activity_main);
        myActionBar = findViewById(R.id.my_tool_bar);
        setSupportActionBar(myActionBar);

        Fragment fragment =  new AllGalleriesFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container,fragment,
                        AllGalleriesFragment.class.getSimpleName()).
                commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
