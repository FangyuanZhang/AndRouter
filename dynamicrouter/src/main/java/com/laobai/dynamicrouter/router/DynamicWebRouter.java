package com.laobai.dynamicrouter.router;

import android.content.Context;
import android.text.TextUtils;

import com.laobai.dynamicrouter.net.UrlConfig;

import cn.campusapp.router.Router;
import cn.campusapp.router.route.IRoute;
import cn.campusapp.router.router.BaseRouter;
import cn.campusapp.router.utils.UrlUtils;
import timber.log.Timber;

/**
 * Created by kris on 16/12/2.
 */
public class DynamicWebRouter extends BaseRouter {
    private static final String DEFAULT_SCHEMA = "dynamicWeb";

    public DynamicWebRouter(){

    }

    @Override
    public boolean open(IRoute route) {
        return open(null, route);
    }

    protected boolean open(Context context, IRoute route){
        boolean isSuccess = false;
        if(canOpenTheRoute(route)){
            //强制使用WebViewActivity打开
            String routeUrl = route.getUrl();
            try {
                String host = UrlUtils.getHost(routeUrl);
                String path = UrlConfig.BASE_URL + host;
                Timber.i("path %s", path);
                String url = UrlUtils.addQueryParameters(path, UrlUtils.getParameters(routeUrl));
                isSuccess = Router.open(context, url);
            } catch (Exception e){
                Timber.e(e, "");
            }
        }
        return isSuccess;
    }

    @Override
    public boolean open(String url) {
        return open(getRoute(url));
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
        return TextUtils.equals(UrlUtils.getScheme(url), DEFAULT_SCHEMA);
    }

    @Override
    public Class<? extends IRoute> getCanOpenRoute() {
        return DynamicRoute.class;
    }
}
