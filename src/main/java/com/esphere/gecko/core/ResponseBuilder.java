package com.esphere.gecko.core;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.esphere.gecko.api.Builder;
import com.esphere.gecko.api.Response;

public class ResponseBuilder implements Builder {

	private static Logger LOGGER = Logger.getLogger(HttpServer.class);

	@SuppressWarnings("unchecked")
	ThreadLocal<SimpleDateFormat> local = new ThreadLocal() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
		}
	};

	public Response build(OutputStream outputStream) {
		LOGGER.info("Building response object");
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream));
		HttpResponse response = new HttpResponse(printWriter);
		response.addHeader("Server", "Gecko/1.0");
		response.addHeader("Date", local.get().format(new Date()));
		response.addHeader("Expires", local.get().format(new Date()));
		response.addHeader("Last-modified", local.get().format(new Date()));
		response.setOutputStream(outputStream);
		LOGGER.info("Build response object" + response);
		return response;
	}

}
