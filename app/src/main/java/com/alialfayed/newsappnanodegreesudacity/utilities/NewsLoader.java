package com.alialfayed.newsappnanodegreesudacity.utilities;

import android.content.AsyncTaskLoader;
import android.content.Context;

import android.util.Log;

import com.alialfayed.newsappnanodegreesudacity.model.NewsModel;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by ( Eng Ali Al Fayed)
 * Class do :-
 * * Performs in the background on a thread separate from the main thread.
 * * Constant for final static variables
 * Date 6/5/2020 - 4:00 PM
 */

public class NewsLoader extends AsyncTaskLoader<List<NewsModel>> {

    private String url;

    /**
     * Constructor for Loader.
     *
     * @param context Context (e.g. this).
     * @param url The Json URL.
     */
    public NewsLoader(@NonNull Context context, String url) {
        super(context);
        this.url = url;
    }


    @Nullable
    @Override
    public List<NewsModel> loadInBackground() {
        // Call jsonUrlDataFetcher, get Json data from jsonUrl and add it to List<NewsModel>
        try {
            String jsonString = UrlDataFetcher.getJsonStringFromUrl(url);
            return JsonDataConverter.extractJsonToArrayList(jsonString);
        } catch (IOException e) {
            Log.e(Constant.TAG_NEWS_LOADER, Constant.ERROR_TAG_NEWS_LOADER, e);
        }
        return null;
    }


    /**
     * Set forceLoad() when Loader is initialized.
     */
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
