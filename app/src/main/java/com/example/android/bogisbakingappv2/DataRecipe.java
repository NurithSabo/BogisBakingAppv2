package com.example.android.bogisbakingappv2;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bogi on 2018. 05. 05..
 */

public class DataRecipe implements Serializable{
    private String id;
    private String name;
    private ArrayList<DataIngredient> ingredients;
    private ArrayList<DataStep> steps;
    private int servings;
    private String imageUrl;

    public DataRecipe(String id, String name, ArrayList<DataIngredient> ingredients,
                      ArrayList<DataStep> steps, int servings, String imageUrl) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<DataIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<DataIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<DataStep> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<DataStep> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
