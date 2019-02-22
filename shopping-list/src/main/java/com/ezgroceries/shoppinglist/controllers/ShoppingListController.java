package com.ezgroceries.shoppinglist.controllers;

import com.ezgroceries.shoppinglist.models.CocktailResource;
import com.ezgroceries.shoppinglist.models.ShoppingListResource;
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
    public ShoppingListResource createShoppingList(@RequestBody ShoppingListResource shoppingListResource) {
        shoppingListResource.setShoppingListId(UUID.fromString("eb18bb7c-61f3-4c9f-981c-55b1b8ee8915"));
        return shoppingListResource;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ShoppingListResource> getAllShoppingLists() {
        return Arrays.asList(
                new ShoppingListResource(
                        UUID.fromString("4ba92a46-1d1b-4e52-8e38-13cd56c7224c"),
                        "Stephanie's Birthday",
                        Arrays.asList("Tequila", "Triple Sec", "Lime Juice", "Salt", "Blue Curacao")
                ),
                new ShoppingListResource(
                        UUID.fromString("6c7d09c2-8a25-4d54-a979-25ae779d2465"),
                        "My Birthday",
                        Arrays.asList("Tequila", "Triple Sec", "Lime Juice", "Salt", "Blue Curacao")
                )
        );
    }

    @GetMapping(value = "/{listId}")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingListResource getShoppingListById(@PathVariable String listId) {
        return new ShoppingListResource(
                 UUID.fromString("90689338-499a-4c49-af90-f1e73068ad4f"),
                "Stephanie's Birthday",
                 Arrays.asList("Tequila", "Triple Sec", "Lime Juice", "Salt", "Blue Curacao")
        );
    }

    @PostMapping(value = "/{listId}/cocktails")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CocktailResource> createSimpleCocktail(@PathVariable String listId, @RequestBody List<CocktailResource> cocktails) {
        return cocktails;
    }
}
