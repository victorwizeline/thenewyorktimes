package com.codepath.thenewyorktimes.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codepath.thenewyorktimes.controllers.ApiManager;
import com.codepath.thenewyorktimes.controllers.IApiManager;

public class BaseActivity extends AppCompatActivity {

    private IApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiManager = new ApiManager();
    }

    public IApiManager getApiManager() {
        return apiManager;
    }
}
