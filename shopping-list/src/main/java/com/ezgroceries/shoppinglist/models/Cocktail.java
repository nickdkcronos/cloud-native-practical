package com.ezgroceries.shoppinglist.models;

import java.util.UUID;

public class Cocktail {
    private UUID id;
    private String cocktailId;

    public Cocktail(UUID id, String cocktailId) {
        this.id = id ;
        this.cocktailId = cocktailId;
    }

    public String getCocktailId() {
        return cocktailId;
    }
}
