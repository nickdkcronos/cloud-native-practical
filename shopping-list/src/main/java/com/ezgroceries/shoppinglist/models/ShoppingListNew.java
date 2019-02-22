package com.ezgroceries.shoppinglist.models;

import java.util.UUID;

public class ShoppingListNew {
    private String shoppingListId;
    private String name;

    public ShoppingListNew(String shoppingListId, String name) {
        this.shoppingListId = shoppingListId;
        this.name = name;
    }

//    public void setShoppingListId(UUID shoppingListId) {
//        this.shoppingListId = shoppingListId;
//    }

    public String getName() {
        return name;
    }

}
