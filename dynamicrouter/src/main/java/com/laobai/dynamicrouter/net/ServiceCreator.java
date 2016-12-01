package com.laobai.dynamicrouter.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kris on 16/12/1.
 */
public class ServiceCreator {

    private static Retrofit retrofit;

    public static void init(){
        if(retrofit == null){
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(UrlConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public static RouterService createRouterService(){
        return retrofit.create(RouterService.class);
    }

}
