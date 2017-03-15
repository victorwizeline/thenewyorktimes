package com.codepath.thenewyorktimes.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.thenewyorktimes.R;
import com.codepath.thenewyorktimes.models.Article;
import com.codepath.thenewyorktimes.models.Multimedia;
import com.codepath.thenewyorktimes.models.SearchResults;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private SearchResults searchResults;

    public SearchAdapter(SearchResults searchResults) {
        this.searchResults = searchResults;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(searchResults.getResponse().getArticles().get(position));
    }

    @Override
    public int getItemCount() {
        return searchResults.getResponse().getArticles().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivThumbnail;
        TextView tvHeadline;

        ViewHolder(View itemView) {
            super(itemView);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.ivThumbnail);
            tvHeadline = (TextView) itemView.findViewById(R.id.tvHeadline);
        }

        void bind(Article article) {
            tvHeadline.setText(article.getHeadline().getMain());
            if (article.getMultimedia().size() > 0) {
                for (Multimedia multimedia : article.getMultimedia()) {
                    if (multimedia.getSubtype() != null && multimedia.getSubtype().equalsIgnoreCase("thumbnail")) {
                        Glide.with(itemView.getContext()).load(multimedia.getUrl()).into(ivThumbnail);
                        break;
                    }
                }
            }
        }
    }
}
