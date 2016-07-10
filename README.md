# Camel EAP Demos

This is a collection of example Apache Camel applications for the JBoss EAP 6.4 WildFly-Camel subsystem. 

From this project you will be able to test a variety of Apache Camel Components using Spring Web and CDI frameworks, which can be deployed to an application
server running the WildFly-Camel subsystem. Below are further details on how to get setup as well as links to the exmple projects in this repository.

## Prerequisites

* Minimum of Java 1.7
* Maven 3.2 or greater
* JBoss EAP 6.4
* JBoss Fuse 6.2.1 Rollup 2 for EAP: [Download](https://access.redhat.com/jbossnetwork/restricted/softwareDetail.html?softwareId=44171&product=jboss.fuse&version=6.2.1&downloadType=patches "Fuse for EAP Download") 

## Getting started

1. Install JBoss EAP 6.4

1. Install JBoss Fuse 6.2.1 Rollup 2 on your EAP

2. Conifgure a `$JBOSS_HOME` environment variable to point at your application server installation directory

3. Start the application server from the command line

For Linux:

`$JBOSS_HOME/bin/standalone.sh -c standalone.xml`

For Windows:

`%JBOSS_HOME%\bin\standalone.bat -c standalone.xml`

### Building the application

To build the application do:

`mvn clean install`

### Run Arquillian Tests
    
By default, tests are configured to be skipped as Arquillian requires the use of a container.

If you already have a running application server, you can run integration tests with:

`mvn clean test -Parq-remote`

Otherwise you can get Arquillian to start and stop the server for you (Note: you must have `JBOSS_HOME` configured beforehand):

`mvn clean test -Parq-managed`

### Deploying the application

To deploy the application to a running application server do:

`mvn clean package wildfly:deploy` 

The server console should display lines like the following:

```
(MSC service thread 1-16) Apache Camel (CamelContext: spring-context) is starting
(MSC service thread 1-16) Camel context starting: spring-context
(MSC service thread 1-6) Bound camel naming object: java:jboss/camel/context/spring-context
(MSC service thread 1-16) Route: route4 started and consuming from: Endpoint[direct://start]
(MSC service thread 1-16) Total 1 routes, of which 1 is started
```

### Access the application

The application will be available at <http://localhost:8080/wildfly-camel-archetype-spring?name=Kermit>

### Undeploying the application

`mvn wildfly:undeploy`

## Further reading

* [WildFly-Camel documentation] (https://www.gitbook.com/book/wildflyext/wildfly-camel)
* [Apache Camel documentation] (http://camel.apache.org/)



[Camel EAP CDI](https://github.com/sully6768/camel-eap/tree/master/camel-eap-cdi "Camel EAP CDI")

[Camel EAP CXF Contract First](https://github.com/sully6768/camel-eap/tree/master/camel-eap-cxf-contract-first "Camel EAP CXF Contract First")

[Camel EAP Quartz Cluster](https://github.com/sully6768/camel-eap/tree/master/camel-eap-quartz-cluster "Camel EAP Quartz Cluster") 