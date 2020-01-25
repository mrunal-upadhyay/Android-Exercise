package com.servicenow.exercise_java;


import android.os.Parcel;
import android.os.Parcelable;

import com.servicenow.exercise.R;

/**
 * Model class of the API response
 */
public class ReviewModel implements Parcelable {

    public static final Creator<ReviewModel> CREATOR = new Creator<ReviewModel>() {
        @Override
        public ReviewModel createFromParcel(Parcel in) {
            return new ReviewModel(in);
        }

        @Override
        public ReviewModel[] newArray(int size) {
            return new ReviewModel[size];
        }
    };
    private final String name;
    private final String review;
    private final int rating;
    private final String location;
    public ReviewModel(String name, String review, int rating, String location) {
        this.name = name;
        this.review = review;
        this.rating = rating;
        this.location = location;
    }

    public ReviewModel(Parcel in) {
        name = in.readString();
        review = in.readString();
        rating = in.readInt();
        location = in.readString();
    }

    public static int getIconResourceFromName(String name) {
        switch (name) {
            case "Lofty":
                return R.drawable.bean_bag;
            case "Zumbar":
                return R.drawable.coffee_cup;
            case "Blue Bottle":
                return R.drawable.coffee_grinder;
            case "Bird Rock":
                return R.drawable.coffee_maker;
            case "Better Buzz Coffee":
                return R.drawable.coffee_shop;
        }
        return -1;
    }

    public String getName() {
        return name;
    }

    public String getReview() {
        return review;
    }

    public int getRating() {
        return rating;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(review);
        parcel.writeInt(rating);
        parcel.writeString(location);
    }

}