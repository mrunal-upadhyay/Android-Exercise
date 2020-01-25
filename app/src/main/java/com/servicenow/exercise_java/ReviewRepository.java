package com.servicenow.exercise_java;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class provides Singleton network request for hitting API and using LiveData for
 * observing API response. LiveData is an observable data holder class. Unlike regular observable,
 * LiveData respects the lifecycle of other app components such as activities and fragments.
 */
public class ReviewRepository {

    private static ReviewRepository reviewRepository;
    private ReviewApi reviewApi;

    public ReviewRepository() {
        reviewApi = RetrofitService.createService(ReviewApi.class);
    }

    public static ReviewRepository getInstance() {
        if (reviewRepository == null) {
            reviewRepository = new ReviewRepository();
        }
        return reviewRepository;
    }

    public MutableLiveData<ArrayList<ReviewModel>> getReviews() {
        MutableLiveData<ArrayList<ReviewModel>> reviewsLiveData = new MutableLiveData<>();
        reviewApi.fetchReviews().enqueue(new Callback<ArrayList<ReviewModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ReviewModel>> call, Response<ArrayList<ReviewModel>> response) {
                if (response.isSuccessful()) {
                    Log.d("ReviewRepository", "Retrofit response: " + response.body().toString());
                    reviewsLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ReviewModel>> call, Throwable t) {
                //Make sure to show data to the user from local if remote call does not work
                reviewsLiveData.setValue(CoffeeShopReviewsData.getData());
            }
        });
        return reviewsLiveData;
    }

}
