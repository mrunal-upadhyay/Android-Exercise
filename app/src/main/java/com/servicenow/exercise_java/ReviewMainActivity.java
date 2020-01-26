package com.servicenow.exercise_java;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.servicenow.exercise.R;

import java.util.ArrayList;

public class ReviewMainActivity extends AppCompatActivity implements ReviewAdapter.ReviewAdapterOnClickHandler {
    private final String TAG = this.getClass().getSimpleName();
    private ArrayList<ReviewModel> coffeeShopReviewsList;
    private ReviewAdapter adapter;
    private RecyclerView recyclerView;
    private ReviewMainViewModel reviewMainViewModel;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        reviewMainViewModel = new ViewModelProvider(this).get(ReviewMainViewModel.class);
        reviewMainViewModel.init();
        setupRecyclerView();

        // Observer that will respond when there is change in the reviewModels ArrayList which is the data returned by the rest call.
        final Observer<ArrayList<ReviewModel>> reviewObserver = new Observer<ArrayList<ReviewModel>>() {
            @Override
            public void onChanged(ArrayList<ReviewModel> reviewModels) {
                if (reviewModels != null) {
                    progressBar.setVisibility(View.INVISIBLE);
                    adapter.setReviewData(reviewModels);
                }
            }
        };

        //register a observer to observe a change in the ReviewModel livedata.
        reviewMainViewModel.getReviewsLiveData().observe(this, reviewObserver);
    }

    //set up the recyclerview
    //1.  Get the recylerview reference from the layout
    //2.  Set the LayoutManager
    //3.  Set hasFixedSize for performance improvement
    //4.  Set the adapter to the recylerview
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
    // Create an intent and pass the review that user has clicked on as an extra to the intent.
    @Override
    public void onClick(ReviewModel review) {
        Context context = this;
        Class destinationClass = ReviewDetailActivity.class;
        Intent intent = new Intent(context, destinationClass);
        intent.putExtra(Constants.REVIEW_EXTRA, review);
        startActivity(intent);
    }
}
