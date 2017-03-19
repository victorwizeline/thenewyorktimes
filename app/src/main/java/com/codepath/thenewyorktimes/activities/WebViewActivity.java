package com.codepath.thenewyorktimes.activities;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.codepath.thenewyorktimes.R;
import com.codepath.thenewyorktimes.databinding.ActivityWebViewBinding;

import static com.codepath.thenewyorktimes.utils.Constants.WEB_URL;

public class WebViewActivity extends AppCompatActivity {

    private ActivityWebViewBinding bind;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_web_view);
        bind.webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                bind.webview.setVisibility(View.VISIBLE);
                bind.progress.setVisibility(View.INVISIBLE);
            }
        });
        bind.webview.getSettings().setJavaScriptEnabled(true);
        bind.webview.loadUrl(getIntent().getStringExtra(WEB_URL));
    }

    @Override
    public void onBackPressed() {
        if (bind.webview.canGoBack()) {
            bind.webview.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
