package com.servicenow.exercise_java;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
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
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ReviewAdapter(coffeeShopReviewsList, this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        //If the activity starts the very first time, make network call and fetch the data
        if (savedInstanceState == null || !savedInstanceState.containsKey(Constants.REVIEW_BUNDLE_KEY)) {
            loadReviewData();
        //If the activity restarts and if we have saved state then use the state and dont make a network call
        } else {
            coffeeShopReviewsList = savedInstanceState.getParcelableArrayList(Constants.REVIEW_BUNDLE_KEY);
            adapter.setReviewData(coffeeShopReviewsList);
        }
    }

    private void loadReviewData() {
//        Load data from local
//        coffeeShopReviewsList = CoffeeShopReviewsData.getData();
//        adapter.setReviewData(coffeeShopReviewsList);

        //Load data from remote
        Log.d(TAG, "Inside loadReviewData()");
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        FetchReviewService fetchReviewService = retrofit.create(FetchReviewService.class);

        Call<ArrayList<ReviewModel>> call = fetchReviewService.fetchReviews();
        call.enqueue(new Callback<ArrayList<ReviewModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ReviewModel>> call, Response<ArrayList<ReviewModel>> response) {
                Log.d(TAG, "Retrofit response: " + response.body().toString());
                coffeeShopReviewsList = response.body();
                adapter.setReviewData(coffeeShopReviewsList);
            }

            @Override
            public void onFailure(Call<ArrayList<ReviewModel>> call, Throwable t) {
                Log.e(TAG, t.toString());
                //Make sure to show data to the user from local if remote call does not work
                coffeeShopReviewsList = CoffeeShopReviewsData.getData();
                adapter.setReviewData(coffeeShopReviewsList);
            }
        });
    }

    //Save the state if the activity gets paused(device rotation, user getting a phone call, user opens another app)
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Constants.REVIEW_BUNDLE_KEY, coffeeShopReviewsList);
        super.onSaveInstanceState(outState);
    }

    //Launch Detail Activity when user clicks on a review from Main Activity
    @Override
    public void onClick(ReviewModel review) {
        Context context = this;
        Class destinationClass = ReviewDetailActivity.class;
        Intent intent = new Intent(context, destinationClass);
        intent.putExtra(Constants.REVIEW_EXTRA,review);
        startActivity(intent);
    }
}
