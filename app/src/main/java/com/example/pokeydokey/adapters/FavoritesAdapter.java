package com.example.pokeydokey.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokeydokey.DetailsActivity;
import com.example.pokeydokey.R;
import com.example.pokeydokey.room.FavoritePokemon;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    public interface OnFavoriteActionListener {
        void onRemove(FavoritePokemon fav);
    }

    private List<FavoritePokemon> list;
    private Context context;
    private OnFavoriteActionListener listener;

    public FavoritesAdapter(Context context, List<FavoritePokemon> list, OnFavoriteActionListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public void updateList(List<FavoritePokemon> l) {
        this.list = l;
        notifyDataSetChanged();
    }

    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.ViewHolder holder, int position) {
        FavoritePokemon item = list.get(position);
        holder.name.setText(capitalize(item.getName()));
        Glide.with(context).load(item.getImageUrl()).into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("name", item.getName());
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onRemove(item);
        });
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        ImageButton btnDelete;
        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.pokemon_image);
            name = itemView.findViewById(R.id.pokemon_name);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) return s;
        return s.substring(0,1).toUpperCase() + s.substring(1);
    }
}
