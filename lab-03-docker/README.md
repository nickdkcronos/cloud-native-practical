# Lab 03 - Docker

We've got a bare minimum application working, time to dockerize it. This will serve us well when deploying to any container orchastration platform.

We'll install a few prerequisites using HomeBrew package manager for MacOS, if you don't have it yet see:

https://brew.sh/

## Install Docker

If you haven't already, we need to install Docker:
```
brew install docker
```

## Dockerize our application

Now we're going to package our application as a Docker image. Add a Dockerfile to the module root, alongside the pom.xml:

```
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
```

Before we build this Dockerfile, make sure the latest code is packaged by explicitly doing a Maven build:

```
cd shopping-list
mvn package
```

Now do the Docker build:

```
docker build -t shopping-list:0.0.1-SNAPSHOT .
```

Run our Docker image, we're mapping our 8080 port to 8081 to avoid a possible conflicting running application:

```
docker run -p 8081:8080 shopping-list:0.0.1-SNAPSHOT
```

Verify it's up and running:

http://localhost:8081/actuator/health

A deep understanding of Docker is not needed for now, but try to research what is actually going on while running these commands.

## Commit and tag your work

Make sure to add, commit and push all your files at least once at the end of every lab. After the lab has been completed completely please tag it with the appropriate lab number:

````
git tag -a lab03 -m "lab03"
````