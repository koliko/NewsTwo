package com.example.andriod.newstwo;

public class News {
    //================================================//
    // this part list items to be displayed on the   //
    // news interface. items are declared as private//
    // variable.                                   //
    //============================================//

    private String newsTitle;
    private String newsAuthor;
    private String newsCategory;
    private String newsDate;
    private String newsUrl;

    //=============================================//
    // this part defines how the news will be     //
    // populated                                 //
    //==========================================//

    public News(String nTitle, String nAuthor, String nCategory, String nUrl, String nDate){

        newsAuthor = nAuthor;
        newsTitle = nTitle;
        newsCategory = nCategory;
        newsDate = nDate;
        newsUrl = nUrl;
    }

    //==================================================//
    // this part set the getter methods                //
    //================================================//


    public String getNewsAuthor() {
        return newsAuthor;
    }

    public String getNewsCategory() {
        return newsCategory;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsUrl() {
        return newsUrl;
    }
}
