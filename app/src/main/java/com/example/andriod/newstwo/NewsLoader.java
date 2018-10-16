package com.example.andriod.newstwo;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class NewsLoader extends AsyncTaskLoader {

    private String nUrl;

    NewsLoader(Context context, String Url) {
        super( context );
        nUrl = Url;

    }

    @Override
    public List<News> loadInBackground() {
        //==========================================//
        // this part performs network call         //
        //========================================//

        List<News> newsItems = new ArrayList<>();
        if (nUrl == null) {
            return null;
        }
        try {
            newsItems = NewsUtility.fetchNewsItems( nUrl );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsItems;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
