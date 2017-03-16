package com.codepath.thenewyorktimes.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.thenewyorktimes.R;
import com.codepath.thenewyorktimes.adapters.SearchAdapter;
import com.codepath.thenewyorktimes.databinding.ActivitySearchBinding;
import com.codepath.thenewyorktimes.models.SearchResults;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity implements Callback<SearchResults> {

    private ActivitySearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        getNewYorkTimesClient().requestRecentArticles("0", this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getNewYorkTimesClient().requestSearchArticles(query, "0", SearchActivity.this);
                searchView.clearFocus();
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
    public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
        binding.rvArticles.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        binding.rvArticles.setAdapter(new SearchAdapter(response.body().getResponse().getArticles()));
    }

    @Override
    public void onFailure(Call<SearchResults> call, Throwable t) {
    }
}
