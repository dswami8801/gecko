package com.esphere.gecko.resources;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esphere.gecko.annotation.Endpoint;
import com.esphere.gecko.constant.RequestMethod;
import com.esphere.gecko.core.HttpRequest;
import com.esphere.gecko.core.HttpResponse;
import com.esphere.gecko.entity.Resource;

@Endpoint(value = "/404", method = RequestMethod.GET, produces = "text/html")
public class ResourceNotFound implements Resource {

	private static Logger LOGGER = LoggerFactory.getLogger(GreetingResource.class);

	@Override
	public void doServe(HttpRequest httpRequest, HttpResponse httpResponse) {
		LOGGER.info("Invoking doExecute");

		httpResponse.setStatusCode(404);
		httpResponse.setStatus("Not Found");
		httpResponse.addHeader("Content-Type", "text/html");
		httpResponse.setResponse("<p><font size='10' color='red'>404</font> Given Resource Not found.</p>");
		LOGGER.info("responding with headers " + httpResponse);
		httpResponse.commit();
	}

}
