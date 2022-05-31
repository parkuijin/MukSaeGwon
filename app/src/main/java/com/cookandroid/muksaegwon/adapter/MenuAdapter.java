package com.cookandroid.muksaegwon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.muksaegwon.R;
import com.cookandroid.muksaegwon.model.Menu;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    ArrayList<Menu> data;

    public MenuAdapter(ArrayList<Menu> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_tv, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getMenuNameTv().setText(data.get(position).getMenuName());
        holder.getMenuPriceTv().setText(data.get(position).getMenuPrice());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView MenuNameTv;
        private TextView MenuPriceTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            MenuNameTv = (TextView) itemView.findViewById(R.id.MenuNameTv);
            MenuPriceTv = (TextView) itemView.findViewById(R.id.MenuPriceTv);
        }

        public TextView getMenuNameTv() {
            return MenuNameTv;
        }

        public TextView getMenuPriceTv() {
            return MenuPriceTv;
        }
    }

}
