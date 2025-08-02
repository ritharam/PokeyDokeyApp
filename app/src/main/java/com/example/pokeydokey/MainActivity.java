package com.example.pokeydokey;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText searchInput;
    Button searchButton, favoritesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // ← this needs the layout we just created

        searchInput = findViewById(R.id.searchInput);
        searchButton = findViewById(R.id.searchButton);
        favoritesButton = findViewById(R.id.favoritesButton);

        searchButton.setOnClickListener(v -> {
            String query = searchInput.getText().toString().trim().toLowerCase();
            if (!query.isEmpty()) {
                // We'll implement this later
                Toast.makeText(this, "Search clicked for: " + query, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter a Pokémon name", Toast.LENGTH_SHORT).show();
            }
        });

        favoritesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });


        searchButton.setOnClickListener(v -> {
            String query = searchInput.getText().toString().trim().toLowerCase();
            if (!query.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("pokemon_name", query);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please enter a Pokémon name", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
