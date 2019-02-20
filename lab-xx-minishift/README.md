# Lab xx - Minishift

We'll install a few prerequisites using HomeBrew package manager for MacOS, if you don't have it yet see:

https://brew.sh/

## Install Minishift

If you haven't already, we need to install Docker before we can install OpenShift:
```
brew install docker
```

To use OpenShift we're going to use Minishift:

https://github.com/minishift/minishift

> Minishift is a tool that helps you run OpenShift locally by running a single-node OpenShift cluster inside a VM. You can try out OpenShift or develop with it, day-to-day, on your local host.

Install xhyve
```
brew update
brew install docker-machine-driver-xhyve
sudo chown root:wheel /usr/local/opt/docker-machine-driver-xhyve/bin/docker-machine-driver-xhyve
sudo chmod u+s /usr/local/opt/docker-machine-driver-xhyve/bin/docker-machine-driver-xhyve
```

Install the OpenShift CLI
```
brew install openshift-cli
```

Install Minishift
```
brew cask install minishift
```

Start Minishift
```
minishift start --vm-driver xhyve
```

During this process, note down the IP address of the VM, example:

```
Determining server IP ...
Using public hostname IP 192.168.64.2 as the host IP
```

Note: If you have an old version of minishift around on your machine, it may be a good idea to delete it first:

```
minishift delete
```

Immediately stop minishift after this step and check the output for “The server is accessible via web console at”. Take a note of this ip.

```
minishift stop
```

Start minishift again, but now add arguments to configure an insecure registry. This will make sure that the docker engine in the minishift VM is able to access the registry that is integrated in minishift.

```
minishift start --vm-driver xhyve --insecure-registry 172.30.0.0/16 --insecure-registry minishift --insecure-registry docker-registry-default.$(minishift ip).nip.io:80
```

Configure oc (The openshiftconfig binary) on your terminal.

```
eval $(minishift oc-env)
```

You need to repeat this step once in every terminal where you want to run oc.

Now it's time to configure your admin user. Login with the system:admin role:

```
oc login -u system:admin
```

Note: in case you receive the following error "error: dial tcp 192.168.99.100:8443: i/o timeout", you might have a conflict with a previous installation of minishift. Uninstalling and re-installing minishift will solve this issue.

Add the admin role to the admin user:

```
oc adm policy add-cluster-role-to-user admin admin
```

This makes sure that you are able to login as cluster administrator in both the API and web console.

##Dockerize our application

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

## Pushing our Docker image to the OpenShift registry

Some magic going on here, make your terminal session use the Docker daemon inside the Minishift VM, this can be a bit confusing especially since this only enabled the current terminal session. For background info, see:

https://docs.okd.io/latest/minishift/using/docker-daemon.html

```
minishift docker-env
eval $(minishift docker-env)
```

Now login to the Docker registry:
```
docker login -u developer -p $(oc whoami -t) $(minishift openshift registry)
```

Let's tag our latest Docker image to be able to push it to the OpenShift registry:

```
docker tag shopping-list:0.0.1-SNAPSHOT $(minishift openshift registry)/myproject/shopping-list:0.0.1-SNAPSHOT
```

Push the image to the registry:

```
docker push $(minishift openshift registry)/myproject/shopping-list:0.0.1-SNAPSHOT
```

