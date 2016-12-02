package com.laobai.dynamicrouter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.campusapp.router.utils.UrlUtils;
import timber.log.Timber;

/**
 * 全功能WebView
 * <p>
 * 1. 支持js
 * 2. 拦截confirm、alert
 * 3. 支持cookie
 *
 * @author nius
 */
public class FunctionalWebView extends WebView {

    Context mContext;
    boolean hasLoad = false;


    ProgressChangedListener mProgressChangedListener;


    PageLoadedFinishCallback mPageLoadedFinishCallback;


    public FunctionalWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FunctionalWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FunctionalWebView(Context context) {
        this(context, null);
    }

    public void setContext(Context context) {
        mContext = context;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        getSettings().setJavaScriptEnabled(true);
        getSettings().setBuiltInZoomControls(true);
        getSettings().setDisplayZoomControls(false);

        CookieManager.getInstance().setAcceptCookie(true);

        setWebViewClient(new CaptureWebViewClient());
        setWebChromeClient(new CaptureChromeClient());

        //去除远程代码执行漏洞
        removeJavascriptInterface("searchBoxJavaBridge_");
        removeJavascriptInterface("accessibilityTraversal");
        removeJavascriptInterface("accessibility");
    }


    public void setPageLoadedFinishCallback(PageLoadedFinishCallback callback){
        this.mPageLoadedFinishCallback = callback;
    }

    public void setProgressChangedListener(ProgressChangedListener listener) {
        this.mProgressChangedListener = listener;
    }


    public interface ProgressChangedListener {
        void onProgressChanged(int progress);
    }


    public interface PageLoadedFinishCallback{
        void onPageLoaded(WebView webView, String url);
    }

    private class CaptureWebViewClient extends WebViewClient {
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if(mPageLoadedFinishCallback != null){
                mPageLoadedFinishCallback.onPageLoaded(view, url);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(TextUtils.equals(UrlUtils.getScheme(url), "dynamic") || TextUtils.equals(UrlUtils.getScheme(url), "dynamicWeb")) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                mContext.startActivity(intent);
                return true;
            } else {
                return false;
            }
        }
    }



    private class CaptureChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 JsResult result) {
            Timber.d(message);
            result.confirm();
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url,
                                   String message, JsResult result) {
            result.confirm();
            return true;
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage cm) {
            Timber.tag("WEBVIEW-" + cm.messageLevel()).d(String.format("%s     -- from %s: %d",
                    cm.message(), cm.sourceId(), cm.lineNumber()));
            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (mProgressChangedListener != null)
                mProgressChangedListener.onProgressChanged(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }


}
