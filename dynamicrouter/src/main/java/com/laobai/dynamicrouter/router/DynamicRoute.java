package com.laobai.dynamicrouter.router;

import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

import cn.campusapp.router.route.BaseRoute;
import cn.campusapp.router.router.IRouter;
import timber.log.Timber;

/**
 * Created by kris on 16/12/2.
 */
public class DynamicRoute extends BaseRoute {

    public DynamicRoute(IRouter router, String url) {
        super(router, url);
    }


    public static class Builder {
        Map<String, String> parameters = new HashMap<>();

        IRouter router;
        String url;

        public Builder(IRouter router){
            this.router = router;
        }

        public Builder setUrl(String url){
            this.url = url;
            return this;
        }

        public Builder appendQueryParameter(String key, String value){
            parameters.put(key, value);
            return this;
        }

        public Builder appendQueryParameter(Map<String, String> map){
            parameters.putAll(map);
            return this;
        }

        public DynamicRoute build(){
            String urlAppendQuery = "";
            try{
                Uri.Builder builder = Uri.parse(url)
                        .buildUpon();
                for(String key : parameters.keySet()){
                    builder.appendQueryParameter(key, parameters.get(key));
                }

                urlAppendQuery = builder.build().toString();
            } catch (Exception e){
                Timber.e(e, "");
                urlAppendQuery = url;
            }
            return new DynamicRoute(router, urlAppendQuery);
        }



    }
}
