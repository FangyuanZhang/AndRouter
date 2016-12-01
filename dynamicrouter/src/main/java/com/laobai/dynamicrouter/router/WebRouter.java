package com.laobai.dynamicrouter.router;

import android.content.Context;
import android.text.TextUtils;

import com.laobai.dynamicrouter.WebViewActivity;

import cn.campusapp.router.route.IRoute;
import cn.campusapp.router.router.IRouter;
import cn.campusapp.router.utils.UrlUtils;
import timber.log.Timber;

/**
 * Created by kris on 16/9/22.
 */
public class WebRouter implements IRouter {
    static WebRouter mInstance = new WebRouter();

    public static WebRouter getInstance(){
        return mInstance;
    }

    @Override
    public boolean open(IRoute iRoute) {
        return false;
    }

    @Override
    public boolean open(String s) {
        return false;
    }

    @Override
    public boolean open(Context context, String s) {
        context.startActivity(WebViewActivity.makeIntent(s));
        return true;
    }

    @Override
    public IRoute getRoute(String s) {
        return null;
    }

    @Override
    public boolean canOpenTheRoute(IRoute iRoute) {
        return false;
    }

    @Override
    public boolean canOpenTheUrl(String s) {
        String host = "";
        try{
            host = UrlUtils.getScheme(s);
        } catch (Exception e){
            Timber.e(e, "");
        }
        return TextUtils.equals(host, "http") || TextUtils.equals(host, "https");
    }

    @Override
    public Class<? extends IRoute> getCanOpenRoute() {
        return null;
    }
}
