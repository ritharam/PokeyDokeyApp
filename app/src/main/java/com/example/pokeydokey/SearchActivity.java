package com.example.pokeydokey;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.pokeydokey.models.Pokemon;
import com.example.pokeydokey.ui.PokemonViewModel;

public class SearchActivity extends AppCompatActivity {
    private EditText input;
    private Button btnSearch;
    private PokemonViewModel viewModel;
    private ProgressBar progress;
    private ImageView image;
    private TextView txtName, txtInfo;
    private Pokemon currentPokemon; // store the current pokemon

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Search Pokémon");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // enable back button
        }

        input = findViewById(R.id.edit_search);
        btnSearch = findViewById(R.id.button_search);
        progress = findViewById(R.id.progress_search);
        image = findViewById(R.id.search_image);
        txtName = findViewById(R.id.search_name);
        txtInfo = findViewById(R.id.search_info);

        viewModel = new ViewModelProvider(this).get(PokemonViewModel.class);

        btnSearch.setOnClickListener(v -> {
            String query = input.getText().toString().trim();
            if (query.isEmpty()) {
                Toast.makeText(this, "Enter name or id", Toast.LENGTH_SHORT).show();
                return;
            }
            progress.setVisibility(View.VISIBLE);
            viewModel.fetchPokemonDetails(query);
        });

        viewModel.getSelectedPokemon().observe(this, pokemon -> {
            progress.setVisibility(View.GONE);
            if (pokemon != null) {
                currentPokemon = pokemon; // save reference
                txtName.setText(capitalize(pokemon.getName()) + " (#" + pokemon.getId() + ")");
                txtInfo.setText("Height: " + pokemon.getHeight() + "  Weight: " + pokemon.getWeight());
                String url = (pokemon.getSprites() != null) ? pokemon.getSprites().front_default : null;
                Glide.with(this).load(url).into(image);

                // Make image clickable to open details
                image.setOnClickListener(v -> openDetailsActivity());
            }
        });

        viewModel.getError().observe(this, s -> {
            progress.setVisibility(View.GONE);
            if (s != null) Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        });
    }

    private void openDetailsActivity() {
        if (currentPokemon == null) return;
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("name", currentPokemon.getName());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // back button pressed
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
