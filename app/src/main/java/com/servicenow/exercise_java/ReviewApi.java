package com.servicenow.exercise_java;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

// Defining the rest endpoint here which the retrofit service is going to hit
// Single place where all other rest points will be defined.
public interface ReviewApi {

    @GET(Constants.PATH)
    Call<ArrayList<ReviewModel>> fetchReviews();
}
