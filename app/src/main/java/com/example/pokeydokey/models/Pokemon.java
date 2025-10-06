package com.example.pokeydokey.models;

import java.util.List;

public class Pokemon {
    private int id;
    private String name;
    private int height;
    private int weight;
    private Sprites sprites;
    private List<TypeSlot> types;
    private List<StatSlot> stats;

    public int getId() { return id; }
    public String getName() { return name; }
    public int getHeight() { return height; }
    public int getWeight() { return weight; }
    public Sprites getSprites() { return sprites; }
    public List<TypeSlot> getTypes() { return types; }
    public List<StatSlot> getStats() { return stats; }

    public static class Sprites { public String front_default; }
    public static class TypeSlot { public int slot; public Type type; }
    public static class Type { public String name; public String url; }
    public static class StatSlot { public int base_stat; public int effort; public Stat stat; }
    public static class Stat { public String name; public String url; }
}
