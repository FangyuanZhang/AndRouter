package com.laobai.dynamicrouter.router;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kris on 16/12/1.
 */
public class RouterCache {

    static RouterCache cache;

    Map<String, String> mMap = new HashMap<>();


    public static void init(){
        if(cache == null){
            cache = new RouterCache();
        }
    }
    private void updateRouterCache(Map<String, String> map){
        mMap = map;
    }




    private String getRouteFromCache(String key){
        return mMap.get(key);
    }


    public static void updateRouter(Map<String, String> map){
        if(map != null) {
            cache.updateRouterCache(map);
        }
    }

    public static String getRoute(String key){
        return cache.getRouteFromCache(key);
    }


}
