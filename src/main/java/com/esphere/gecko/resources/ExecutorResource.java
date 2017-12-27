package com.esphere.gecko.resources;

import org.apache.log4j.Logger;

import com.esphere.gecko.annotation.Endpoint;
import com.esphere.gecko.constant.RequestMethod;
import com.esphere.gecko.core.HttpRequest;
import com.esphere.gecko.core.HttpResponse;
import com.esphere.gecko.entity.Resource;

@Endpoint(value = "/executors", method = RequestMethod.GET, produces = "text/html")
public class ExecutorResource implements Resource {

	private static Logger LOGGER = Logger.getLogger(ExecutorResource.class);

	@Override
	public void doServe(HttpRequest httpRequest, HttpResponse httpResponse) {
		LOGGER.info("Invoking doExecute");

		httpResponse.setStatusCode(200);
		httpResponse.setStatus("OK");
		httpResponse.addHeader("Content-Type", "text/html");
		httpResponse.setResponse("<p>ExecutorResource</p>");
		LOGGER.info("responding with headers " + httpResponse);
		httpResponse.commit();
	}

}
