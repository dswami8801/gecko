package com.esphere.gecko.core;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.esphere.gecko.api.Request;
import com.esphere.gecko.constant.RequestMethod;

public class HttpRequest implements Request {

	private Map<String, String> parameters = new HashMap<String, String>();

	private Map<String, String> headers = new HashMap<String, String>();

	private InputStream inputStream;

	private RequestMethod requestType;

	private String endpoint;

	private String protocol;

	public void addParameter(String key, String value) {
		parameters.put(key, value);
	}

	protected void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

	public RequestMethod getRequestType() {
		return requestType;
	}

	protected void setRequestType(RequestMethod requestType) {
		this.requestType = requestType;
	}

	public String getEndpoint() {
		return endpoint;
	}

	protected void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getProtocol() {
		return protocol;
	}

	protected void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	protected void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	protected void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getHeader(String key) {
		return headers.get(key);
	}

	public String getParam(String key) {
		return parameters.get(key);
	}

	@Override
	public String toString() {
		return "HttpRequest [parameters=" + parameters + ", headers=" + headers + ", requestType=" + requestType
				+ ", endpoint=" + endpoint + ", protocol=" + protocol + "]";
	}

}
