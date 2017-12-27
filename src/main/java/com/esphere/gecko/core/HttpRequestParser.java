package com.esphere.gecko.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.esphere.gecko.api.Request;
import com.esphere.gecko.constant.RequestMethod;

public class HttpRequestParser {

	private Map<String, String> headers;

	private String requestLine;

	public HttpRequestParser(BufferedReader reader) {
		headers = new HashMap<String, String>();
		parse(reader);
	}

	private void parse(BufferedReader reader) {

		String line;
		int count = 0;
		try {
			while ((line = reader.readLine()) != null && line.length() > 0) {
				if (count == 0) {
					requestLine = line;
				}
				putInHeaders(line);
				count++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void putInHeaders(String line) {
		String[] strings = line.split(":");
		if (strings.length > 1) {
			headers.put(strings[0], strings[1]);
		}

	}

	private void enhanceRequest(HttpRequest httpRequest) {
		String[] strings = requestLine.split(" ");
		httpRequest.setRequestType(RequestMethod.valueOf(strings[0]));
		httpRequest.setEndpoint(strings[1].split("\\?")[0]);
		httpRequest.setProtocol(strings[2]);
		// set parameters from url endpoint

		if (strings[1].split("\\?").length > 1) {
			String[] params = strings[1].split("\\?")[1].split("&");
			for (int i = 0; i < params.length; i++) {
				String[] keyValue = params[i].split("=");
				httpRequest.addParameter(keyValue[0], keyValue[1]);
			}
		}
	}

	public Request getRequest() {
		HttpRequest request = new HttpRequest();
		request.setHeaders(headers);
		enhanceRequest(request);
		return request;
	}

}
