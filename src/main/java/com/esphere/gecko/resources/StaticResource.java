package com.esphere.gecko.resources;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esphere.gecko.annotation.Endpoint;
import com.esphere.gecko.core.HttpRequest;
import com.esphere.gecko.core.HttpResponse;
import com.esphere.gecko.entity.Resource;

@Endpoint(value = "/static")
public class StaticResource implements Resource {

	private static Logger LOGGER = LoggerFactory.getLogger(StaticResource.class);

	@Override
	public void doServe(HttpRequest httpRequest, HttpResponse httpResponse) {
		LOGGER.info("Invoking doServe");
		try {
			String path = httpRequest.getParam("filepath");
			LOGGER.info("responding with headers " + httpResponse);
			httpResponse.ok().commitMedia(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
