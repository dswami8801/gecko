package com.esphere.gecko.api;

import java.net.Socket;

import com.esphere.gecko.exception.MappingNotFoundException;

public interface ServerHandler extends BeanValidator {
	
	public void doHandle(Socket socket) throws MappingNotFoundException;

}
