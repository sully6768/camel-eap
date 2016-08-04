package io.github.sully6768.examples.camel.cxf.eap.interceptor;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.databinding.DataReader;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.ServiceModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import camelinaction.order.Secured;

public class BasicAuthAuthorizationInterceptor extends AbstractSoapInterceptor {
	private static final QName HEADER_TYPE = 
	        new QName("http://order.camelinaction",
	                  "Secured");
	private static final Logger LOGGER = LoggerFactory.getLogger(BasicAuthAuthorizationInterceptor.class);

	public BasicAuthAuthorizationInterceptor() {
		super(Phase.PRE_INVOKE);
	}

	public void handleMessage(SoapMessage message) throws Fault {

		LOGGER.info("In the Message Handler");

		Header header = message.getHeader(HEADER_TYPE);
		if(header != null) {

			LOGGER.info("Secured Header found... begin processing");
	        Service service =
	            ServiceModelUtil.getService(message.getExchange());
	        DataReader<Node> dataReader =
	            service.getDataBinding().createReader(Node.class);
	        Secured secured =
	            (Secured)dataReader.read(HEADER_TYPE,
	                 (Node)header.getObject(), Secured.class);

			LOGGER.info("Secured User ID : " + secured.getUser());
			LOGGER.info("Secured Password: " + secured.getPassword());
	        
	        message.setContextualProperty(
	        		Secured.class.getName(), secured);
	    }

		
	}

}