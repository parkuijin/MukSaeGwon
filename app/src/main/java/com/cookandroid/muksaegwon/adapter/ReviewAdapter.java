package com.cookandroid.muksaegwon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.muksaegwon.R;
import com.cookandroid.muksaegwon.model.Review;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    ArrayList<Review> data;

    public ReviewAdapter(ArrayList<Review> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTvReview().setText(data.get(position).getReview());
        holder.getTvStoreName().setText(data.get(position).getStoreName());
        holder.getRating().setRating((float)data.get(position).getRating());
        holder.getTvDate().setText(data.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvReview;
        private TextView tvStoreName;
        private RatingBar Rating;
        private TextView tvDate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReview = (TextView) itemView.findViewById(R.id.tvReview);
            tvStoreName = (TextView) itemView.findViewById(R.id.tvStoreName);
            Rating = (RatingBar) itemView.findViewById(R.id.tvRating);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
        }

        public TextView getTvReview() {
            return tvReview;
        }

        public TextView getTvStoreName() {
            return tvStoreName;
        }

        public RatingBar getRating() { return Rating; }

        public TextView getTvDate() {
            return tvDate;
        }
    }
}
