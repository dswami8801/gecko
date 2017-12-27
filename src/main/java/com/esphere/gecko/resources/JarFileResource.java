package com.esphere.gecko.resources;

import java.util.UUID;

import org.apache.log4j.Logger;

import com.esphere.gecko.annotation.Endpoint;
import com.esphere.gecko.constant.RequestMethod;
import com.esphere.gecko.core.HttpRequest;
import com.esphere.gecko.core.HttpResponse;
import com.esphere.gecko.entity.Resource;
import com.esphere.gecko.util.IOUtils;

@Endpoint(value = "/jar", method = RequestMethod.GET,consumes="multipart/form-data")
public class JarFileResource implements Resource {

	private static Logger LOGGER = Logger.getLogger(JarFileResource.class);

	@Override
	public void doServe(HttpRequest httpRequest, HttpResponse httpResponse) {
		LOGGER.info("Invoking doExecute");
		
		String filename=httpRequest.getParam("filename");
		Integer size = Integer.parseInt(httpRequest.getHeader("Content-Length").trim());
		
		String dest=IOUtils.saveFile(httpRequest.getInputStream(), "input/"+UUID.randomUUID().toString()+".csv", Integer.MAX_VALUE);

		httpResponse.setStatusCode(201);
		httpResponse.setStatus("OK");
		httpResponse.addHeader("Content-Type", "text/html");
		httpResponse.setResponse("<p>"+filename+" saved at "+dest+"</p>");
		LOGGER.info("responding with headers " + httpResponse);
		httpResponse.commit();
	}

}
