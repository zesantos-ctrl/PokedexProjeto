package br.com.pokedexAPI.Pokedex;

import java.awt.*;

public class features {
     private int id;
     private String name;
     private String image;
     private List<String> types;     // tipos do pokemon
     private int height;             // altura
     private int weight;             // peso
     private List<String> abilities; // habilidades

    //Construtor basico

    public features(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    //Construtor completo

    public features(int id, String name, String image, List<String> types, int height, int weight, List<String> abilities) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.types = types;
        this.height = height;
        this.weight = weight;
        this.abilities = abilities;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<String> abilities) {
        this.abilities = abilities;
    }




}
