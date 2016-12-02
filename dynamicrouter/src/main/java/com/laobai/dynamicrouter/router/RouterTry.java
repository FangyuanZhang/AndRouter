package com.laobai.dynamicrouter.router;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Map;

import cn.campusapp.router.Router;
import timber.log.Timber;

/**
 * Created by kris on 16/12/1.
 */
public class RouterTry {

    public static void tryOpenOr(Context context, String routeUrl, String defaultUrl){
        if(!TextUtils.isEmpty(routeUrl)){
            if(!Router.open(context, routeUrl)){
                Router.open(context, defaultUrl);
            }
        } else {
            Router.open(context, defaultUrl);
        }

    }

    public static void tryOpenOr(Context context, String routeUrl, String defaultUrl, Map<String, String> queryParam){
        if(TextUtils.isEmpty(routeUrl)){
            Router.open(context, defaultUrl);
        } else {
            String routeUrlWithQuery = appendQueryParam(routeUrl, queryParam);
            String defaultUrlWithQuery = appendQueryParam(defaultUrl, queryParam);
            tryOpenOr(context, routeUrlWithQuery, defaultUrlWithQuery);
        }
    }


    protected static String appendQueryParam(String url, Map<String, String> queryPrams){
        String urlWithQuery = "";
        try {
            Uri.Builder builder = Uri.parse(url)
                    .buildUpon();

            for (String key : queryPrams.keySet()) {
                builder.appendQueryParameter(key, queryPrams.get(key));
            }
            urlWithQuery = builder.build().toString();
        } catch (Exception e){
            Timber.e(e, "");
            urlWithQuery = url;
        }
        return urlWithQuery;

    }


}
