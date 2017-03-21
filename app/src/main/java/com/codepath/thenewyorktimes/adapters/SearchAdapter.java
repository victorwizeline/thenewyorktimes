package com.codepath.thenewyorktimes.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.thenewyorktimes.R;
import com.codepath.thenewyorktimes.activities.WebViewActivity;
import com.codepath.thenewyorktimes.models.Article;
import com.codepath.thenewyorktimes.utils.Utils;

import org.parceler.Parcels;

import java.util.List;

import static com.codepath.thenewyorktimes.utils.Constants.WEB_URL;

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
        holder.tvHeadline.setTypeface(Utils.getCheltenhamFont(holder.itemView.getContext()));
        holder.tvOriginal.setText(article.getByline().getOriginal());
        holder.tvOriginal.setTypeface(Utils.getGeorgiaFont(holder.itemView.getContext()));
        holder.tvLeadParagraph.setText(article.getLeadParagraph());
        holder.tvLeadParagraph.setTypeface(Utils.getGeorgiaFont(holder.itemView.getContext()));
        holder.ivThumbnail.getLayoutParams().height = getScaledHeight(context);
        Glide.with(context).load(article.getWideImage()).placeholder(R.drawable.placeholder).into(holder.ivThumbnail);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra(WEB_URL, article.getWebUrl());
            context.startActivity(intent);
        });
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
        TextView tvOriginal;
        TextView tvLeadParagraph;

        ViewHolder(View itemView) {
            super(itemView);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.ivThumbnail);
            tvHeadline = (TextView) itemView.findViewById(R.id.tvHeadline);
            tvOriginal = (TextView) itemView.findViewById(R.id.tvOriginal);
            tvLeadParagraph = (TextView) itemView.findViewById(R.id.tvLeadParagraph);
        }
    }
}
