package com.sample.simpsonviewer.data.remote;

import com.sample.simpsonviewer.model.BaseModel;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("?format=json")
    Single<BaseModel> getCharacterViewer(@Query("q") String query);

}
