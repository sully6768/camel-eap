package com.redhat.eap.camel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * Application Lifecycle Listener implementation class CamelListener
 *
 */
@WebListener
public class CamelServletListener implements ServletContextListener {

	@Resource(name = "java:jboss/camel/context/camel-context")
	private CamelContext camelContext;
	private static final String ROUTE_ID = "timerRoute1";
	
    /**
     * Default constructor. 
     */
    public CamelServletListener() {
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         try {
 			camelContext.stopRoute(ROUTE_ID, 5000, TimeUnit.MILLISECONDS, false);
			camelContext.removeRoute(ROUTE_ID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 

         try {
			camelContext.addRoutes(new RouteBuilder() {
				
				@Override
				public void configure() throws Exception {
					
					from("timer://foo?fixedRate=true&period=5000")
						.process(new Processor() {
							
							@Override
							public void process(Exchange exchange) throws Exception {
								exchange.getIn().setBody(new Date());
							}
						})
						.log("Message from timer FOO: ${body}")
						.setId(ROUTE_ID);
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
	
}
