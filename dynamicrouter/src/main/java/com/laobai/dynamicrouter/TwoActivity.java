package com.laobai.dynamicrouter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.laobai.dynamicrouter.router.RouterCache;
import com.laobai.dynamicrouter.router.RouterTryer;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.campusapp.router.annotation.RouterMap;
@RouterMap("activity://two")
public class TwoActivity extends AppCompatActivity {

    public static final String KEY_NAME = "NAME";
    private static final String KEY_ACTION_ONE = "two:one";

    @BindView(R.id.btn_1)
    Button vBtn1;

    @BindView(R.id.hello_tv)
    TextView vHelloTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        ButterKnife.bind(this);
        String name = getIntent().getStringExtra(KEY_NAME);
        vBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这样直接写btn one 的key不太好，最好改成annotation的方式
                RouterTryer.tryOpenOr(TwoActivity.this, RouterCache.getRoute(KEY_ACTION_ONE), "activity://three");
            }
        });

        vHelloTv.setText("Hello " + name);
    }



}
