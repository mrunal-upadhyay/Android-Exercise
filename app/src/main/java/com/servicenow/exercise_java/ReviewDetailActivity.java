package com.servicenow.exercise_java;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.servicenow.exercise.R;

//Detail Activity which is launched when the user clicks on a review.
//REVIEW_DETAILS_BUNDLE_KEY contains the review that user has clicked on
public class ReviewDetailActivity extends AppCompatActivity {

    private TextView reviewTv;
    private TextView ratingTv;
    private TextView locationTv;
    private ReviewModel review;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);
        reviewTv = findViewById(R.id.textreview);
        ratingTv = findViewById(R.id.textrating);
        locationTv = findViewById(R.id.textlocation);

        // check if the activity is newly created or recreated after a activity restart that can be because of a phone call or device rotation
        if (savedInstanceState == null || !savedInstanceState
                .containsKey(Constants.REVIEW_DETAILS_BUNDLE_KEY)) {
            Intent intent = getIntent();
            if (intent.hasExtra(Constants.REVIEW_EXTRA)) {
                review = intent.getParcelableExtra(Constants.REVIEW_EXTRA);
                populateUi();
            }
        } else {
            review = savedInstanceState.getParcelable(Constants.REVIEW_DETAILS_BUNDLE_KEY);
            populateUi();
        }

    }

    //save the review if the activity is going to be paused and eventually stopped.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.REVIEW_DETAILS_BUNDLE_KEY, review);
        super.onSaveInstanceState(outState);
    }

    //Update the UI textviews with the data from review object.
    private void populateUi() {
        this.reviewTv.setText(review.getReview());
        this.ratingTv.setText("" + review.getRating());
        this.locationTv.setText(review.getLocation());
    }

}
