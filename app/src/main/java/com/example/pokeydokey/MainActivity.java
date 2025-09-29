package com.example.pokeydokey;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokeydokey.adapters.PokemonAdapter;
import com.example.pokeydokey.models.PokemonResult;
import com.example.pokeydokey.ui.PokemonViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private PokemonViewModel viewModel;
    private RecyclerView recyclerView;
    private PokemonAdapter adapter;
    private FloatingActionButton fabSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Pokémon Finder");

        recyclerView = findViewById(R.id.recycler);
        fabSearch = findViewById(R.id.fab_search);

        adapter = new PokemonAdapter(this, new ArrayList<>());
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        viewModel.getPokemonList().observe(this, results -> {
            adapter.updateList(results);
        });

        viewModel.getError().observe(this, s -> {
            if (s != null) Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        });

        // load first 151 Pokemon
        viewModel.fetchPokemonList(151, 0);

        fabSearch.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SearchActivity.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_favorites) {
            startActivity(new Intent(this, FavoritesActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}