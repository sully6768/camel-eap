/**
 * 
 */
package com.redhat.wildfly.camel.spring.jackson;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * @author scottes
 *
 */
public class DataProcessor implements Processor {

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "Scott");
		map.put("date", new Date());
		exchange.getIn().setBody(map);
	}

}
