package com.example.pokeydokey;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokeydokey.adapters.FavoritesAdapter;
import com.example.pokeydokey.room.FavoritePokemon;
import com.example.pokeydokey.ui.PokemonViewModel;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {
    private PokemonViewModel viewModel;
    private RecyclerView recycler;
    private FavoritesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        if (getSupportActionBar()!=null) getSupportActionBar().setTitle("Favorites");

        recycler = findViewById(R.id.recycler_fav);
        adapter = new FavoritesAdapter(this, new ArrayList<>(), fav -> {
            // remove action
            viewModel.removeFavorite(fav);
        });
        recycler.setLayoutManager(new GridLayoutManager(this, 3));
        recycler.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        viewModel.getAllFavorites().observe(this, favorites -> adapter.updateList(favorites));
    }
}
