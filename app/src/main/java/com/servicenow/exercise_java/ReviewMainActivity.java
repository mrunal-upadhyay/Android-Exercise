package com.servicenow.exercise_java;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.servicenow.exercise.R;

import java.util.ArrayList;

public class ReviewMainActivity extends AppCompatActivity implements ReviewAdapter.ReviewAdapterOnClickHandler {
    private ArrayList<ReviewModel> coffeeShopReviewsList;
    private ReviewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ReviewAdapter(coffeeShopReviewsList, this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        if (savedInstanceState == null || !savedInstanceState.containsKey(Constants.REVIEW_BUNDLE_KEY)) {
            loadReviewData();
        } else {
            coffeeShopReviewsList = savedInstanceState.getParcelableArrayList(Constants.REVIEW_BUNDLE_KEY);
            adapter.setReviewData(coffeeShopReviewsList);
        }
    }

    private void loadReviewData() {
        coffeeShopReviewsList = CoffeeShopReviewsData.getData();
        adapter.setReviewData(coffeeShopReviewsList);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Constants.REVIEW_BUNDLE_KEY, coffeeShopReviewsList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(ReviewModel review) {
        Context context = this;
        Class destinationClass = ReviewDetailActivity.class;
        Intent intent = new Intent(context, destinationClass);
        intent.putExtra(Constants.REVIEW_EXTRA,review);
        startActivity(intent);
    }
}
