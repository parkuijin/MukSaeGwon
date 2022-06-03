package com.cookandroid.muksaegwon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.muksaegwon.R;
import com.cookandroid.muksaegwon.model.Favorite;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>{
    ArrayList<Favorite> data;

    public FavoriteAdapter(ArrayList<Favorite> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTvFavoriteStore().setText(data.get(position).getStoreName());
        holder.getTvFavoriteStoreLocation().setText(data.get(position).getStoreLocation());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvFavoriteStore;
        private TextView tvFavoriteStoreLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFavoriteStore = (TextView) itemView.findViewById(R.id.tvFavoriteStore);
            tvFavoriteStoreLocation = (TextView) itemView.findViewById(R.id.tvFavoriteStoreLocation);
        }

        public TextView getTvFavoriteStore() {
            return tvFavoriteStore;
        }

        public TextView getTvFavoriteStoreLocation() {
            return tvFavoriteStoreLocation;
        }

    }
}
