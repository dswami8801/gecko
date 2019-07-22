package com.esphere.gecko.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esphere.gecko.annotation.Endpoint;
import com.esphere.gecko.constant.RequestMethod;
import com.esphere.gecko.core.HttpRequest;
import com.esphere.gecko.core.HttpResponse;
import com.esphere.gecko.entity.Resource;
import com.esphere.gecko.util.IOUtils;

@Endpoint(value = "/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
public class FileUploadResource implements Resource {

	private static Logger LOGGER = LoggerFactory.getLogger(FileUploadResource.class);

	@Override
	public void doServe(HttpRequest httpRequest, HttpResponse httpResponse) {
		LOGGER.info("Invoking doExecute");

		String filename = httpRequest.getParam("filename");
		Integer size = Integer.parseInt(httpRequest.getHeader("Content-Length").trim());

		String dest = IOUtils.saveFile(httpRequest.getInputStream(), filename, size);

		httpResponse.setStatusCode(201);
		httpResponse.setStatus("OK");
		httpResponse.addHeader("Content-Type", "text/html");
		httpResponse.setResponse("<p>" + filename + " saved at " + dest + "</p>");
		LOGGER.info("responding with headers " + httpResponse);
		httpResponse.commit();
	}

}
