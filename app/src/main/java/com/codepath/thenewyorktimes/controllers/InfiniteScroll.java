package com.codepath.thenewyorktimes.controllers;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.codepath.thenewyorktimes.interfaces.InfiniteScrollListener;

public class InfiniteScroll extends RecyclerView.OnScrollListener {

    private StaggeredGridLayoutManager layoutManager;
    private InfiniteScrollListener listener;
    private boolean isLoading = false;
    private int itemsOnPage = 0;
    private int threshold = 0;

    public InfiniteScroll(StaggeredGridLayoutManager layoutManager, int itemsOnPage, int threshold, InfiniteScrollListener listener) {
        this.layoutManager = layoutManager;
        this.itemsOnPage = itemsOnPage;
        this.threshold = threshold;
        this.listener = listener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int[] lastVisibleItemPositions = layoutManager.findLastVisibleItemPositions(null);
        int itemsCount = layoutManager.getItemCount();
        int page = itemsCount / itemsOnPage;
        int lastVisibleItem = 0;

        for (int itemPosition : lastVisibleItemPositions) {
            if (lastVisibleItem < itemPosition) {
                lastVisibleItem = itemPosition;
            }
        }

        if (lastVisibleItem + 1 >= itemsCount - threshold && !isLoading) {
            listener.onLoadMore(page);
        }
    }

    public void loadStarted() {
        isLoading = true;
    }

    public void LoadFinished() {
        isLoading = false;
    }

    public boolean isLoading() {
        return isLoading;
    }
}