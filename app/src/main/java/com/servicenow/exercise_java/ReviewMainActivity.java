package com.servicenow.exercise_java;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.servicenow.exercise.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewMainActivity extends AppCompatActivity implements ReviewAdapter.ReviewAdapterOnClickHandler {
    private final String TAG = this.getClass().getSimpleName();
    private ArrayList<ReviewModel> coffeeShopReviewsList;
    private ReviewAdapter adapter;
    private RecyclerView recyclerView;
    private Retrofit retrofit;
    private ReviewMainViewModel reviewMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reviewMainViewModel = new ViewModelProvider(this).get(ReviewMainViewModel.class);
        reviewMainViewModel.init();
        setupRecyclerView();

        final Observer<ArrayList<ReviewModel>> reviewObserver = new Observer<ArrayList<ReviewModel>>() {
            @Override
            public void onChanged(ArrayList<ReviewModel> reviewModels) {
                if (reviewModels != null) {
                    adapter.setReviewData(reviewModels);
                }
            }
        };
        reviewMainViewModel.getReviewsLiveData().observe(this, reviewObserver);
    }

    private void setupRecyclerView() {
        if (adapter == null) {
            adapter = new ReviewAdapter(coffeeShopReviewsList, this);
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
        }
    }

    //Launch Detail Activity when user clicks on a review from Main Activity
    @Override
    public void onClick(ReviewModel review) {
        Context context = this;
        Class destinationClass = ReviewDetailActivity.class;
        Intent intent = new Intent(context, destinationClass);
        intent.putExtra(Constants.REVIEW_EXTRA, review);
        startActivity(intent);
    }
}
