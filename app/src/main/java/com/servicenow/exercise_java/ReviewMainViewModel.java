package com.servicenow.exercise_java;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

/**
 * ViewModel class is designed to store and manage UI related data in a lifecycle conscious way.
 * It allows to data to survive configuration change such as screen rotations.
 */
public class ReviewMainViewModel extends ViewModel {

    private MutableLiveData<ArrayList<ReviewModel>> reviewsLiveData;
    private ReviewRepository reviewRepository;

    public void init() {
        if (reviewsLiveData != null) {
            return;
        }
        reviewRepository = ReviewRepository.getInstance();
        reviewsLiveData = reviewRepository.getReviews();
    }

    public MutableLiveData<ArrayList<ReviewModel>> getReviewsLiveData() {
        return reviewsLiveData;
    }
}

