package com.esphere.gecko.resources;

import org.apache.log4j.Logger;

import com.esphere.gecko.annotation.Endpoint;
import com.esphere.gecko.core.HttpRequest;
import com.esphere.gecko.core.HttpResponse;
import com.esphere.gecko.entity.Resource;

@Endpoint(value = "/jobs")
public class JobResource implements Resource {

	private static Logger LOGGER = Logger.getLogger(JobResource.class);

	@Override
	public void doServe(HttpRequest httpRequest, HttpResponse httpResponse) {
		LOGGER.info("Invoking doExecute");

		try {
			String path = "IndiGo.pdf";
			LOGGER.info("responding with headers " + httpResponse);
			httpResponse.ok().commitMedia(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
