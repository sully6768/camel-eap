contract-first-camel-eap
========================

Demo of contract-first web services on JBoss EAP

We use spring-web to bootstrap the Camel context. 


### Build
Normal fat war:

    mvn clean install
    
Skinny war:

    mvn clean install -Pskinny
    
  

### Deploy
Deploy the WAR to your servlet container (EAP/Wildfly/Tomcat/Jetty), start the container, then try to hit this url
to see if the web service (SOAP) was deployed successfully:


    curl 'http://localhost:8080/contract-first/soap/order?wsdl'