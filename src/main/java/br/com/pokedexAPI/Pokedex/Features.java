package br.com.pokedexAPI.Pokedex;

import java.util.List;

public class Features {
    private int id;
    private String name;
    private String image;
    private int height;
    private int weight;
    private List<String> types;
    private List<String> abilities; // cada item: "abilityName - short effect"
    private String description;
    private String color;

    public Features() {
    }

    public Features(int id, String name, String image, int height, int weight,
                    List<String> types, List<String> abilities, String description, String color) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.height = height;
        this.weight = weight;
        this.types = types;
        this.abilities = abilities;
        this.description = description;
        this.color = color;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public List<String> getTypes() {
        return types;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public String getDescription() {
        return description;
    }

    public String getColor() {
        return color;
    }

}
