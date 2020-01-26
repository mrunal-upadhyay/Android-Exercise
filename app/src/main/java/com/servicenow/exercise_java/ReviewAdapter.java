package com.servicenow.exercise_java;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.servicenow.exercise.R;

import java.util.List;

/**
 * Adapter that does the magic of adapting the data to recyclerview. 3 important methods here
 * onCreateViewHolder - creating a new view that is saved in a viewholder
 * onBindViewHolder -  Bind the data from reviews to VH views.
 * getItemCount - returns the count of reviews.
 * ViewHolder is a subclass that performs the actual recyling of the views
 * that are not currently shown to the user making the logic performance efficient as
 * OS does not need to make expensive findByViewId calls
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private final ReviewAdapterOnClickHandler reviewAdapterOnClickHandler;
    private List<ReviewModel> reviews;

    //Initialize adapter
    public ReviewAdapter(List<ReviewModel> reviews, ReviewAdapterOnClickHandler onClickHandler) {
        this.reviews = reviews;
        this.reviewAdapterOnClickHandler = onClickHandler;

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
        ReviewModel review = this.reviews.get(position);
        //Bind the Review object to the holder
        holder.bindData(review);
    }

    @Override
    public int getItemCount() {
        if (reviews != null) {
            return this.reviews.size();
        }
        return 0;
    }

    public void setReviewData(List<ReviewModel> reviews) {
        if (reviews != null) {
            this.reviews = reviews;
            notifyDataSetChanged();
        }
    }

    public interface ReviewAdapterOnClickHandler {
        void onClick(ReviewModel review);
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
            int adapterPosition = getAdapterPosition();
            ReviewModel review = reviews.get(adapterPosition);
            reviewAdapterOnClickHandler.onClick(review);
        }

        public void bindData(ReviewModel review) {
            this.name.setText(review.getName());
            this.review.setText(review.getReview());
            this.reviewImage.setImageResource(ReviewModel.getIconResourceFromName(review.getName()));
        }
    }
}
