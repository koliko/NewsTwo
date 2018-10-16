package com.example.andriod.newstwo;


import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>,
        AdapterView.OnItemClickListener {

    //==================================================//
    // this part list the id and url for data parsing  //
    //================================================//

    private final int API_KEY = 1;
    private final String NEWS_URL = " https://content.guardianapis.com/search?api-key=cfb08190-aca6-49db-96e0-c1a662a874c1";
    private ArrayList<News> arrayList;
    private NewsAdapter newsAdapter;
    private TextView newsTextView;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        //================================================//
        // this part gets the system services using the  //
        // the connectivity manager, checks if there is //
        // internet connectivity to populate the news  //
        // and if no internet connectivity is not     //
        // found the system the user is notify       //
        //==========================================//

        ConnectivityManager manager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );

        final NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        listView = findViewById( R.id.list_view );
        newsTextView = findViewById( R.id.text_view );

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader( API_KEY, null, this );
        } else {
            newsTextView.setVisibility( View.VISIBLE );
            newsTextView.setText( R.string.no_internet_connection );
        }
        listView.setOnItemClickListener( this );
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {

        Uri uri = Uri.parse( NEWS_URL );
        Uri.Builder buildUpon = uri.buildUpon();
        return new NewsLoader( this, buildUpon.toString() );
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newList) {
        //============================================//
        // this part creates a new arraylist and     //
        // checks for error if the array list is    //
        // empty                                   //
        //========================================//

        arrayList = new ArrayList<>( newList );

        if (arrayList.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder( this );
            builder.setMessage( R.string.json_error_report )
                    .setTitle( R.string.json_error_report_title );
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        setList( arrayList );
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

        newsAdapter.clear();
    }

    //==================================================//
    // this part creates a new array list and gets     //
    // the application context and append it to the   //
    // list item                                     //
    //==============================================//

    public void setList(ArrayList<News> list) {
        newsAdapter = new NewsAdapter( getApplicationContext(), list );
        listView.setAdapter( newsAdapter );
    }

    //=========================================================//
    // this part is to get position of every news and send    //
    // it to the web browser via intent                      //
    //======================================================//

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent intent = new Intent( getApplicationContext(), NewsWebDetails.class );
        intent.putExtra( "URL", arrayList.get( i ).getNewsUrl() );
        startActivity( intent );
    }
}
