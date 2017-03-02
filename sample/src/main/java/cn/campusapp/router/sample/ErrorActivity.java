package cn.campusapp.router.sample;

import android.app.Activity;
import android.os.Bundle;

import cn.campusapp.router.annotation.RouterMap;

/**
 * Created by kris on 17/3/2.
 */
@RouterMap("activity://error")
public class ErrorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

    }
}
