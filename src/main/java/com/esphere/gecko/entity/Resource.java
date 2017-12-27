package com.esphere.gecko.entity;

import com.esphere.gecko.core.HttpRequest;
import com.esphere.gecko.core.HttpResponse;

public interface Resource {
	
	public void doServe(HttpRequest httpRequest, HttpResponse httpResponse);

}
