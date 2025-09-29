package com.example.pokeydokey.models;

public class PokemonResult {
    private String name;
    private String url;

    public String getName() { return name; }
    public String getUrl() { return url; }

    // Helper: parse id from url like .../pokemon/25/
    public int getIdFromUrl() {
        if (url == null) return -1;
        String[] parts = url.split("/");
        for (int i = parts.length - 1; i >= 0; i--) {
            if (!parts[i].isEmpty()) {
                try {
                    return Integer.parseInt(parts[i]);
                } catch (NumberFormatException e) {}
            }
        }
        return -1;
    }
}