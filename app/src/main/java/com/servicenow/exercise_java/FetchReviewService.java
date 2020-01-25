package com.servicenow.exercise_java;

import com.servicenow.coffee.Review;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FetchReviewService {

    @GET("api/jsonBlob/c1a89a37-371e-11ea-a549-6f3544633231")
    Call<ArrayList<ReviewModel>> fetchReviews();
}
