/**
 * 
 */
package com.redhat.wildfly.camel.spring.quartz;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * @author scottes
 *
 */
public class DateProcessor implements Processor {

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		exchange.getIn().setBody(new Date());
	}

}
