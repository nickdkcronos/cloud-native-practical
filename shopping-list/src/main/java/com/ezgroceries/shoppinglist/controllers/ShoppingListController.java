package com.ezgroceries.shoppinglist.controllers;

import com.ezgroceries.shoppinglist.models.Cocktail;
import com.ezgroceries.shoppinglist.models.ShoppingList;
import com.ezgroceries.shoppinglist.models.ShoppingListNew;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/shopping-lists")
public class ShoppingListController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingList createShoppingList(@RequestBody ShoppingList shoppingList) {
        shoppingList.setShoppingListId(UUID.fromString("eb18bb7c-61f3-4c9f-981c-55b1b8ee8915"));
        return shoppingList;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ShoppingList> getAllShoppingLists() {
        return Arrays.asList(
                new ShoppingList(
                        UUID.fromString("4ba92a46-1d1b-4e52-8e38-13cd56c7224c"),
                        "Stephanie's Birthday",
                        Arrays.asList("Tequila", "Triple Sec", "Lime Juice", "Salt", "Blue Curacao")
                ),
                new ShoppingList(
                        UUID.fromString("6c7d09c2-8a25-4d54-a979-25ae779d2465"),
                        "My Birthday",
                        Arrays.asList("Tequila", "Triple Sec", "Lime Juice", "Salt", "Blue Curacao")
                )
        );
    }

    @GetMapping(value = "/{listId}")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingList getShoppingListById(@PathVariable String listId) {
        return new ShoppingList(
                 UUID.fromString("90689338-499a-4c49-af90-f1e73068ad4f"),
                "Stephanie's Birthday",
                 Arrays.asList("Tequila", "Triple Sec", "Lime Juice", "Salt", "Blue Curacao")
        );
    }

    @PostMapping(value = "/{listId}/cocktails")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Cocktail> createSimpleCocktail(@PathVariable String listId, @RequestBody List<Cocktail> cocktails) {
        return cocktails;
    }
}
