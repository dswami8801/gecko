package com.esphere.gecko.core;

import java.io.BufferedReader;

import com.esphere.gecko.api.Builder;
import com.esphere.gecko.api.Request;

public class RequestBuilder implements Builder {

	private HttpRequestParser httpRequestParser;

	public Request build(BufferedReader bufferedReader) {
		httpRequestParser = new HttpRequestParser(bufferedReader);
		return httpRequestParser.getRequest();
	}

}
