/*
 * Copyright (c) 2019 Mahmoud Galal.
 * Mahmoudgalal57@yahoo.com
 */

package com.mahmoud_galal.mobilabtask.api;

import com.mahmoud_galal.mobilabtask.Utils.Constants;

import com.mahmoud_galal.mobilabtask.model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AllMainGalleries {
    @Headers("Authorization:Client-ID "+ Constants.CLIENT_ID)
    @GET("gallery/{section}/{sort}/{window}/0.json")
    Call<ServerResponse> getAllMainGalleries(@Path("section") String section,
                                             @Path("sort") String sortOption,
                                             @Path("window") String windowOption,
                                             @Query("showViral") boolean showViral);
}
