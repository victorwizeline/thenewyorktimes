package com.codepath.thenewyorktimes.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.thenewyorktimes.controllers.InfiniteScroll;
import com.codepath.thenewyorktimes.R;
import com.codepath.thenewyorktimes.adapters.SearchAdapter;
import com.codepath.thenewyorktimes.controllers.RetrofitClient;
import com.codepath.thenewyorktimes.databinding.ActivitySearchBinding;
import com.codepath.thenewyorktimes.interfaces.InfiniteScrollListener;
import com.codepath.thenewyorktimes.interfaces.NewYorkTimesClient;
import com.codepath.thenewyorktimes.models.Article;
import com.codepath.thenewyorktimes.models.SearchResults;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL;
import static com.codepath.thenewyorktimes.utils.Constants.DEFAULT_PAGE;
import static com.codepath.thenewyorktimes.utils.Constants.ITEMS_ON_PAGE;
import static com.codepath.thenewyorktimes.utils.Constants.THRESHOLD;

public class SearchActivity extends AppCompatActivity implements Callback<SearchResults>,
        SwipeRefreshLayout.OnRefreshListener, InfiniteScrollListener {

    private List<Article> articles = new ArrayList<>();
    private NewYorkTimesClient newYorkTimesClient;
    private InfiniteScroll infiniteScroll;
    private SearchAdapter searchAdapter;
    private ActivitySearchBinding bind;
    private String currentQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_search);
        bind.swipe.setOnRefreshListener(this);
        newYorkTimesClient = new RetrofitClient();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, VERTICAL);
        searchAdapter = new SearchAdapter(articles);
        infiniteScroll = new InfiniteScroll(layoutManager, ITEMS_ON_PAGE, THRESHOLD, this);
        bind.recycler.setLayoutManager(layoutManager);
        bind.recycler.setAdapter(searchAdapter);
        bind.recycler.addOnScrollListener(infiniteScroll);
    }

    @Override
    public void onLoadMore(int page) {
        infiniteScroll.loadStarted();
        newYorkTimesClient.requestSearchArticles(currentQuery, page, this);
    }

    @Override
    public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
        infiniteScroll.LoadFinished();
        articles.addAll(response.body().getResponse().getArticles());
        searchAdapter.notifyItemRangeInserted(searchAdapter.getItemCount(), response.body().getResponse().getArticles().size());
        bind.swipe.setRefreshing(false);
    }

    @Override
    public void onFailure(Call<SearchResults> call, Throwable t) {
        Log.d(getClass().getSimpleName(), t.getMessage());
        bind.swipe.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        resetSearch();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                currentQuery = query;
                resetSearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetSearch() {
        infiniteScroll.loadStarted();
        articles.clear();
        newYorkTimesClient.requestSearchArticles(currentQuery, DEFAULT_PAGE, this);
    }
}