package com.servicenow.exercise_java;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.servicenow.coffee.CoffeeShopReviews;
import com.servicenow.coffee.Review;
import com.servicenow.exercise.R;

import java.util.Arrays;
import java.util.List;

public class ReviewMainActivity extends Activity {

    public static final Review[] coffeeShopReviews = CoffeeShopReviews.INSTANCE.getList();
    private List<Review> coffeeShopReviewsList = Arrays.asList(coffeeShopReviews);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ReviewAdapter adapter = new ReviewAdapter(coffeeShopReviewsList);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }
}
