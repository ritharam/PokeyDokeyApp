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
import com.example.pokeydokey.models.PokemonResult;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {
    private List<PokemonResult> list;
    private Context context;

    public PokemonAdapter(Context context, List<PokemonResult> list) {
        this.context = context;
        this.list = list;
    }

    public void updateList(List<PokemonResult> l) {
        this.list = l;
        notifyDataSetChanged();
    }

    @Override
    public PokemonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PokemonAdapter.ViewHolder holder, int position) {
        PokemonResult item = list.get(position);
        holder.name.setText(capitalize(item.getName()));
        int id = item.getIdFromUrl();
        String imgUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id + ".png";
        Glide.with(context).load(imgUrl).into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("name", item.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return list==null?0:list.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.pokemon_image);
            name = itemView.findViewById(R.id.pokemon_name);
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) return s;
        return s.substring(0,1).toUpperCase() + s.substring(1);
    }
}
