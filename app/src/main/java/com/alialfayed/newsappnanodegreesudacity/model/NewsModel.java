package com.alialfayed.newsappnanodegreesudacity.model;

import android.graphics.drawable.Drawable;

/**
 * Created by ( Eng Ali Al Fayed)
 * Class do :-
 * Model to store and get News
 * Date 6/5/2020 - 3:25 PM
 */
public class NewsModel {
    private String headline;
    private String summary;
    private String section;
    private String author;
    private String date;
    private String webUrl;
    private String bodyText;
    private String imageUrl;
    private Drawable imageDrawable;

    /**
     * Constructor for News objects.
     *
     * @param headline      Headline of News.
     * @param summary       Summary text which describes the News.
     * @param bodyText      Body text of News.
     * @param section       Name of section which the News belongs to.
     * @param author        Name of author whom wrote the News.
     * @param date          Date in String format.
     * @param webUrl        Internet URL which links to the full News.
     * @param imageUrl      Internet URL to the image representing the News.
     * @param imageDrawable Image as Drawable Object.
     */
    public NewsModel(String headline
            , String summary
            , String section
            , String author
            , String date
            , String webUrl
            , String bodyText
            , String imageUrl
            , Drawable imageDrawable) {

        this.headline = headline;
        this.summary = summary;
        this.section = section;
        this.author = author;
        this.date = date;
        this.webUrl = webUrl;
        this.bodyText = bodyText;
        this.imageUrl = imageUrl;
        this.imageDrawable = imageDrawable;
    }

    public String getHeadline() {
        return CheckVariable(headline);
    }

    public String getSummary() {
        return CheckVariable(summary);
    }

    public String getSection() {
        return CheckVariable(section);
    }

    public String getAuthor() {
        return CheckVariable(author);
    }

    public String getDate() {
        return CheckVariable(date);
    }

    public String getWebUrl() {
        return CheckVariable(webUrl);
    }

    public String getBodyText() {
        return CheckVariable(bodyText);
    }

    public String getImageUrl() {
        return CheckVariable(imageUrl);
    }

    public Drawable getImageDrawable() {
        return imageDrawable;
    }

    /**
     * Method for Check Variable have data or null
     *
     * @param variable String
     * @return String
     */
    private String CheckVariable(String variable) {
        if (variable.equals("")) {
            return null;
        }
        return variable;
    }
}
