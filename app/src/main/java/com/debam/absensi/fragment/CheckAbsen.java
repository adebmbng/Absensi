package com.debam.absensi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.debam.absensi.BaseFragment;
import com.debam.absensi.R;
import com.debam.absensi.utils.Constant;

/**
 * Created by Debam on 11/28/2016.
 */

public class CheckAbsen extends BaseFragment {

    Context ctx;
    WebView mWebview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.content_checkabsen,container, false);
        mWebview = (WebView) rootview.findViewById(R.id.wView);
        mWebview.setWebViewClient(new MyBrowser());
        mWebview.loadUrl(Constant.URL.checkAbsen);
        mWebview.getSettings().setLoadsImagesAutomatically(true);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebview.reload();
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
