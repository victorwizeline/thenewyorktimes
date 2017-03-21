package com.codepath.thenewyorktimes.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.thenewyorktimes.controllers.InfiniteScrollListener;
import com.codepath.thenewyorktimes.R;
import com.codepath.thenewyorktimes.adapters.SearchAdapter;
import com.codepath.thenewyorktimes.controllers.RetrofitClient;
import com.codepath.thenewyorktimes.databinding.ActivitySearchBinding;
import com.codepath.thenewyorktimes.fragments.FilterFragmentDialog;
import com.codepath.thenewyorktimes.interfaces.FilterFragmentDialogListener;
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
import static com.codepath.thenewyorktimes.utils.Constants.TWO_COLUMS;

public class SearchActivity extends AppCompatActivity implements Callback<SearchResults>,
        InfiniteScrollListener.ScrollListener, FilterFragmentDialogListener {

    private List<Article> articles = new ArrayList<>();
    private StaggeredGridLayoutManager layoutManager;
    private NewYorkTimesClient newYorkTimesClient;
    private InfiniteScrollListener infiniteScrollListener;
    private SearchAdapter searchAdapter;
    private ActivitySearchBinding bind;
    private String currentQuery;
    private String date;
    private String sort;
    private String newsDesk;
    private SearchType searchType;

    private enum SearchType {
        DEFAULT,
        QUERIED,
        FILTERED
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_search);
        setSupportActionBar(bind.toolbar);
        setupControllers();
        setupRecyclerView();

        articles.clear();
        searchType = SearchType.DEFAULT;
        infiniteScrollListener.loadStarted();
        newYorkTimesClient.requestQueriedArticles(DEFAULT_PAGE, this);
    }

    private void setupControllers() {
        newYorkTimesClient = new RetrofitClient();
        layoutManager = new StaggeredGridLayoutManager(TWO_COLUMS, VERTICAL);
        searchAdapter = new SearchAdapter(articles);
        infiniteScrollListener = new InfiniteScrollListener(layoutManager, ITEMS_ON_PAGE, THRESHOLD, this);
    }

    private void setupRecyclerView() {
        bind.recycler.setLayoutManager(layoutManager);
        bind.recycler.setAdapter(searchAdapter);
        bind.recycler.addOnScrollListener(infiniteScrollListener);
    }

    @Override
    public void onLoadMore(int page) {
        infiniteScrollListener.loadStarted();
        if (searchType == SearchType.DEFAULT) {
            newYorkTimesClient.requestQueriedArticles(page, this);
        } else if (searchType == SearchType.QUERIED) {
            newYorkTimesClient.requestQueriedArticles(currentQuery, page, this);
        } else if (searchType == SearchType.FILTERED) {
            newYorkTimesClient.requestFilteredArticles(date, sort, newsDesk, page, this);
        }
    }

    @Override
    public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
        infiniteScrollListener.LoadFinished();
        articles.addAll(response.body().getResponse().getArticles());
        searchAdapter.notifyItemRangeInserted(searchAdapter.getItemCount(), response.body().getResponse().getArticles().size());
    }

    @Override
    public void onFailure(Call<SearchResults> call, Throwable t) {
        Log.d(getClass().getSimpleName(), t.getMessage());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!infiniteScrollListener.isLoading()) {
                    currentQuery = query;
                    searchView.clearFocus();

                    articles.clear();
                    searchType = SearchType.QUERIED;
                    infiniteScrollListener.loadStarted();
                    newYorkTimesClient.requestQueriedArticles(currentQuery, DEFAULT_PAGE, SearchActivity.this);
                }
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
                FilterFragmentDialog filterFragmentDialog = new FilterFragmentDialog();
                filterFragmentDialog.setListener(this);
                filterFragmentDialog.show(getSupportFragmentManager(), "filer_fragment");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFiltered(String date, String sort, String newsDesk) {
        if (!infiniteScrollListener.isLoading()) {
            this.date = date;
            this.sort = sort;
            this.newsDesk = newsDesk;

            articles.clear();
            searchType = SearchType.FILTERED;
            infiniteScrollListener.loadStarted();
            newYorkTimesClient.requestFilteredArticles(date, sort, newsDesk, DEFAULT_PAGE, this);
        }
    }
}