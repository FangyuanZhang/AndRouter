package com.laobai.dynamicrouter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.laobai.dynamicrouter.router.RouterCache;
import com.laobai.dynamicrouter.router.RouterTryer;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.campusapp.router.annotation.RouterMap;

@RouterMap("activity://three")
public class ThreeActivity extends AppCompatActivity {
    private static final String KEY_ACTION_ONE = "three:one";

    @BindView(R.id.btn_1)
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        ButterKnife.bind(this);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterTryer.tryOpenOr(ThreeActivity.this, RouterCache.getRoute(KEY_ACTION_ONE), "http://www.sina.com");
            }
        });
    }
}
