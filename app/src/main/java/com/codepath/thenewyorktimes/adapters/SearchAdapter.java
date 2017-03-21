package com.codepath.thenewyorktimes.adapters;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

import static com.codepath.thenewyorktimes.utils.Constants.WEB_URL;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int LAYOUT_TYPE_IMAGE = 0;
    private final static int LAYOUT_TYPE_NO_IMAGE = 1;

    private List<Article> articles;

    public SearchAdapter(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public int getItemViewType(int position) {
        if (!articles.get(position).getWideImage().equals("")) {
            return LAYOUT_TYPE_IMAGE;
        } else {
            return LAYOUT_TYPE_NO_IMAGE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case LAYOUT_TYPE_IMAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_image, parent, false);
                return new ViewHolderImage(view);
            case LAYOUT_TYPE_NO_IMAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_no_image, parent, false);
                return new ViewHolderNoImage(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_image, parent, false);
                return new ViewHolderImage(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Article article = articles.get(position);
        switch (getItemViewType(position)) {
            case LAYOUT_TYPE_IMAGE:
                ((ViewHolderImage) holder).tvHeadline.setText(article.getHeadline().getMain());
                ((ViewHolderImage) holder).tvHeadline.setTypeface(Utils.getCheltenhamFont(holder.itemView.getContext()));
                ((ViewHolderImage) holder).tvLeadParagraph.setText(article.getLeadParagraph());
                ((ViewHolderImage) holder).tvLeadParagraph.setTypeface(Utils.getGeorgiaFont(holder.itemView.getContext()));
                ((ViewHolderImage) holder).ivThumbnail.getLayoutParams().height = getScaledHeight(context);
                Glide.with(context).load(article.getWideImage()).placeholder(R.drawable.placeholder).into(((ViewHolderImage) holder).ivThumbnail);
                break;
            case LAYOUT_TYPE_NO_IMAGE:
                ((ViewHolderNoImage) holder).tvHeadline.setText(article.getHeadline().getMain());
                ((ViewHolderNoImage) holder).tvHeadline.setTypeface(Utils.getCheltenhamFont(holder.itemView.getContext()));
                ((ViewHolderNoImage) holder).tvLeadParagraph.setText(article.getLeadParagraph());
                ((ViewHolderNoImage) holder).tvLeadParagraph.setTypeface(Utils.getGeorgiaFont(holder.itemView.getContext()));
                break;
        }

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

    private class ViewHolderImage extends RecyclerView.ViewHolder {

        ImageView ivThumbnail;
        TextView tvHeadline;
        TextView tvLeadParagraph;

        ViewHolderImage(View itemView) {
            super(itemView);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.ivThumbnail);
            tvHeadline = (TextView) itemView.findViewById(R.id.tvHeadline);
            tvLeadParagraph = (TextView) itemView.findViewById(R.id.tvLeadParagraph);
        }
    }

    private class ViewHolderNoImage extends RecyclerView.ViewHolder {

        TextView tvHeadline;
        TextView tvLeadParagraph;

        ViewHolderNoImage(View itemView) {
            super(itemView);
            tvHeadline = (TextView) itemView.findViewById(R.id.tvHeadline);
            tvLeadParagraph = (TextView) itemView.findViewById(R.id.tvLeadParagraph);
        }
    }
}
