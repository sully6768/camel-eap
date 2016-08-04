# Camel EAP Camel & CXF Contract First Interceptor
========================

This is an example of how to obtain the SOAP message as it moves through the Camel & CXF route.

In EAP the easiest way to gain access to the SOAP document is through the use of an Interceptor. 
In our case we are going to capture and update the SOAP Action element using a class appropriately
called the SoapActionInterceptor.  The SAI extends a helper class provided by CXF called the AbstractSoapInterceptor.

The helpers included in the ASI aren't specifically for reading documents. They are actually
there to help us determine when in the Interceptor Chain we want to take some kind of action.

There are a number of options and 

We use spring-web to bootstrap the Camel context. 
    
### Prerequisites

Before starting your instance of EAP you will want to enable logging for the CXF Endpoints.
To do this complete the following:

1. Open up the <JBoss Home>/standalone/configuration/standalone.xml (or what ever config your using)
2. Find the <system-properties> element and add the line below:
    ```
    <system-properties>
        <property name="hawtio.authenticationEnabled" value="true"/>
        <property name="hawtio.offline" value="true"/>
        <property name="hawtio.realm" value="hawtio-domain"/>
        
        <!-- ADD TO ENABLE CXF LOGGING -->
        <property name="org.apache.cxf.logging.enabled" value="true"/>
    </system-properties>
    ```

### Deploy
Deploy the WAR to your servlet container (EAP/Wildfly/Tomcat/Jetty), start the container, then try to hit this url
to see if the web service (SOAP) was deployed successfully:


    curl 'http://localhost:8080/contract-first/soap/order?wsdl'