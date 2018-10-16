package com.example.andriod.newstwo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsWebDetails extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_news_web_details );

        //===========================================================//
        // this part gets the webview by id, load the url and       //
        // receive the clicked news from the mainactivity via      //
        // intent and shows the user the complete news            //
        //=======================================================//

        webView = findViewById( R.id.webView );

        webView.setWebViewClient( new WebViewClient() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                view.loadUrl( request.getUrl().toString() );

                return false;
            }
        } );

        Intent in = getIntent();
        String mURL = in.getStringExtra( "URL" );
        webView.loadUrl( mURL );
    }
}
