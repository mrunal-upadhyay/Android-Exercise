package com.servicenow.exercise_java;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.servicenow.coffee.Review;
import com.servicenow.exercise.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private List<Review> reviews;

    //Initialize adapter
    public ReviewAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the view and return the new ViewHolder
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        //Use position to access the correct Review object
        Review review = this.reviews.get(position);
        //Bind the Review object to the holder
        holder.bindData(review);
    }

    @Override
    public int getItemCount() {
        return this.reviews.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name;
        private TextView review;
        private ImageView reviewImage;

        public ReviewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.text1);
            this.review = itemView.findViewById(R.id.text2);
            this.reviewImage = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("ReviewAdapter", "Clicked on" + this.name + ":" + this.review);
        }

        public void bindData(Review review) {
            this.name.setText(review.getName());
            this.review.setText(review.getReview());
            this.reviewImage.setImageResource(Review.getIconResourceFromName(review.getName()));
        }
    }
}
