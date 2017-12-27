package com.esphere.gecko.core;

import com.esphere.gecko.annotation.Endpoint;

public class ResourceMeta {

	private Endpoint endpoint;

	private Class clazz;

	public Endpoint getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	@Override
	public String toString() {
		return "ResourceMeta [endpoint=" + endpoint + ", clazz=" + clazz + "]";
	}

}
