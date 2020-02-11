package com.servicenow.exercise_java;

import android.util.Log;

import androidx.lifecycle.LiveData;
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

    private final String TAG = this.getClass().getSimpleName();

    /* Repository SingleTon Instance */
    private static volatile ReviewRepository reviewRepository;
    private ReviewApi reviewApi;

    private MutableLiveData<NetworkState> networkState;
    private MutableLiveData<ArrayList<ReviewModel>> reviewsLiveData;

    private ReviewRepository() {
        networkState = new MutableLiveData<>();
        reviewsLiveData = new MutableLiveData<>();

        reviewApi = RetrofitService.createService(ReviewApi.class);
    }

    public static ReviewRepository getInstance() {
        if (reviewRepository == null) {

            synchronized (ReviewRepository.class) {
                reviewRepository = new ReviewRepository();
            }
        }
        return reviewRepository;
    }

    public LiveData<ArrayList<ReviewModel>> getReviews() {
        networkState.postValue(NetworkState.LOADING);
        reviewApi.fetchReviews().enqueue(new Callback<ArrayList<ReviewModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ReviewModel>> call, Response<ArrayList<ReviewModel>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Retrofit onResponse: " + response.body().toString());
                    reviewsLiveData.setValue(response.body());
                } else {
                    Log.d(TAG, "Retrofit onResponse: " + response.message());
                    networkState.setValue(NetworkState.FAILED);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ReviewModel>> call, Throwable t) {
                String errorMessage;
                if(t.getMessage() == null) {
                    errorMessage = "unknown error";
                } else {
                    errorMessage = t.getMessage();
                }
                Log.d(TAG, "Retrofit onFailure: " + errorMessage);
            }
        });
        return reviewsLiveData;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
