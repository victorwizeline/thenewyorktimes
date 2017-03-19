package com.codepath.thenewyorktimes.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.thenewyorktimes.R;
import com.codepath.thenewyorktimes.models.Article;
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
        Context context = holder.itemView.getContext();
        Article article = articles.get(position);
        holder.tvHeadline.setText(article.getHeadline().getMain());
        holder.tvLeadParagraph.setText(article.getLeadParagraph());
        holder.ivThumbnail.getLayoutParams().height = getScaledHeight(context);
        Glide.with(context).load(article.getWideImage()).placeholder(R.drawable.placeholder).into(holder.ivThumbnail);
    }

    private int getScaledHeight(Context context) {
        int totalWidth = Utils.getDisplayMetrics(context).widthPixels;
        return ((totalWidth / 2) / 16) * 9;
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivThumbnail;
        TextView tvHeadline;
        TextView tvLeadParagraph;

        ViewHolder(View itemView) {
            super(itemView);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.ivThumbnail);
            tvHeadline = (TextView) itemView.findViewById(R.id.tvHeadline);
            tvLeadParagraph = (TextView) itemView.findViewById(R.id.tvLeadParagraph);
        }
    }
}
