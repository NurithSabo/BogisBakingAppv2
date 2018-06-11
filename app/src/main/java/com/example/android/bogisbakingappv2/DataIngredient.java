package com.example.android.bogisbakingappv2;

import java.io.Serializable;

/**
 * Created by Bogi on 2018. 05. 05..
 */

public class DataIngredient implements Serializable {
    public double quantity;
    public String measure;
    public String ingredientName;

    public DataIngredient(double quantity, String measure, String ingredientName) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredientName = ingredientName;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredientName() {
        return ingredientName;
    }
}
