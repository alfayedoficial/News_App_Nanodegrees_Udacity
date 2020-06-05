package com.alialfayed.newsappnanodegreesudacity.utilities;

import android.util.Log;

import com.alialfayed.newsappnanodegreesudacity.model.ImageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ( Eng Ali Al Fayed)
 * Class do :-
 * * Keeps a static list of ArticleImages as a sort of cache for when the app
 * * is running. This way a recently downloaded image will not download a second
 * * time, thus saving bandwidth and time. Cache size is set by the user in
 * * Settings activity.
 * Date 6/5/2020 - 3:40 PM
 */

public abstract class ImageCache {

    private static ArrayList<ImageModel> imageCache = new ArrayList<>();

    public static List<ImageModel> getImageCache() {
        return new ArrayList<>(imageCache);
    }

    public static void addImage(ImageModel imageModel) {
        imageCache.add(imageModel);
    }

    public static void removeImageAtIndex(int index) {
        if(index < imageCache.size()) {
            imageCache.remove(index);
        } else {
            Log.e(Constant.TAG_IMAGE_CACHE,Constant.ERROR_TAG_IMAGE_CACHE);
        }
    }

    public static void clear() {
        imageCache.clear();
    }
}
