package com.esphere.gecko.core;

import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.esphere.gecko.exception.MappingNotFoundException;

public class RequestDelegator {

	private static Logger LOGGER = Logger.getLogger(RequestDelegator.class);

	private ExecutorService executorService;


	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public RequestDelegator(Integer capacity) {
		executorService = Executors.newFixedThreadPool(capacity);
	}

	public void delegate(Socket socket) throws MappingNotFoundException {

		Future<Object> future=executorService.submit(new RequestHandler(socket));
		
//		try {
//			future.get();
//		} catch (InterruptedException|ExecutionException e) {
//			e.printStackTrace();
//		}

	}

}
