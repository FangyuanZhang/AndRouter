package com.laobai.dynamicrouter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import cn.campusapp.router.Router;

/**
 * Created by kris on 16/12/2.
 * Router 网关，网页和原生页面跳转都需要经过该网管，负责url过滤，安全处理，以及url分发,所以它不需要 ui
 */
public class RouterDispatchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent  = getIntent();
        Uri uri = intent.getData();
        Router.open(RouterDispatchActivity.this, uri.toString());
        finish();
    }
}
