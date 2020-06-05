package com.alialfayed.newsappnanodegreesudacity.utilities;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public abstract class UrlDataFetcher {


    /**
     * Make a HTTP request to the given URL and return a String as the response.
     */
    public static String getJsonStringFromUrl(String jsonUrl) throws IOException {
        String jsonRespone = "";

        // Convert jsonUrl (String) to URL Object
        URL url = createUrl(jsonUrl);

        // Return empty String if url is null
        if (url == null) {
            return jsonRespone;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(Constant.GET);
            urlConnection.setReadTimeout(Constant.READ_TIMEOUT_MS); // Milliseconds, 10000 = 10s
            urlConnection.setConnectTimeout(Constant.CONNECT_TIMEOUT_MS); // Milliseconds, 15000 = 15s
            urlConnection.connect();

            if (urlConnection.getResponseCode() == Constant.STRING_200) {
                Log.v(Constant.TAG_URL_DATA_FETCHER, Constant.ERROR_TAG_URL_DATA_FETCHER_1 +
                        urlConnection.getResponseCode());

                // Get the stream
                inputStream = urlConnection.getInputStream();

                // Call readJsonFromStream to convert the data stream to String
                jsonRespone = readJsonFromStream(inputStream);
            } else {
                Log.e(Constant.TAG_URL_DATA_FETCHER, Constant.ERROR_TAG_URL_DATA_FETCHER_2 + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(Constant.TAG_URL_DATA_FETCHER, Constant.ERROR_TAG_URL_DATA_FETCHER_3, e);
        } finally {
            // Disconnect the connection
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            // Close the stream
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonRespone;
    }


    /**
     * Make a HTTP request to the given URL and return a Drawable as the response.
     */
    public static Drawable getDrawableFromUrl(String imageUrl) throws IOException {
        // Return early if imageUrl is null or empty
        if (imageUrl == null || imageUrl.isEmpty()) {
            return null;
        }

        // Convert jsonUrl (String) to URL Object
        URL url = createUrl(imageUrl);

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        Drawable drawable = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(Constant.GET);
            urlConnection.setReadTimeout(Constant.READ_TIMEOUT_MS);
            urlConnection.setConnectTimeout(Constant.CONNECT_TIMEOUT_MS);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == Constant.STRING_200) {
                Log.v(Constant.TAG_URL_DATA_FETCHER, Constant.ERROR_TAG_JASON_DATA_CONVERTER_1 +
                        urlConnection.getResponseCode());

                // Get the stream
                inputStream = (InputStream) url.getContent();

                // Call readJsonFromStream to convert the data stream to Drawable
                drawable = Drawable.createFromStream(inputStream, Constant.IMAGE_SRC);
            } else {
                Log.e(Constant.TAG_URL_DATA_FETCHER, Constant.ERROR_TAG_URL_DATA_FETCHER_2 + urlConnection.getResponseCode());
            }
        } catch (NullPointerException e) {
            Log.e(Constant.TAG_URL_DATA_FETCHER, Constant.ERROR_TAG_URL_DATA_FETCHER_4, e);
        } finally {
            // Disconnect the connection
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            // Close the stream
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return drawable;
    }


    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(Constant.TAG_URL_DATA_FETCHER, Constant.ERROR_TAG_URL_DATA_FETCHER_5 + stringUrl, e);
            return null;
        }

        return url;
    }


    /**
     * Convert the InputSteam to a String which contains the JSON response from the server.
     */
    private static String readJsonFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            // Create inputStreamReader for the data stream
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
                    StandardCharsets.UTF_8);

            // Append the data stream to StringBuilder
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) /* Possible IOException */ {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }

        return output.toString();
    }
}
