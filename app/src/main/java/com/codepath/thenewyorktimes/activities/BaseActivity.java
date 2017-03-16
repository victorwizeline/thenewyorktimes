package com.codepath.thenewyorktimes.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codepath.thenewyorktimes.controllers.RetrofitClient;
import com.codepath.thenewyorktimes.interfaces.NewYorkTimesClient;

public class BaseActivity extends AppCompatActivity {

    private NewYorkTimesClient newYorkTimesClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newYorkTimesClient = new RetrofitClient();
    }

    public NewYorkTimesClient getNewYorkTimesClient() {
        return newYorkTimesClient;
    }
}
