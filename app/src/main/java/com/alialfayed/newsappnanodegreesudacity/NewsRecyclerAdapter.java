package com.alialfayed.newsappnanodegreesudacity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alialfayed.newsappnanodegreesudacity.model.NewsModel;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by ( Eng Ali Al Fayed)
 * Class do :
 * Date 6/5/2020 - 6:25 PM
 */
class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsRecyclerHolder> {

    private ArrayList<NewsModel> newsModels;
    private OnNewsListener onNewsListener;

    public NewsRecyclerAdapter(ArrayList<NewsModel> newsModels, OnNewsListener onNewsListener) {
        this.newsModels = newsModels;
        this.onNewsListener = onNewsListener;
    }

    @NonNull
    @Override
    public NewsRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);

        return new NewsRecyclerHolder(view, onNewsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerHolder holder, int position) {
        final NewsModel currentNews = newsModels.get(position);

        if (currentNews != null) {

            // Set headline
            if (currentNews.getHeadline() != null) {
                // Remove author from headline
                StringBuilder headline = new StringBuilder(currentNews.getHeadline());
                if (headline.toString().contains(" |")) {
                    headline.delete(headline.indexOf(" |"), headline.length());
                }
                holder.txtHeadline_News.setText(headline);
            }

            // Set summary
            if (currentNews.getSummary() != null) {
                StringBuilder summary = new StringBuilder(currentNews.getSummary());
                // Remove unwanted HTML tags such as <br>
                while (summary.indexOf("<") != -1 && summary.indexOf(">") != -1) {
                    int startIndex = summary.indexOf("<"),
                            endIndex = summary.indexOf(">") + 1;
                    summary.delete(startIndex, endIndex);
                }
                // Remove the extra space that sometimes appears at end of summary
                if (summary.lastIndexOf(" ") == summary.length() - 1) {
                    summary.deleteCharAt(summary.length() - 1);
                }
                // Add a couple of dots to the summary
                holder.txtSummary_News.setText(summary);
            }

            // Set section
            if (currentNews.getSection() != null) {
                holder.txtSection_News.setText(currentNews.getSection());
            }

            // Set author, hide view if the article has no author
            if (currentNews.getAuthor() != null) {
                holder.txtAuthor_News.setText(currentNews.getAuthor());
            } else {
                holder.txtAuthor_News.setVisibility(View.GONE);
            }

            // Set date
            if (currentNews.getDate() != null) {
                /* Original String format: YYYY-MM-DD
                 * Rebuilt version: DD MMM YYYY */
                int lastDateIndex = currentNews.getDate().indexOf("T".toUpperCase()),
                        monthFirstIndex = currentNews.getDate().charAt(5) == '0' ? 6 : 5;
                StringBuilder month = new StringBuilder(new DateFormatSymbols().getMonths()[Integer.
                        valueOf(currentNews.getDate().substring(monthFirstIndex, 7)) - 1]);
                month.delete(3, month.length());
                StringBuilder date = new StringBuilder(currentNews.getDate().
                        substring(lastDateIndex - 2, lastDateIndex));
                date.append(" ").append(month).append(" ").
                        append(currentNews.getDate().substring(0, 4));
                holder.txtDate_News.setText(date);
            }

            // Hide divider if either author or date is missing
            if (currentNews.getAuthor() == null || currentNews.getDate() == null) {
                holder.divider.setVisibility(View.GONE);
            }

            // Set image, hide view if the article has no image
            if (currentNews.getImageDrawable() != null) {
                holder.imgNews.setImageDrawable(currentNews.getImageDrawable());
            } else {
                holder.imgNews.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    public void clear() {
        newsModels.clear();
        notifyDataSetChanged();
    }

    public void updateAdapter(List<NewsModel> newsModelsList) {
        if (newsModelsList != null && !newsModelsList.isEmpty()) {
            this.newsModels.addAll(newsModelsList);
        } else {
            Log.i("TAG", "Invalid or empty List of articles.");
        }
    }

    public ArrayList<NewsModel> getNews() {
        return newsModels;
    }

    static class NewsRecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtSection_News;
        private TextView txtHeadline_News;
        private TextView txtSummary_News;
        private TextView txtDate_News;
        private TextView txtAuthor_News;
        private View divider;
        private ImageView imgNews;

        private OnNewsListener onNewsListener;

        public NewsRecyclerHolder(@NonNull View itemView, OnNewsListener onNewsListener) {
            super(itemView);
            txtSection_News = itemView.findViewById(R.id.txtSection_News);
            txtHeadline_News = itemView.findViewById(R.id.txtHeadline_News);
            txtSummary_News = itemView.findViewById(R.id.txtSummary_News);
            txtDate_News = itemView.findViewById(R.id.txtDate_News);
            txtAuthor_News = itemView.findViewById(R.id.txtAuthor_News);
            divider = itemView.findViewById(R.id.divider);
            imgNews = itemView.findViewById(R.id.imgNews);

            this.onNewsListener = onNewsListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNewsListener.onNewsClick(getAdapterPosition());
        }
    }
}
