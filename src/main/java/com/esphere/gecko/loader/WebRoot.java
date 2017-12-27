package com.esphere.gecko.loader;

public class WebRoot {
	
	private String path; 
	
	private String name;
	
	private DirectoryResource classes;
	
	private DirectoryResource lib;
	
	private DirectoryResource libProvided;

	public DirectoryResource getClasses() {
		return classes;
	}

	public void setClasses(DirectoryResource classes) {
		this.classes = classes;
	}

	public DirectoryResource getLib() {
		return lib;
	}

	public void setLib(DirectoryResource lib) {
		this.lib = lib;
	}

	public DirectoryResource getLibProvided() {
		return libProvided;
	}

	public void setLibProvided(DirectoryResource libProvided) {
		this.libProvided = libProvided;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	

}
