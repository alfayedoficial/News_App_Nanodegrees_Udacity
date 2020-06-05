package com.alialfayed.newsappnanodegreesudacity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alialfayed.newsappnanodegreesudacity.model.NewsModel;
import com.alialfayed.newsappnanodegreesudacity.utilities.Constant;
import com.alialfayed.newsappnanodegreesudacity.utilities.ImageCache;
import com.alialfayed.newsappnanodegreesudacity.utilities.NewsLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsModel>> , OnNewsListener {

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.container)
    ConstraintLayout container;
    @BindView(R.id.imageViewError)
    ImageView imageViewError;
    @BindView(R.id.textViewError)
    TextView textViewError;
    @BindView(R.id.layoutError)
    ConstraintLayout layoutError;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private boolean clearList;
    private boolean isScrolling;
    private static boolean enableImages;

    private LinearLayoutManager manager;
    private NewsRecyclerAdapter adapter;
    private SharedPreferences sharedPreferences = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Shared Preferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        enableImages = sharedPreferences.getBoolean(Constant.Enable,true);

        String jsonAllUrl = uriBuilder();

        if (Constant.jsonUrl == null){
            Constant.jsonUrl = jsonAllUrl;
        }

        // Inflate Adapter
        adapter = new NewsRecyclerAdapter(new ArrayList<NewsModel>(), this);

        manager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));

        // Set swipeRefresh listener
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearList = true;
                setJsonPageNumber(1);
                initLoader();
            }
        });

        // Set scroll listener
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // Scrolled to last item
                if (isScrolling && (manager.getChildCount() +
                        manager.findFirstVisibleItemPosition() == manager.getItemCount())) {
                    isScrolling = false;

                    if (getJsonPageNumber() < Integer.parseInt(Constant.API_PAGES)) {
                        setJsonPageNumber(getJsonPageNumber() + 1);
                        initLoader();
                    }
                }
            }
        });

        // Initialize loader
        initLoader();

    }

    @Override
    protected void onResume() {
        super.onResume();
        clearList = true;
        setJsonPageNumber(1);
        initLoader();
    }

    /**
     * URI Builder for The Guardian API.
     */
    private String uriBuilder() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(Constant.HTTPS)
                .authority(Constant.AUTHORITY)
                .appendPath(Constant.SEARCH)
                .appendQueryParameter(Constant.SHOW_TAGS, Constant.CONTRIBUTOR)
                .appendQueryParameter(Constant.SHOW_FIELDS, Constant.TRAIL_TEXT_BODY_TEXT)
                .appendQueryParameter(Constant.PAGE_SIZE, Constant.API_PAGE_SIZE)
                .appendQueryParameter(Constant.PAGES, Constant.API_PAGES)
                .appendQueryParameter(Constant.STRING_PAGE, "1")
                .appendQueryParameter(Constant.ORDER_BY, Constant.NEWEST)
                .appendQueryParameter(Constant.API_KEY, BuildConfig.API_KEY);

        return builder.build().toString();
    }

    private void initLoader(){
        if (isConnected()){
            // Show refresh indicator
            swipeRefresh.setRefreshing(true);

            /* Set list_error visibility manually to prevent it
             * from showing when first launching the app */
            layoutError.setVisibility(View.GONE);

            // Start/Restart background thread for article processing
            if (getLoaderManager().getLoader(Constant.LOADER_ID) != null) {
                getLoaderManager().restartLoader(Constant.LOADER_ID, null, this);
            } else {
                getLoaderManager().initLoader(Constant.LOADER_ID, null, this);
            }
        }else {
            // Destroy loader
            getLoaderManager().destroyLoader(Constant.LOADER_ID);

            // Hide refresh indicator
            swipeRefresh.setRefreshing(false);

            // Set error text to tell the user that they have no internet connection
            layoutError.setVisibility(View.VISIBLE);
            imageViewError.setImageResource(R.drawable.no_have_internet);
            textViewError.setText(getString(R.string.main_no_internet));
        }
    }

    @Override
    public Loader<List<NewsModel>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this, Constant.jsonUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsModel>> loader, List<NewsModel> newsModels) {
        if (clearList) {
            adapter.clear();
            clearList = false;
        }

        // Update Adapter with news
        if (newsModels != null && !newsModels.isEmpty()) {
            adapter.updateAdapter(newsModels);
        }

        // Clear image cache according to user settings
        int cacheValue = Integer.parseInt(Objects.requireNonNull(sharedPreferences
                .getString(Constant.CachedImages, Constant.CachedImages_100)));
        while (ImageCache.getImageCache().size() > cacheValue) {
            ImageCache.removeImageAtIndex(0);
        }

        // Hide swipeRefresh spinner
        swipeRefresh.setRefreshing(false);

        // Update list_error visibility
        updateErrorVisibility();

    }

    @Override
    public void onLoaderReset(Loader<List<NewsModel>> loader) {
        // Loader is reset, so we clear out existing data
        adapter.clear();

        // Update list_error visibility
        updateErrorVisibility();
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    private void updateErrorVisibility() {
        layoutError.setVisibility(adapter.getItemCount() > 0 ? View.GONE : View.VISIBLE);
        imageViewError.setImageResource(R.drawable.no_have_data);
        textViewError.setText(getString(R.string.main_no_have_data));
    }

    public static boolean isEnableImages() {
        return enableImages;
    }

    @Override
    public void onNewsClick(int position) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(adapter.getNews().get(position).getWebUrl()));
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            this.startActivity(intent);
        }
    }

    /**
     * Get / Set page number of current jsonUrl
     */
    private int getJsonPageNumber() {
        int pageIndex = Constant.jsonUrl.indexOf(Constant.PAGE) + 5;
        if (Constant.jsonUrl.charAt(pageIndex + 1) != '&') {
            return Integer.parseInt(Constant.jsonUrl.substring(pageIndex, pageIndex + 2));
        }
        return Integer.parseInt(Constant.jsonUrl.substring(pageIndex, pageIndex + 1));
    }
    private void setJsonPageNumber(int pageNumber) {
        int pageIndex = Constant.jsonUrl.indexOf(Constant.PAGE) + 5;
        // Make sure pageNumber is in valid range, otherwise set it to min or max
        if(pageNumber < 1) {
            pageNumber = 1;
        } else if(pageNumber > Integer.parseInt(Constant.API_PAGES)) {
            pageNumber = Integer.parseInt(Constant.API_PAGES);
        }
        StringBuilder modifiedJsonUrl = new StringBuilder(Constant.jsonUrl);
        switch (String.valueOf(getJsonPageNumber()).length()) {
            case 1:
                modifiedJsonUrl.deleteCharAt(pageIndex);
                break;
            case 2:
                modifiedJsonUrl.delete(pageIndex, pageIndex + 2);
                break;
        }
        modifiedJsonUrl.insert(pageIndex, pageNumber);
        Constant.jsonUrl = modifiedJsonUrl.toString();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ImageCache.clear();
        finishAffinity();
    }
}