package com.alialfayed.newsappnanodegreesudacity.utilities;

/**
 * Created by ( Eng Ali Al Fayed)
 * Class do :-
 * * Constant for final static variables
 * Date 6/5/2020 - 11:35 PM
 */
public class Constant {

    // TAG ID
    public static final String TAG_IMAGE_CACHE = "TAG_IMAGE_CACHE";
    public static final String TAG_JASON_DATA_CONVERTER = "TAG_JASON_CONVERTER";
    public static final String TAG_NEWS_LOADER = "TAG_NEWS_LOADER";
    public static final String TAG_URL_DATA_FETCHER = "TAG_URL_FETCHER";

    // REST API Config
    public static final String GET = "GET";
    public static final String RESPONSE = "response";
    public static final String RESULTS = "results";
    public static final String FIELDS = "fields";
    public static final String TAGS = "tags";
    public static final String WEB_TITLE = "webTitle";
    public static final String SECTION_NAME = "sectionName";
    public static final String WEB_PUBLICATION_DATE = "webPublicationDate";
    public static final String WEB_URL = "webUrl";
    public static final String TRAIL_TEXT = "trailText";
    public static final String THUMBNAIL = "thumbnail";
    public static final String BODY_TEXT = "bodyText";
    public static final String IMAGE_SRC = "imageSrc";
    public static  String jsonUrl;
    public final static String API_PAGES = "50";
    public final static String API_PAGE_SIZE = "10";
    public static final int LOADER_ID = 0;

    // The Guardian API.
    public static final String HTTPS = "https";
    public static final String AUTHORITY = "content.guardianapis.com";
    public static final String SEARCH = "search";
    public static final String SHOW_TAGS = "show-tags";
    public static final String CONTRIBUTOR = "contributor";
    public static final String SHOW_FIELDS = "show-fields";
    public static final String NEWEST = "newest";
    public static final String PAGE_SIZE = "page-size";
    public static final String PAGES = "pages";
    public static final String STRING_PAGE = "page";
    public static final String ORDER_BY = "order-by";
    public static final String API_KEY = "api-key";
    public static final String TRAIL_TEXT_BODY_TEXT = "trailText,thumbnail,body-text";

    public static final String Enable = "Enable";
    public static final String CachedImages = "CachedImages";
    public static final String CachedImages_100 = "100";

    public static final String PAGE = "page=";

    // ERROR MESSAGES
    public static final String ERROR_TAG_JASON_DATA_CONVERTER_1 = "Error getting image from URL.";
    public static final String ERROR_TAG_JASON_DATA_CONVERTER_2 = "Problem parsing the JSON results.";
    public static final String ERROR_TAG_IMAGE_CACHE = "Failed getting Json String from URL.";
    public static final String ERROR_TAG_NEWS_LOADER = "removeNewsAtIndex: index out of range";
    public static final String ERROR_TAG_URL_DATA_FETCHER_1 =  "Connection successful! Response code: ";
    public static final String ERROR_TAG_URL_DATA_FETCHER_2 =  "Error! Response code: ";
    public static final String ERROR_TAG_URL_DATA_FETCHER_3 =  "Problem retrieving JSON data. ";
    public static final String ERROR_TAG_URL_DATA_FETCHER_4 =  "Problem retrieving image. ";
    public static final String ERROR_TAG_URL_DATA_FETCHER_5 =  "Error creating URL: ";

    public static final int READ_TIMEOUT_MS = 10000;
    public static final int CONNECT_TIMEOUT_MS = 15000;
    public static final int STRING_200 = 200;



}
