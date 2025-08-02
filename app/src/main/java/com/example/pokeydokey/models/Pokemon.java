package com.example.pokeydokey.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Pokemon {
    public int id;
    public String name;

    @SerializedName("sprites")
    public Sprites sprites;

    @SerializedName("types")
    public List<TypeSlot> types;

    @SerializedName("abilities")
    public List<AbilitySlot> abilities;

    @SerializedName("stats")
    public List<StatSlot> stats;

    public class Sprites {
        @SerializedName("front_default")
        public String frontDefault;
    }

    public class TypeSlot {
        public Type type;
        public class Type {
            public String name;
        }
    }

    public class AbilitySlot {
        public Ability ability;
        public class Ability {
            public String name;
        }
    }

    public class StatSlot {
        public Stat stat;
        public int base_stat;
        public class Stat {
            public String name;
        }
    }
}
