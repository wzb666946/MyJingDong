package com.example.com.myjingdong.home.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.com.myjingdong.R;

public class KillActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar bar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kill);
        Intent intent=getIntent();
        String url = intent.getStringExtra("url");
        //获取资源ID
        webView = (WebView) findViewById(R.id.webview);
        bar = (ProgressBar) findViewById(R.id.bar);
        textView = (TextView) findViewById(R.id.text);
        //加载网页
        webView.loadUrl(url);
        //webSettings辅助类
        webView.getSettings().setBuiltInZoomControls(true);
        //设置编码格式
        webView.getSettings().setDefaultTextEncodingName("utf-8");

        webView.setWebChromeClient(new WebChromeClient(){


            //用来获得网页的标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                // TODO Auto-generated method stub
                super.onReceivedTitle(view, title);

                textView.setText(title);

            }


            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                super.onProgressChanged(view, newProgress);

                Log.i("TAG", newProgress+"");
                bar.setProgress(newProgress);
            }



        });

    }
}
