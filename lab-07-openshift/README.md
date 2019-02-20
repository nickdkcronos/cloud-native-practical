# Lab 07 - OpenShift

IMPORTANT Note: this lab only covers the general steps to prepare our application for OpenShift. Actually deploying depends on the target cluster. One option is deploying to a local OpenShift instance called Minishift, check the lab-xx-minishift on how to set-up minishift:

[Lab xx - Minishift lab](../lab-xx-minishift) 

In this lab we're going to prepare our application to be deployed on OpenShift. To do so we're going to rely on these OpenShift concepts:

* Deployment
* Service
* Route

We're going to provide definitions of these objects in one yaml file, add this to our application root, alongside pom.xml:

```
shopping-list-template.yml
```
# Templates

## Deployment

Provide a Deployment definition, here's a simple starting template:

```
kind: Deployment
metadata:
  name: shopping-list-deployment
  annotations:
    version: 0.0.1-SNAPSHOT
spec:
  replicas: 1
  template:
    metadata:
      labels:
        name: shopping-list
        component: shopping-list
    spec:
      containers:
      - name: shopping-list
        image: shopping-list:0.0.1-SNAPSHOT
         ports:
        - containerPort: 8080
        - containerPort: 8443
        lifecycle:
          preStop:
            exec:
              # SIGTERM triggers a quick exit; gracefully terminate instead
              command: ["kill", "-SIGINT", "`pidof java`"]
```



Add appropriate extra configuration for:

* Readiness and Liveness probes, use the appropriate Spring Boot actuator endpoint
* Add sensible resource requests and limits
* Use environment variables to enable our "hsqldb" Spring profile (hint: SPRING_PROFILES_ACTIVE)

## Service

In the same yml, provide an appropriate Service definition of type LoadBalancer that will map port 80 requests to port 8080 and port 443 to 8443.

## Route

In the same yml, provide an appropriate Route definition that will route to the Service above and enables tls passthrough termination.

# Upload a Docker image to the OpenShift registry

Do a Docker build of your application and upload this image to the OpenShift docker registry

# OpenShift apply

Apply our shopping-list-template.yml in the OpenShift environment, verify a few endpoints to asses everything is working as expected.

## Commit and tag your work

Make sure to add, commit and push all your files at least once at the end of every lab. After the lab has been completed completely please tag it with the appropriate lab number:

````
git tag -a lab07 -m "lab07"
````