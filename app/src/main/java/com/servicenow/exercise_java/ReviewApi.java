package com.servicenow.exercise_java;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ReviewApi {

    @GET(Constants.PATH)
    Call<ArrayList<ReviewModel>> fetchReviews();
}
