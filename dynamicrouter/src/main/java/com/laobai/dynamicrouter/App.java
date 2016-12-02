package com.laobai.dynamicrouter;

import android.app.Application;
import android.content.Context;

import com.laobai.dynamicrouter.net.ServiceCreator;
import com.laobai.dynamicrouter.router.DynamicRouter;
import com.laobai.dynamicrouter.router.DynamicWebRouter;
import com.laobai.dynamicrouter.router.RouterCache;
import com.laobai.dynamicrouter.router.WebRouter;

import cn.campusapp.router.Router;
import timber.log.Timber;

/**
 * Created by kris on 16/12/1.
 */
public class App extends Application {

    private static App APPLICATION_CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();
        ServiceCreator.init();
        RouterCache.init();
        APPLICATION_CONTEXT = this;
        Timber.plant(new Timber.DebugTree());
        Router.initActivityRouter(getContext());
        Router.addRouter(new WebRouter());
        Router.addRouter(new DynamicRouter());
        Router.addRouter(new DynamicWebRouter());
    }

    public static Context getContext(){
        return APPLICATION_CONTEXT;
    }
}
