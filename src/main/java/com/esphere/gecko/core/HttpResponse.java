package com.esphere.gecko.core;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import com.esphere.gecko.api.Response;

public class HttpResponse implements Response {

	private static String CLRF = "\r\n";

	private boolean isCommitted;

	private PrintWriter printWriter;

	private OutputStream outputStream;

	private Map<String, String> headers = new HashMap<String, String>();

	private Object response;

	private Integer statusCode;

	private String status;

	private String protocol = "HTTP/1.0";

	public HttpResponse(PrintWriter printWriter) {
		this.printWriter = printWriter;
	}

	public HttpResponse ok() {
		this.setStatus("OK");
		this.setStatusCode(200);
		return this;
	}

	protected void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public void addHeader(String key, String value) {
		headers.put(key, value);
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

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public boolean isCommitted() {
		return isCommitted;
	}

	public void setContentType(String contentType) {
		headers.put("Content-Type", contentType);
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public void commit() {
		printWriter.write(protocol + " " + statusCode + " " + status + CLRF);
		this.headers.forEach(new BiConsumer<String, String>() {

			@Override
			public void accept(String key, String value) {

				printWriter.write(key + ":" + value + CLRF);
			}
		});
		printWriter.write(CLRF);
		printWriter.write(response.toString());
		printWriter.flush();
		printWriter.close();
		isCommitted = true;
	}

	public void commitMedia(String path) throws IOException {

		Path path2 = Paths.get(path);
		File file = path2.toFile();
		this.addHeader("Content-Length ", Long.toString(file.length()));
		this.addHeader("Content-Disposition", "attachment;filename="+path);
		this.addHeader("Accept-Ranges", "bytes");
		this.setContentType("application/octet-stream");
		outputStream.write((protocol + " " + statusCode + " " + status + CLRF).getBytes());
		this.headers.forEach(new BiConsumer<String, String>() {

			@Override
			public void accept(String key, String value) {

				try {
					outputStream.write((key + ":" + value + CLRF).getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		outputStream.write(CLRF.getBytes());
		Files.copy(Paths.get(path), outputStream);
		outputStream.flush();
		outputStream.close();
		isCommitted = true;
	}

	@Override
	public String toString() {
		return "HttpResponse [headers=" + headers + ", response=" + response + ", statusCode=" + statusCode
				+ ", status=" + status + ", protocol=" + protocol + "]";
	}

}
