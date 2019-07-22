package com.esphere.gecko.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esphere.gecko.api.Server;
import com.esphere.gecko.entity.BeanNotValidException;
import com.esphere.gecko.exception.MappingNotFoundException;
import com.esphere.gecko.loader.ContextLoader;

public class HttpServer implements Server {

	private static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

	private boolean isRunning = false;

	private RequestDelegator delegator;

	public HttpServer(RequestDelegator delegator) {
		this.delegator = delegator;
	}

	public void start(ServerConfig config) {
		LOGGER.info("Starting server with config: {}",config.getPort());
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
			e.printStackTrace();
		}

		while (isRunning) {

			try {
				Socket socket = serverSocket.accept();
				LOGGER.info("New Client Request Accepted");
				LOGGER.info("Server handler " + delegator.getClass());
				delegator.delegate(socket);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (MappingNotFoundException e) {
				e.printStackTrace();
			}

		}

	}

	public void doValidate() throws BeanNotValidException {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		ServerConfig config = new ServerConfig();
		Options options = new Options();
		options.addOption("p", true, "server binding port");
		options.addOption("d", true, "Deploymemnt working directory");

		CommandLineParser parser = new BasicParser();

		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);

			if (cmd.hasOption("p")) {
				config.setPort(Integer.parseInt(cmd.getOptionValue("p")));
			} else {
				LOGGER.info("MIssing port , option -d");
				help(options);
			}

			if (cmd.hasOption("d")) {
				config.setWorkingDirectory(cmd.getOptionValue("d"));
			} else {
				LOGGER.info("MIssing working directory , option -d");
				help(options);
			}

			RequestDelegator delegator = new RequestDelegator(10);
			ContextLoader contextLoader = new ContextLoader(config.getWorkingDirectory());
			contextLoader.readContext();

			new HttpServer(delegator).start(config);

		} catch (ParseException e) {
			LOGGER.error("Failed to parse comand line properties", e);
			help(options);
		}
	}

	private static void help(Options options) {
		HelpFormatter formater = new HelpFormatter();
		formater.printHelp("Main", options);
		System.exit(0);
	}

}
