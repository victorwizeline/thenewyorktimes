package com.codepath.thenewyorktimes.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codepath.thenewyorktimes.controllers.ApiManager;

public class BaseActivity extends AppCompatActivity {

    private ApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiManager = new ApiManager();
    }

    public ApiManager getApiManager() {
        return apiManager;
    }
}
