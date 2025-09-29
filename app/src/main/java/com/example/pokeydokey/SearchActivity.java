package com.example.pokeydokey;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.pokeydokey.models.Pokemon;
import com.example.pokeydokey.ui.PokemonViewModel;

import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import android.widget.ImageView;

public class SearchActivity extends AppCompatActivity {
    private EditText input;
    private Button btnSearch;
    private PokemonViewModel viewModel;
    private ProgressBar progress;
    private ImageView image;
    private TextView txtName, txtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setTitle("Search Pokémon");

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
                txtName.setText(capitalize(pokemon.getName()) + " (#" + pokemon.getId() + ")");
                txtInfo.setText("Height: " + pokemon.getHeight() + "  Weight: " + pokemon.getWeight());
                String url = (pokemon.getSprites() != null) ? pokemon.getSprites().front_default : null;
                Glide.with(this).load(url).into(image);
            }
        });

        viewModel.getError().observe(this, s -> {
            progress.setVisibility(View.GONE);
            if (s != null) Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        });
    }
    private String capitalize(String s) {
        if (s==null||s.length()==0) return s;
        return s.substring(0,1).toUpperCase()+s.substring(1);
    }
}