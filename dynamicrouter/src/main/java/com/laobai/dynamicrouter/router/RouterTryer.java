package com.laobai.dynamicrouter.router;

import android.content.Context;
import android.net.Uri;

import java.util.Map;

import cn.campusapp.router.Router;
import timber.log.Timber;

/**
 * Created by kris on 16/12/1.
 */
public class RouterTryer {

    public static void tryOpenOr(Context context, String routeUrl, String defaultUrl){

        boolean isSuccess = false;
        //首先尝试用原生的Activity打开，如果无法打开，则使用WebView打开
        try {
            Uri uri = Uri.parse(routeUrl)
                    .buildUpon()
                    .scheme("activity")
                    .build();
            if (!Router.open(context, uri.toString())) {
                Uri httpUri = Uri.parse(routeUrl)
                        .buildUpon()
                        .scheme("http")
                        .build();
                isSuccess = Router.open(context, httpUri.toString());
            } else {
                isSuccess = true;
            }
        } catch (Exception e){
            Timber.e(e, "");
        }
        if(!isSuccess){
            Router.open(context, defaultUrl);
        }

    }

    public static void tryOpenOr(Context context, String routeUrl, String defaultUrl, Map<String, String> queryParam){
        String routeUrlWithQuery = appendQueryParam(routeUrl, queryParam);
        String defaultUrlWithQuery = appendQueryParam(defaultUrl, queryParam);
        tryOpenOr(context, routeUrlWithQuery, defaultUrlWithQuery);
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
