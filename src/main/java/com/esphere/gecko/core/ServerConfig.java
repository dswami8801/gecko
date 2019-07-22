package com.esphere.gecko.core;

public class ServerConfig {

	private Integer port;

	private String host;

	private String workingDirectory;

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getWorkingDirectory() {
		return workingDirectory;
	}

	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	@Override
	public String toString() {
		return "[port=" + port + ", host=" + host + ", workingDirectory=" + workingDirectory + "]";
	}

}
