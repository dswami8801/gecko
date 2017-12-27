package com.esphere.gecko;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class LoadFromWar {
	static URLClassLoader classLoader = null;
	static {
		try {
			classLoader = new URLClassLoader(
					new URL[] { new File("C:\\temp\\tumblr-api-1.0-SNAPSHOT").toURI().toURL(),
							new File("C:\\temp\\tumblr-api-1.0-SNAPSHOT\\httpcore-4.4.6.jar").toURI().toURL(),
							new File("C:\\temp\\tumblr-api-1.0-SNAPSHOT\\httpclient-4.3.4.jar").toURI().toURL()
							
							});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public static Class<?> loadClass(String clazz) throws ClassNotFoundException {

		return classLoader.loadClass(clazz);
	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		System.out.println(LoadFromWar.loadClass("com.fi.tumblr.remote.TumblrClient").newInstance());
	}

}
