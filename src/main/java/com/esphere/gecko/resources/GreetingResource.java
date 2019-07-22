package com.esphere.gecko.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esphere.gecko.annotation.Endpoint;
import com.esphere.gecko.constant.RequestMethod;
import com.esphere.gecko.core.HttpRequest;
import com.esphere.gecko.core.HttpResponse;
import com.esphere.gecko.entity.Resource;

@Endpoint(value = "/greet", method = RequestMethod.GET, produces = "text/html")
public class GreetingResource implements Resource {

	private static Logger LOGGER = LoggerFactory.getLogger(GreetingResource.class);

	@Override
	public void doServe(HttpRequest httpRequest, HttpResponse httpResponse) {
		LOGGER.info("Invoking doServe");
		httpResponse.setStatusCode(200);
		httpResponse.setStatus("OK");
		httpResponse.addHeader("Content-Type", "text/html");
		httpResponse.setResponse("Hi " + httpRequest.getParam("name"));
		LOGGER.info("responding with headers " + httpResponse);
		httpResponse.commit();
	}

}
