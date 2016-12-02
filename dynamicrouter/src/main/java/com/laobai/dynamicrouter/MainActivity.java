package com.laobai.dynamicrouter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.laobai.dynamicrouter.net.RouterService;
import com.laobai.dynamicrouter.net.ServiceCreator;
import com.laobai.dynamicrouter.router.RouterCache;
import com.laobai.dynamicrouter.router.RouterTry;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.campusapp.router.annotation.RouterMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * 一个动态路由的demo工程 Application界面上所有的跳转都支持由后台下发路由更改动作
 * TODO 在有参数的情况下要给后台返回的url拼上参数
 */
@RouterMap("activity://main")
public class MainActivity extends AppCompatActivity {


    private static final String KEY_ACTION_ONE = "main:one";
    private static final String KEY_ACTION_TWO = "main:two";
    private static final String KEY_ACTION_THREE = "main:three";

    @BindView(R.id.btn_1)
    Button btn1;

    @BindView(R.id.btn_2)
    Button btn2;

    @BindView(R.id.btn_3)
    Button btn3;

    @BindView(R.id.btn_4)
    Button refreshBtn;
    RouterService service = ServiceCreator.createRouterService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        refreshRouter();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterTry.tryOpenOr(MainActivity.this, RouterCache.getRoute(KEY_ACTION_ONE), "activity://two");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<String, String>();
                map.put(TwoActivity.KEY_NAME, "Tango");
                RouterTry.tryOpenOr(MainActivity.this, RouterCache.getRoute(KEY_ACTION_TWO), "activity://three", map);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterTry.tryOpenOr(MainActivity.this, RouterCache.getRoute(KEY_ACTION_THREE), "http://www.baidu.com");
            }
        });

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshRouter();
            }
        });
    }



    void refreshRouter(){
        service.getRouters()
                .enqueue(new Callback<Map<String, String>>() {
                    @Override
                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                        if(response != null && response.body() != null) {
                            RouterCache.updateRouter(response.body());
                        } else {
                            Timber.w("路由更新失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<Map<String, String>> call, Throwable t) {
                        Timber.e(t, "路由更新失败");
                    }
                });
    }

}
