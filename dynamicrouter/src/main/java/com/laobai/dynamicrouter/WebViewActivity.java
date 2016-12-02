package com.laobai.dynamicrouter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import timber.log.Timber;

/**
 * webview，用于关于页面
 */
public class WebViewActivity extends AppCompatActivity {
    public static final String ABOUT_BLANK = "about:blank";   //空白页
    public static final String WEB_APP_URL_KEY = "webapp";
    public static final String WEB_APP_TITLE = "webtitle";
    public static final String JS_INTERFACE_NAME = "WebView";

    protected FrameLayout webViewWrapper;
    protected ProgressBar mProgressBar;
    FunctionalWebView webView;

    public JsAccess jsAccess = new JsAccess();

    public WebViewActivity() {
    }

    public static Intent makeIntent(String url, String title) {
        Intent intent = new Intent(App.getContext(), WebViewActivity.class);
        intent.putExtra(WEB_APP_URL_KEY, url);
        intent.putExtra(WEB_APP_TITLE, title);
        return intent;
    }

    public static Intent makeIntent(String url) {
        return makeIntent(url, App.getContext().getString(R.string.app_name));
    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webViewWrapper = ((FrameLayout) findViewById(R.id.web_view_wrapper));
        webView = (FunctionalWebView) findViewById(R.id.functional_web_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        webView.setContext(WebViewActivity.this);
        webView.requestFocus(View.FOCUS_DOWN);
        if( android.os.Build.VERSION.SDK_INT >= 17) {
            webView.addJavascriptInterface(jsAccess, JS_INTERFACE_NAME);
        }

        webView.setProgressChangedListener(new FunctionalWebView.ProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress) {
                if (progress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if (mProgressBar.getVisibility() == View.GONE)
                        mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(progress);
                }
            }
        });


        webView.setPageLoadedFinishCallback(new FunctionalWebView.PageLoadedFinishCallback() {
            @Override
            public void onPageLoaded(WebView webView, String url) {
            }
        });

//        /**
//         * 加载页面完成后, 将页面内容发送给 {@link #processHTML(String)} 处理
//         */

        final String title = getIntent().getStringExtra(WEB_APP_TITLE);


        processIntent();
    }

    protected void processIntent() {
        Intent intent = getIntent();
        try {
            String urlString = intent.getExtras().getString(WEB_APP_URL_KEY);
            //过滤空格
            urlString = urlString.replaceAll("\\s+", "");
            Uri uri = Uri.parse(urlString);
            //屏蔽掉非http或者https协议的链接
            if (!TextUtils.equals(uri.getScheme(), "http") && !TextUtils.equals(uri.getScheme(), "https")) {
                Toast.makeText(this, "非法的网页链接", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            Timber.d("LOAD URL %s", uri.toString());
            webView.loadUrl(uri.toString());
        } catch (Exception e) {
            Timber.e(e, "NO WEB APP HERE");
            Toast.makeText(this, "无法加载网页内容", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        destroyWebView();
        super.onDestroy();
    }

    protected void destroyWebView() {
        try {
            webViewWrapper.removeAllViews();
            if (webView != null) {
                webView.clearFocus();
                webView.clearHistory();
                webView.clearCache(true);
                webView.loadUrl(ABOUT_BLANK);
                webView.setWebViewClient(null);
                webView.setProgressChangedListener(null);
                webView.removeJavascriptInterface(JS_INTERFACE_NAME);
                webView.setContext(null);
                webView.destroy();
                webView = null;
                System.gc();
                System.gc();
            }
        } catch (Throwable t) {
            Timber.wtf(t, "wtf");
        }
    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 如果需要让js调用Java代码
     */
    public class JsAccess {

    }
}
