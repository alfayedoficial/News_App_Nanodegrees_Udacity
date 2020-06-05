package com.alialfayed.newsappnanodegreesudacity.utilities;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.alialfayed.newsappnanodegreesudacity.MainActivity;
import com.alialfayed.newsappnanodegreesudacity.model.ImageModel;
import com.alialfayed.newsappnanodegreesudacity.model.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ( Eng Ali Al Fayed)
 * Class do :-
 * * Process the locally saved JSON data String and return a List of articles with the
 * * parameters which are specified in the for loop.
 * Date 6/5/2020 - 3:50 PM
 */

public abstract class JsonDataConverter {

    public static List<NewsModel> extractJsonToArrayList(String json) {
        ArrayList<NewsModel> articles = new ArrayList<>();
        try {
            // Traverse the json String
            JSONObject root = new JSONObject(json);
            JSONObject response = root.getJSONObject(Constant.RESPONSE);
            JSONArray results = response.getJSONArray(Constant.RESULTS);

            // Loop the json array and add the parameters to the articles ArrayList
            for (int i = 0; i < results.length(); i++) {

                // Get Object from array position (i)
                JSONObject j = results.getJSONObject(i);

                // Get fields object
                JSONObject fields = j.getJSONObject(Constant.FIELDS);

                // Get tags array
                JSONArray tagsArray = j.getJSONArray(Constant.TAGS);

                /* Get values from json and put into local Strings, use optString instead of
                 * getString for values that might not exist when called, if they don't exist
                 * they will instead return an empty String. */
                String headline =j.getString(Constant.WEB_TITLE);
                String section = j.getString(Constant.SECTION_NAME);
                String date = j.getString(Constant.WEB_PUBLICATION_DATE);
                String url = j.getString(Constant.WEB_URL);
                String summary = fields.optString(Constant.TRAIL_TEXT);
                String imageUrl = fields.optString(Constant.THUMBNAIL);
                String bodyText = fields.optString(Constant.BODY_TEXT);
                String author = tagsArray.length() > 0 ?
                                tagsArray.getJSONObject(0).optString(Constant.WEB_TITLE) : "";

                Drawable drawableImage = null;

                if(MainActivity.isEnableImages()) {
                    // Iterate existing images and set if already exist in cache
                    if (!ImageCache.getImageCache().isEmpty()) {
                        for (ImageModel imageData : ImageCache.getImageCache()) {
                            if (imageUrl.equals(imageData.getImageUrl())) {
                                drawableImage = imageData.getImage();
                                break;
                            }
                        }
                    }

                    // Fetch image as drawable if it didn't already exist
                    if (drawableImage == null) {
                        try {
                            drawableImage = UrlDataFetcher.getDrawableFromUrl(imageUrl);
                        } catch (IOException e) {
                            Log.e(Constant.TAG_JASON_DATA_CONVERTER, Constant.ERROR_TAG_JASON_DATA_CONVERTER_1, e);
                        } finally {
                            // Add image to list of images
                            ImageCache.addImage(new ImageModel(imageUrl, drawableImage));
                        }
                    }
                }

                // Add article to ArrayList
                articles.add(new NewsModel(headline,summary,section,author,date,url,bodyText,
                        imageUrl, drawableImage));
            }
        } catch (JSONException e) {
            Log.e(Constant.TAG_JASON_DATA_CONVERTER, Constant.ERROR_TAG_JASON_DATA_CONVERTER_2, e);
        }
        return articles;
    }
}
