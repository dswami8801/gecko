package com.esphere.gecko.api;

import com.esphere.gecko.core.ServerConfig;

public interface Server extends BeanValidator{
	
	public void start(ServerConfig config);
	
	public void stop();
	
	public boolean isRunning();

}
