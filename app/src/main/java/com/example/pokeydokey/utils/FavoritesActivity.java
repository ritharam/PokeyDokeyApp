package com.example.pokeydokey;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FavoritesActivity extends AppCompatActivity {

    ListView favoritesListView;
    ArrayList<String> favoriteList;
    ArrayAdapter<String> adapter;

    SharedPreferences sharedPreferences;
    static final String PREFS_NAME = "pokey_dokey_prefs";
    static final String FAVORITES_KEY = "favorite_pokemon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favoritesListView = findViewById(R.id.favoritesListView);
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        loadFavorites();

        favoritesListView.setOnItemClickListener((parent, view, position, id) -> {
            String pokemonName = favoriteList.get(position);
            Intent intent = new Intent(FavoritesActivity.this, DetailsActivity.class);
            intent.putExtra("pokemon_name", pokemonName);
            startActivity(intent);
        });

        favoritesListView.setOnItemLongClickListener((parent, view, position, id) -> {
            String nameToRemove = favoriteList.get(position);
            favoriteList.remove(position);
            saveFavorites();
            adapter.notifyDataSetChanged();
            Toast.makeText(this, nameToRemove + " removed from favorites", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void loadFavorites() {
        Set<String> set = sharedPreferences.getStringSet(FAVORITES_KEY, new HashSet<>());
        favoriteList = new ArrayList<>(set);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoriteList);
        favoritesListView.setAdapter(adapter);
    }

    private void saveFavorites() {
        Set<String> set = new HashSet<>(favoriteList);
        sharedPreferences.edit().putStringSet(FAVORITES_KEY, set).apply();
    }
}
