package com.alialfayed.newsappnanodegreesudacity.model;

import android.graphics.drawable.Drawable;


/**
 * Created by ( Eng Ali Al Fayed)
 * Class do :-
 * * Holds Drawable of the downloaded image, it also keeps the
 * * imageURL in order to find the correct image for news.
 * Date 6/5/2020 - 3:30 PM
 */

public class ImageModel {
    private String imageUrl;
    private Drawable image;

    public ImageModel(String imageUrl, Drawable image) {
        this.imageUrl = imageUrl;
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Drawable getImage() {
        return image;
    }
}
