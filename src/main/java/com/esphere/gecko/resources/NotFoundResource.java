package com.esphere.gecko.resources;

import org.apache.log4j.Logger;

import com.esphere.gecko.annotation.Endpoint;
import com.esphere.gecko.constant.RequestMethod;
import com.esphere.gecko.core.HttpRequest;
import com.esphere.gecko.core.HttpResponse;
import com.esphere.gecko.entity.Resource;

@Endpoint(value = "/404", method = RequestMethod.GET, produces = "text/html")
public class NotFoundResource implements Resource {

	private static Logger LOGGER = Logger.getLogger(ExecutorResource.class);

	@Override
	public void doServe(HttpRequest httpRequest, HttpResponse httpResponse) {
		LOGGER.info("Invoking doExecute");

		httpResponse.setStatusCode(404);
		httpResponse.setStatus("Not Found");
		httpResponse.addHeader("Content-Type", "text/html");
		httpResponse.setResponse("<p><img src=\"https://lh3.googleusercontent.com/UKql4vznZC4VQGin34ZXxjQlPPT8P4s6VmgLN0c5cMmbxzP_gGWfVKTEWGeM59cIZaKU=s108\">Error 404 Given Resource Not found.</p>");
		LOGGER.info("responding with headers " + httpResponse);
		httpResponse.commit();
	}

}
