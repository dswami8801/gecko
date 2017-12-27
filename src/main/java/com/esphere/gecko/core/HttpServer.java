package com.esphere.gecko.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.esphere.gecko.api.Server;
import com.esphere.gecko.entity.BeanNotValidException;
import com.esphere.gecko.exception.MappingNotFoundException;
import com.esphere.gecko.loader.ContextLoader;

public class HttpServer implements Server {

	private static Logger LOGGER = Logger.getLogger(HttpServer.class);

	private boolean isRunning = false;

	private RequestDelegator delegator;

	public HttpServer(RequestDelegator delegator) {
		this.delegator = delegator;
	}

	public void start(ServerConfig config) {
		LOGGER.info("Server start Initiated");
		isRunning = true;
		doStart(config);

	}

	public void stop() {
		isRunning = false;
	}

	public boolean isRunning() {
		return isRunning;
	}

	private void doStart(ServerConfig config) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(config.getPort());
			LOGGER.debug("Server Socket created");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (isRunning) {

			try {
				Socket socket = serverSocket.accept();
				LOGGER.info("New Client Request Accepted");
				LOGGER.info("Server handler " + delegator.getClass());
				delegator.delegate(socket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MappingNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void doValidate() throws BeanNotValidException {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		ServerConfig config = new ServerConfig();
		config.setPort(9999);
		RequestDelegator delegator = new RequestDelegator(10);
		ContextLoader.readContext();
		HadlerMapping.getMappings();
		new HttpServer(delegator).start(config);

	}

}
