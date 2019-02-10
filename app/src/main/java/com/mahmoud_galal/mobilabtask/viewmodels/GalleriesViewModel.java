/*
 * Copyright (c) 2019 Mahmoud Galal.
 * Mahmoudgalal57@yahoo.com
 */

package com.mahmoud_galal.mobilabtask.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.mahmoud_galal.mobilabtask.api.NetworkingManager;
import com.mahmoud_galal.mobilabtask.model.Gallery;
import com.mahmoud_galal.mobilabtask.model.ServerResponse;

import java.util.List;

/**
 * ViewModel that manages the API fetches and data loading
 */
public class GalleriesViewModel extends ViewModel implements NetworkingManager.OnCallListener {

    private static final String TAG = GalleriesViewModel.class.getSimpleName();
    private NetworkingManager networkingManager = new NetworkingManager(this);


    public final MutableLiveData<Boolean> loading =  new MutableLiveData<>();
    public final MutableLiveData<String> errorMsg =  new MutableLiveData<>();
    public final MutableLiveData<List<Gallery>> items = new MutableLiveData<>();



    @Override
    public void onDataAvailable(Object data) {
        Log.d(TAG,"Data Downloaded successfully ");
        loading.setValue(false);
        ServerResponse response = (ServerResponse) data;
        List<Gallery> galleryItems = response.data;
        items.setValue(galleryItems);
    }

    @Override
    public void onError(String msg, Object extra) {
        Log.d(TAG,"API Error :"+msg);
        loading.setValue(false);
        errorMsg.setValue(msg);
    }

    /**
     * Fetches imgur api with the supplied query parameters
     * @param section
     * @param sortOption
     * @param windowOption
     * @param showViral
     */
    public void load(String section,String sortOption,
                     String windowOption,boolean showViral){
        if(loading.getValue() != null && loading.getValue()) {
            Log.d(TAG,"Data already loading....");
            return;
        }
        loading.setValue(true);
        networkingManager.getAllGalleriesAsync(section,sortOption,
                 windowOption, showViral);
    }
}
