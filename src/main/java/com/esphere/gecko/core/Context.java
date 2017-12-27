package com.esphere.gecko.core;

import java.util.Set;

public class Context {

	private String path;

	private Set<ResourceMeta> meta;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Set<ResourceMeta> getMeta() {
		return meta;
	}

	public void setMeta(Set<ResourceMeta> meta) {
		this.meta = meta;
	}

}
