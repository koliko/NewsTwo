package com.example.andriod.newstwo;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    //===================================================//
    // this part declares a constructor with name       //
    // NewsAdapter                                     //
    //================================================//

    public NewsAdapter(Context context, List<News> list) {

        super( context, 0, list );

    }
    //=========================================================//
    // this part declares the getview method that checks to   //
    // to see if a view exist or not. If not the view is     //
    // inflated                                             //
    //=====================================================//


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rootView = convertView;

        if (rootView == null) {
            rootView = LayoutInflater.from( getContext()
            ).inflate( R.layout.new_news_item, parent, false );
        }

        //==============================================================//
        // this part gets individual news items using their position   //
        //============================================================//

        News rootNews = getItem( position );

        TextView newsTitle = rootView.findViewById( R.id.news_title );
        TextView newsAuthor = rootView.findViewById( R.id.news_author );
        TextView newsCategory = rootView.findViewById( R.id.new_category );
        TextView newsDate = rootView.findViewById( R.id.news_date );

        newsTitle.setText( rootNews.getNewsTitle() );
        newsAuthor.setText( rootNews.getNewsAuthor() );
        newsCategory.setText( rootNews.getNewsCategory() );
        newsDate.setText( rootNews.getNewsDate() );

        return rootView;
    }
}
