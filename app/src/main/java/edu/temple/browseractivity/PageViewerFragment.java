package edu.temple.browseractivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.PatternsCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.regex.Pattern;


public class PageViewerFragment extends Fragment {
    WebView webber;
    Wave parentActivityInterface;
    String urlKey = "";
    int index;

    public PageViewerFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof Wave) {
            parentActivityInterface = (Wave) context;
        } else {
            throw new RuntimeException("You must implement ButtonClickInterface to attach this fragment");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View webv = inflater.inflate(R.layout.fragment_page_viewer, container, false);

        webber = webv.findViewById(R.id.webv);
        WebSettings webset = webber.getSettings();
        webset.setJavaScriptEnabled(true);
        webber.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                parentActivityInterface.updateText(url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                urlKey = view.getTitle();
                parentActivityInterface.updateTitle(urlKey, index);
                parentActivityInterface.updateText(url);

            }


        });



        return webv;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Store URL and previous/back in case fragment is restarted
        webber.saveState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Restore WebView settings
        if (savedInstanceState != null)
            webber.restoreState(savedInstanceState);
    }




    public boolean WebViewGoBack() {

        webber.getSettings().setJavaScriptEnabled(true);
        if (webber.canGoBack()) {
            webber.goBack();
            urlKey = webber.getTitle();
            return true;
        }
        return false; //webview cannot go back, so use the method of the BackButton
    }

    public boolean WebViewGoForward() {

        webber.getSettings().setJavaScriptEnabled(true);
        if (webber.canGoForward()) {
            webber.goForward();
            urlKey = webber.getTitle();
            return true;
        }
        return false; //webview cannot go back, so use the method of the BackButton
    }

    public void WebViewLoad(String url) {

        webber.getSettings().setJavaScriptEnabled(true);

        if(URLUtil.isValidUrl(url)){
            webber.loadUrl(url);
        } else {
            String httpsURL = "https://" + url;
            String httpURL = "http://" + url;
            if(URLUtil.isValidUrl(httpURL)){
                webber.loadUrl(httpURL);
            } else if (URLUtil.isValidUrl(httpsURL)){
                webber.loadUrl(httpsURL);
            } else {
                webber.loadUrl(url);
            }
        }
        urlKey = webber.getTitle();
    }


    interface Wave { //ROCK SHOOT
        void updateText(String url);
        void updateTitle(String url, int index);
    }

}
