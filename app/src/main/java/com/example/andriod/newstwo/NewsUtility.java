package com.example.andriod.newstwo;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class NewsUtility {
    //========================================//
    // this part states the error codes      //
    // to be used in the network connection //
    //=====================================//

    private static final int RESPONSE_CODE_OK = 200;
    private static final int CONNECTION_TIMEOUT = 15000;
    private static final int READ_TIMEOUT = 10000;
    private static String nUrl;

    public static String getnUrl() {
        NewsUtility.nUrl = nUrl;

        return nUrl;
    }


    public static List<News> fetchNewsItems(String nUrlRequest) throws JSONException {

        URL url = creatNewUrl( nUrlRequest );
        String JsonResponds = null;

        try {
            JsonResponds = makeHttpRequest( url );

        } catch (IOException e) {
            Log.e( "LOG_TAG", "there is an error", e );
        }
        return parseJSON( JsonResponds );
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String JsonResponds = "";

        if (url == null) {
            return JsonResponds;
        }
        //================================================//
        // this part defines the HttpResponds and check  //
        // for error handling                           //
        //=============================================//

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout( READ_TIMEOUT );
            httpURLConnection.setConnectTimeout( CONNECTION_TIMEOUT );
            httpURLConnection.setRequestMethod( "GET" );
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == RESPONSE_CODE_OK) {
                inputStream = httpURLConnection.getInputStream();
                JsonResponds = readInputStream( inputStream );
            } else {
                Log.e( "LOG_TAG", "Error Responds Codes" + httpURLConnection.getResponseCode() );
            }
        } catch (IOException e) {
            Log.e( "LOG_TAG", "There are issues with news items retrive from the jason", e );
        }
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        } else if (inputStream != null) {
            inputStream.close();
        }
        return JsonResponds;
    }

    //===================================================//
    // this reads the reads data from fetched           //
    // inputstream and return the result               //
    //================================================//
    private static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader( inputStream, Charset.forName( "UTF-8" ) );
            BufferedReader bufferedReader = new BufferedReader( inputStreamReader );

            String newLine = bufferedReader.readLine();
            while (newLine != null) {
                stringBuilder.append( newLine );
                newLine = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    //==================================================//
    // this part creates a new URL object              //
    //================================================//
    private static URL creatNewUrl(String nUrlRequest) {
        URL url = null;
        try {
            url = new URL( nUrlRequest );
        } catch (MalformedURLException e) {
            Log.e( "LOG_TAG", "There is a problem parshing the URL", e );
        }
        return url;
    }

    //=========================================================//
    // this part extract news list items and shows the        //
    // the responds                                          //
    //======================================================//

    private static List<News> parseJSON(String jsonResponds) throws JSONException {
        //=============================================//
        // list of strings to  be used for this task  //
        //===========================================//
        String newsTitle;
        String newsAuthour;
        String newsCategory;
        String newsUrl;
        String newsDate;

        //==============================================//
        // this part set a new Array List              //
        //============================================//

        List<News> newNewsList = new ArrayList<>();

        //===============================================//
        //creating jsonobject and jsonArray             //
        //=============================================//

        JSONObject jsonNewsRootObject = new JSONObject( jsonResponds );
        JSONObject jsonRespondObject = jsonNewsRootObject.getJSONObject( "response" );
        JSONArray jsonArrayObject = jsonRespondObject.getJSONArray( "results" );

        for (int i = 0; i < jsonArrayObject.length(); i++) {
            JSONObject jsonObject = jsonArrayObject.getJSONObject( i );
            newsTitle = jsonObject.getString( "webTitle" );
            newsCategory = jsonObject.getString( "sectionName" );
            newsDate = jsonObject.getString( "webPublicationDate" );
            newsUrl = jsonObject.getString( "webUrl" );

            try {
                JSONArray checkJsonArray = jsonObject.getJSONArray( "tags" );
                JSONObject jsonObject1 = checkJsonArray.getJSONObject( 0 );
                newsAuthour = jsonObject1.getString( "webTitle" );
            } catch (Exception e) {
                newsAuthour = "Not Available";
            }

            //==================================================//
            /// this remove extra characters from date         //
            //================================================//
            newsDate = newsDate.replaceAll( "[a-zA-Z]", " " );

            newNewsList.add( new News( newsTitle, newsDate, newsCategory, newsUrl, newsAuthour ) );
        }

        return newNewsList;
    }

    public void myIntent() {
        List<News> newsArrayList = new ArrayList<>();
    }

}
