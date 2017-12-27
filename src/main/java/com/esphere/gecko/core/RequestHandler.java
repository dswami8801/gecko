package com.esphere.gecko.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.esphere.gecko.api.ServerHandler;
import com.esphere.gecko.entity.BeanNotValidException;
import com.esphere.gecko.entity.Resource;
import com.esphere.gecko.exception.MappingNotFoundException;
import com.esphere.gecko.resources.NotFoundResource;

public class RequestHandler implements ServerHandler, Callable<Object> {

	private static Logger LOGGER = Logger.getLogger(RequestHandler.class);

	private RequestBuilder requestBuilder = new RequestBuilder();

	private ResponseBuilder responseBuilder = new ResponseBuilder();

	private Socket clientSocket;

	public void setRequestBuilder(RequestBuilder requestBuilder) {
		this.requestBuilder = requestBuilder;
	}

	public void setResponseBuilder(ResponseBuilder responseBuilder) {
		this.responseBuilder = responseBuilder;
	}

	public RequestHandler(Socket socket) {
		this.clientSocket = socket;
	}

	public void doHandle(Socket socket) throws MappingNotFoundException {

		try {
			doValidate();
		} catch (BeanNotValidException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			HttpRequest request = (HttpRequest) requestBuilder.build(bufferedReader);
			request.setInputStream(inputStream);
			LOGGER.info("Request Built " + request);
			HttpResponse response = (HttpResponse) responseBuilder.build(outputStream);
			LOGGER.info("Loading Executor class");
			try {
				Class<?> clazz = null;
				ResourceMeta meta = HadlerMapping.getEndpoint(request);
				if (meta == null) {
					LOGGER.info("No handler foung for given endpoint " + request.getEndpoint());
					clazz = NotFoundResource.class;
				} else {
					clazz = meta.getClazz();
					response.addHeader("Content-Type", meta.getEndpoint().produces());
				}
				LOGGER.info("Loaded Executor class " + clazz);

				Method main = clazz.getDeclaredMethod("doServe", HttpRequest.class, HttpResponse.class);
				main.invoke(clazz.newInstance(), request, response);

			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			bufferedReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void doValidate() throws BeanNotValidException {
		Objects.requireNonNull(requestBuilder);
		Objects.requireNonNull(responseBuilder);

	}

	@Override
	public Object call() throws MappingNotFoundException {
		doHandle(clientSocket);
		return null;
	}

}
