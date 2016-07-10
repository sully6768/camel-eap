# Camel on EAP Clustered Quartz Example

Installing Camel Quartz in EAP is rather straightforward.  The challenge comes when one needs to have the components perform as a singleton in a cluster of EAP servers.

## Prerequisites
To work with this example you will need to install and configure a datasource to manage cluster.  For this example I chose to use MySQL but any Quartz supported JDBC datastore will do.

* MySQL (I installed the latest 5.7.13 but any should do) [Download](http://dev.mysql.com/downloads/mysql/ "MySQL Download")  
* MySQL Driver [Download](https://dev.mysql.com/downloads/connector/j/ "MySQL Driver Download") 

## Optional Prerequisites

### Quartz 2.2.x

If you intend to use a datastore other than MySQL you will need to download the Quartz binary:

* Quartz 2.2.x [Download](http://d2zwv9pap9ylyd.cloudfront.net/quartz-2.2.3-distribution.tar.gz "Download")  

## Getting started

For our example we will be using a pair of JBoss EAP servers in standalone mode to demonstrate the Quartz2 cluster behavior.  You could just as easily do this using EAP in domain mode but for simplicity we will stick with standalone.  As such all configuration updates will happen under JBOSS_HOME/standalone.  

**Configure MySQL**

First we need to configure our test datastore.

1. Create a Quartz2 database, user and grant the user privileges
    
    ```
    prompt> mysql -u root -p
    sql> create database quartz2;
    sql> create user 'quartz2'@'localhost' identified by 'quartz2123';
    sql> grant all privileges on quartz2.* to 'quartz2'@'localhost';
    sql> exit;
    ```

2. Create the Quartz2 schema

    ```
	propt> mysql -u root -p quartz2 < $QUARTZ2_HOME/docs/dbTables/tables_mysql_innodb.sql
    ```

3. Install the MySQL Driver in the Application Server
    * Create the following directory structure under JBOSS_HOME

    ```
	export JBOSS_HOME=path-to-install
	cd $JBOSS_HOME
	mkdir -p modules/com/mysql/main
    ```
    * Create the modules descriptor for the MySQL Driver

    ```
    touch modules/com/mysql/main/module.xml
    ```
    
    * Open module.xml and add the following contents:
    
    ```
    <?xml version="1.0" encoding="UTF-8"?>
    <module xmlns="urn:jboss:module:1.0" name="com.mysql">
        <resources>
            <resource-root path="mysql-connector-java-5.1.39-bin.jar"/>
        </resources>
        <dependencies>
            <module name="javax.api"/>
            <module name="javax.transaction.api"/>
        </dependencies>
    </module>
    ```
    
    * Copy the downloaded MySQL driver to the `modules/com/mysql/main` directory

4. Add the driver to the standalone.xml JBoss configuration.  
    * Open JBOSS_HOME/standalone/configuration/standalone.xml with your favorite text editor. 
    * Search for the datasource subsystem element: 
        * `<subsystem xmlns="urn:jboss:domain:datasources:1.2">`
    * Add the following xml fragments to the datasources element:
    
    ```
    <datasource jndi-name="java:jboss/datasources/MYSQLQUARTZDS" pool-name="MYSQLQUARTZDS" enabled="true" use-java-context="true">
        <connection-url>jdbc:mysql://localhost:3306/databaseName</connection-url>
        <driver>mysqlDriver</driver>
        <security>
        <user-name>quartz2</user-name>
        <password>quartz2123</password>
        </security>
    </datasource>
    ```

    * Find the drivers element under the datasources element and add the following:

    ```
    <driver name="mysqlDriver" module="com.mysql">
        <xa-datasource-class>com.mysql.jdbc.Driver</xa-datasource-class>
    </driver>
    ```

We are now finished with the datastore setup.

**Spring Context Support EAP**

JBoss EAP, once the Fuse subsystem is applied, includes a number of APIs including Spring Context. It does not include the Spring Context Support APIs though which includes the Quartz helper APIs.  So lets add them to our installation.

* Create the following directory structure under JBOSS_HOME

    ```
    cd $JBOSS_HOME
    mkdir -p modules/org/springframework/context/support/main
    ```

* Create the modules descriptor for the Spring Context Support API

    ```
    touch modules/org/springframework/context/support/main/module.xml
    ```
    
* Open module.xml and add the following contents:
    
    ```
    <?xml version="1.0" encoding="UTF-8"?>
    <module xmlns="urn:jboss:module:1.1" name="org.springframework.context.support">
      <resources>
        <resource-root path="spring-context-support-4.1.6.RELEASE.jar" />
      </resources>
      <dependencies>
        <module name="javax.api" />
        <module name="org.apache.camel.script.groovy" />
        <module name="org.apache.commons.logging" />
        <module name="org.springframework.core" />
        <module name="org.springframework.context" />
        <module name="org.springframework.aop" />
        <module name="org.springframework.beans" />
        <module name="org.springframework.expression" />
        <module name="org.springframework.tx" />
        <module name="org.springframework.jdbc" />
        <module name="org.quartz" />
      </dependencies>
    </module>
    ```
    
    * Copy the `spring-context-support-4.1.6.RELEASE.jar` from your local maven repository to the main directory
    
    ```
    cp \
        ~/.m2/repository/org/springframework/spring-context-support/4.1.6.RELEASE/spring-context-support-4.1.6.RELEASE.jar \
        /opt/jboss/eap/jboss-eap-6.4-fuse-6.2.1-c1/modules/org/springframework/context/support/main/.
    ```     



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
