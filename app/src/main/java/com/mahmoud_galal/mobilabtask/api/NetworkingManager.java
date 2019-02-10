/*
 * Copyright (c) 2019 Mahmoud Galal.
 * Mahmoudgalal57@yahoo.com
 */

package com.mahmoud_galal.mobilabtask.api;

import com.mahmoud_galal.mobilabtask.Utils.Constants;

import com.mahmoud_galal.mobilabtask.model.ServerResponse;

import java.io.IOException;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkingManager {


    public interface OnCallListener{

        void onDataAvailable(Object data);
        void onError(String msg,Object extra);
    }
    private OnCallListener listener;
    private Retrofit retrofit ;

    public NetworkingManager(OnCallListener listener){
        this.listener = listener ;
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void getAllGalleriesAsync(String section,String sortOption,
                                     String windowOption,boolean showViral){

        AllMainGalleries allMainGalleries = retrofit.create(AllMainGalleries.class);
        Call<ServerResponse> galleriesCall = allMainGalleries.getAllMainGalleries(section,
                sortOption,windowOption,showViral);

        galleriesCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(listener != null){
                    if(response.isSuccessful())
                        listener.onDataAvailable(response.body());
                    else
                        listener.onError(response.message(),response.code());
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                if(listener != null)
                    listener.onError( t.getMessage(),t);
            }
        });

    }

    public ServerResponse getAllGalleries(String section,String sortOption,
                                     String windowOption,boolean showViral){

        AllMainGalleries allMainGalleries = retrofit.create(AllMainGalleries.class);
        Call<ServerResponse> galleriesCall = allMainGalleries.getAllMainGalleries(section,
                sortOption,windowOption,showViral);
        try {
            Response<ServerResponse> response = galleriesCall.execute();
            return  response.body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null ;

    }


}
