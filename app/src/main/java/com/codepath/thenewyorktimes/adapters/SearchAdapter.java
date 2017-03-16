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
import com.codepath.thenewyorktimes.utils.Utils;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<Article> articles;

    public SearchAdapter(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivThumbnail;
        private TextView tvHeadline;
        private TextView tvLeadParagraph;

        ViewHolder(View itemView) {
            super(itemView);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.ivThumbnail);
            tvHeadline = (TextView) itemView.findViewById(R.id.tvHeadline);
            tvLeadParagraph = (TextView) itemView.findViewById(R.id.tvLeadParagraph);
        }

        void bind(Article article) {
            tvHeadline.setText(article.getHeadline().getMain());
            tvLeadParagraph.setText(article.getLeadParagraph());
            ivThumbnail.getLayoutParams().height = getScaledHeight();
            if (article.getMultimedia().size() > 0) {
                for (Multimedia multimedia : article.getMultimedia()) {
                    if (multimedia.getSubtype() != null && multimedia.getSubtype().equalsIgnoreCase("wide")) {
                        Glide.with(itemView.getContext()).load(multimedia.getUrl()).into(ivThumbnail);
                        break;
                    }
                }
            }
        }

        int getScaledHeight() {
            int totalWidth = Utils.getDisplayMetrics(itemView.getContext()).widthPixels;
            return ((totalWidth / 2) / 16) * 9;
        }
    }
}
