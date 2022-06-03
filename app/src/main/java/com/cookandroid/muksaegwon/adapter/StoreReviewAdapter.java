package com.cookandroid.muksaegwon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.muksaegwon.R;
import com.cookandroid.muksaegwon.model.StoreReview;

import java.util.ArrayList;

public class StoreReviewAdapter extends RecyclerView.Adapter<StoreReviewAdapter.ViewHolder>{
    ArrayList<StoreReview> data;

    public StoreReviewAdapter(ArrayList<StoreReview> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_review_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTvUserBy().setText(data.get(position).getUserBy());
        holder.getTvReview().setText(data.get(position).getReview());
        holder.getRating().setRating(data.get(position).getRating());
        holder.getTvDate().setText(data.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUserBy;
        private TextView tvReview;
        private RatingBar Rating;
        private TextView tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserBy = (TextView) itemView.findViewById(R.id.userBy);
            tvReview = (TextView) itemView.findViewById(R.id.tvReviewStore);
            Rating = (RatingBar) itemView.findViewById(R.id.RatingStore);
            tvDate = (TextView) itemView.findViewById(R.id.tvDateStore);
        }

        public TextView getTvUserBy() {
            return tvUserBy;
        }

        public TextView getTvReview() {
            return tvReview;
        }

        public TextView getTvDate() {
            return tvDate;
        }

        public RatingBar getRating() {
            return Rating;
        }
    }
}
