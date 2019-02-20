# Lab 02 - REST API

In close collaboration with the Mobile teams, our ezGroceries team has designed a REST API that will cover all the functionalities of the initial minimum viable product.

To make sure the Mobile app can start their implementation asap we have decided to implement these APIs using dummy values before we integrate with the third party cocktail and meal APIs.

Other technical decisions that were taken during the design include:

* Only JSON representation will be supported
* Swagger will be provided alongside the API for contract documentation and interactive explorability 
* UUIDs will be used to uniquely identify every resource

**All of the following APIs need to be implemented using Spring REST Controllers, the first one contains an implementation example.**

## Cocktail API

### Search cocktails

Request
```
GET http://localhost:8080/cocktails?search=Russian
```
Response
```
200 OK

[
    {
        "cocktailId": "23b3d85a-3928-41c0-a533-6538a71e17c4",
        "name": "Margerita",
        "glass": "Cocktail glass",
        "instructions": "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..",
        "image": "https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg",
        "ingredients": [
            "Tequila",
            "Triple sec",
            "Lime juice",
            "Salt"
        ]
    },
    {
        "cocktailId": "d615ec78-fe93-467b-8d26-5d26d8eab073",
        "name": "Blue Margerita",
        "glass": "Cocktail glass",
        "instructions": "Rub rim of cocktail glass with lime juice. Dip rim in coarse salt..",
        "image": "https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg",
        "ingredients": [
            "Tequila",
            "Blue Curacao",
            "Lime juice",
            "Salt"
        ]
    }
]
```

Implementation example:
```
@RestController
@RequestMapping(value = "/cocktails", produces = "application/json")
public class CocktailController {

    @GetMapping
    public Resources<CocktailResource> get(@RequestParam String search) {
        return new Resources<>(getDummyResources());
    }

    private List<CocktailResource> getDummyResources() {
        return Arrays.asList(
                new CocktailResource(
                        UUID.fromString("23b3d85a-3928-41c0-a533-6538a71e17c4"), "Margerita",
                "Cocktail glass",
                "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..",
                "https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg",
                        Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt")),
                new CocktailResource(
                        UUID.fromString("d615ec78-fe93-467b-8d26-5d26d8eab073"), "Blue Margerita",
                "Cocktail glass",
                "Rub rim of cocktail glass with lime juice. Dip rim in coarse salt..",
                "https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg",
                        Arrays.asList("Tequila", "Blue Curacao", "Lime juice", "Salt")));
    }
}
```

## Shopping List API

### Create a new Shopping List

Request:
```
POST http://localhost:8080/shopping-lists

{"name": "Stephanie's birthday"}

```
Response:
```
201 CREATED

{
    "shoppingListId": "eb18bb7c-61f3-4c9f-981c-55b1b8ee8915",
    "name": "Stephanie's birthday"
}
```

### Add Cocktails to Shopping List

Request:
```
POST http://localhost:8080/shopping-lists/97c8e5bd-5353-426e-b57b-69eb2260ace3/cocktails

[
  {
    "cocktailId": "23b3d85a-3928-41c0-a533-6538a71e17c4"
  },
  {
    "cocktailId": "d615ec78-fe93-467b-8d26-5d26d8eab073"
  }
]
```

Response, only needs to contain the cocktailId attributes 

```
[
    {
        "cocktailId": "23b3d85a-3928-41c0-a533-6538a71e17c4"
    }
]
```

### Get a Shopping List

This will provide the main (currently still very simple) functionality of our API, 
it will return a distinct set of ingredients derived from all the cocktails that have been added to this shopping list.

Reminder that currently we only return some dummy generated data, just focus on getting the contract right.

Request:
```
GET http://localhost:8080/shopping-lists/eb18bb7c-61f3-4c9f-981c-55b1b8ee8915
```
Response:
```
200 OK

{
    "shoppingListId": "90689338-499a-4c49-af90-f1e73068ad4f",
    "name": "Stephanie's birthday",
    "ingredients": [
        "Tequila",
        "Triple sec",
        "Lime juice",
        "Salt",
        "Blue Curacao"
    ]
}
```

### Get all Shopping Lists

```
GET http://localhost:8080/shopping-lists
```
Response:
```
200 OK

[
    {
        "shoppingListId": "4ba92a46-1d1b-4e52-8e38-13cd56c7224c",
        "name": "Stephanie's birthday",
        "ingredients": [
            "Tequila",
            "Triple sec",
            "Lime juice",
            "Salt",
            "Blue Curacao"
        ]
    },
    {
        "shoppingListId": "6c7d09c2-8a25-4d54-a979-25ae779d2465",
        "name": "My Birthday",
        "ingredients": [
            "Tequila",
            "Triple sec",
            "Lime juice",
            "Salt",
            "Blue Curacao"
        ]
    }
]
```

## Testing

Add Spring MockMVC tests for all the API calls we just implemented. At the minimum assert the following for everyone of them:

* Response status
* Content type
* JSON response body attributes

## Swagger

To document and to be able to interactively test our API we're going to add Swagger documentation. SpringFox is a Spring project dedicated to automated JSON API Documentation, we can use it to automatically include a Swagger file.

To enable Swagger we need to.

- Add following dependency:

```
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>
```

- Enable Spring Swagger support:

```
@EnableSwagger2
public class ShoppingListApplication
```

- Configure Swagger by providing a bean definition:

```
@Bean
public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build();
}
```

Restart your application and verify, a JSON should be returned containing all our configured APIs:

http://localhost:8080/v2/api-docs

This JSON view is mostly for machine-to-machine API documentation. We'll also add the Swagger UI to have a nice web user interface, also add this dependency:

```
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
```

Restart your application and verify:

http://localhost:8080/swagger-ui.html

Explore our API and do some test calls to make you at least somewhat familiar with Swagger.
 

## Commit and tag your work

Make sure to add and commit all your files at least once at the end of every lab. After the lab has been completed completely please tag it with the appropriate lab number:

````
git tag -a lab02 -m "lab02"
````