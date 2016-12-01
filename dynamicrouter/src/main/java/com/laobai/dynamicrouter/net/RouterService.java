package com.laobai.dynamicrouter.net;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by kris on 16/12/1.
 */
public interface RouterService {

    @GET(UrlConfig.GET_ROUTER)
    Call<Map<String, String>> getRouters();

}
