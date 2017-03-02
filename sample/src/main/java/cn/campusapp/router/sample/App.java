package cn.campusapp.router.sample;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.campusapp.router.Router;
import cn.campusapp.router.interceptor.Interceptor;
import cn.campusapp.router.router.IActivityRouteTableInitializer;

/**
 * Created by kris on 16/3/11.
 */
public class App extends Application {

    private static final HashMap<String, String> INTERCEPTOR_BLACK_LIST_SET = new LinkedHashMap<>();

    static {
        INTERCEPTOR_BLACK_LIST_SET.put("http://www.souhu.com", "activity://error");
        INTERCEPTOR_BLACK_LIST_SET.put("activity://intercepted", "activity://error");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Router.initActivityRouter(getApplicationContext(), new IActivityRouteTableInitializer() {
            @Override
            public void initRouterTable(Map<String, Class<? extends Activity>> router) {
                router.put("activity://second/:{name}", SecondActivity.class);
            }
        }, "activity", "activity2");
        // Router.initActivityRouter(this);
        Router.initBrowserRouter(getApplicationContext());
        // to output logs of AndRouter
        Router.setDebugMode(true);
        Router.setInterceptor(new Interceptor() {
            @Override
            public boolean intercept(Context context, String url) {
                if(INTERCEPTOR_BLACK_LIST_SET.keySet().contains(url)){
                    Router.open(context, INTERCEPTOR_BLACK_LIST_SET.get(url));
                    return true;
                }
                return false;
            }
        });
    }
}
