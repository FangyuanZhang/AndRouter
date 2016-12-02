package com.laobai.dynamicrouter.router;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.laobai.dynamicrouter.net.UrlConfig;

import cn.campusapp.router.Router;
import cn.campusapp.router.route.IRoute;
import cn.campusapp.router.router.IRouter;
import cn.campusapp.router.utils.UrlUtils;
import timber.log.Timber;

/**
 * Created by kris on 16/12/2.
 * 自定义Router 为了打开 Schema为 "dynamic://" 的url
 */
public class DynamicRouter implements IRouter {
    private static final String DEFAULT_SCHEME = "dynamic";


    public DynamicRouter(){

    }

    @Override
    public boolean open(IRoute route) {
        return open(null, route);
    }


    protected boolean open(Context context, IRoute route){

        String routeUrl = route.getUrl();
        boolean isSuccess = false;
        if(canOpenTheRoute(route)) {
            //首先尝试用原生的Activity打开，如果无法打开，则使用WebView打开
            try {
                Uri uri = Uri.parse(routeUrl)
                        .buildUpon()
                        .scheme("activity")
                        .build();
                if (!Router.open(context, uri.toString())) {
                    String path = UrlConfig.BASE_URL + UrlUtils.getHost(routeUrl);
                    Timber.i("path : %s", path);
                    String url = UrlUtils.addQueryParameters(path, UrlUtils.getParameters(routeUrl));
                    isSuccess = Router.open(context, url);
                } else {
                    isSuccess = true;
                }
            } catch (Exception e) {
                Timber.e(e, "");
            }
        }
        return isSuccess;
    }

    @Override
    public boolean open(String url) {
        return open(null, url);
    }

    @Override
    public boolean open(Context context, String url) {
        return open(context, getRoute(url));
    }

    @Override
    public IRoute getRoute(String url) {
        return new DynamicRoute.Builder(this)
                .setUrl(url)
                .build();
    }

    @Override
    public boolean canOpenTheRoute(IRoute route) {
        return canOpenTheUrl(route.getUrl());
    }

    @Override
    public boolean canOpenTheUrl(String url) {
        return TextUtils.equals(UrlUtils.getScheme(url), DEFAULT_SCHEME);
    }

    @Override
    public Class<? extends IRoute> getCanOpenRoute() {
        return DynamicRoute.class;
    }
}
