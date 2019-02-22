package com.ezgroceries.shoppinglist.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.UUID;

public class ShoppingList {
    private UUID shoppingListId;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> ingredients;

    public ShoppingList(UUID shoppingListId, String name, List<String> ingredients) {
        this.shoppingListId = shoppingListId;
        this.name = name;
        this.ingredients = ingredients;
    }

    public void setShoppingListId(UUID shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public String getName() {
        return name;
    }

    public UUID getShoppingListId() {
        return shoppingListId;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}
