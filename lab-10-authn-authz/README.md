# (Optional) Lab 10 - Authentication and authorization

An optional lab for those with time to spare! This only includes basic requirements and a suggested way of working.

We want to add extra functionality to our shopping list, they should be user specific. This has quite an impact on our application:

Basic requirements:

* Use Spring Security (add appropriate dependency)
* Only authenticated users are allowed to de shopping list api calls
* Non authenticated users are still allowed to search cocktails
* Shopping lists need to contain a user identification attribute
* The only shopping lists a user can see are the ones he created

## Authentication

Suggested way of working:

* Provide users through it's [JDBC authentication support](https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#jc-authentication-jdbc)
* Make sure to create the JDBC user's schema through Flyway scripts
* Users need to be pre-populated on application start-up somehow, no sign-up functionality needed
* Provide config to authenticate certain paths (see Basic requirements)

## Authorization

* Authorize shopping list API calls
* Check the userId of the authenticated user and the userId of the specific shopping list (tips: SpeL and @PreAuthorize)

## Commit and tag your work

Make sure to add, commit and push all your files at least once at the end of every lab. After the lab has been completed completely please tag it with the appropriate lab number:

````
git tag -a lab10 -m "lab10"
````