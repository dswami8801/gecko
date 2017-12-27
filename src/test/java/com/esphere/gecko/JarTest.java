package com.esphere.gecko;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarTest {
	
	private static void loadJar() {
		try {
			java.util.jar.JarFile jar = new java.util.jar.JarFile("original-tumblr-api-1.0-SNAPSHOT.jar");
			java.util.Enumeration e = jar.entries();
			while (e.hasMoreElements()) {
				java.util.jar.JarEntry file = (java.util.jar.JarEntry) e.nextElement();
				java.io.File f = new java.io.File("input" + java.io.File.separator + file.getName());
				if (file.isDirectory()) { // if its a directory, create it
					f.mkdir();
					continue;
				}
				java.io.InputStream is = jar.getInputStream(file); // get the input stream
				java.io.FileOutputStream fos = new java.io.FileOutputStream(f);
				while (is.available() > 0) {  // write contents of 'is' to 'fos'
					fos.write(is.read());
				}
				fos.close();
				is.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
		}

	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		String pathToJar = "original-tumblr-api-1.0-SNAPSHOT.jar";
		
		String packageName= "com.fi.tumblr";
		
		String driver = packageName+".remote.AppLauncher";
		
		//loadJar();

		JarFile jarFile = new JarFile(new File(pathToJar));

		Enumeration<JarEntry> enumeration = jarFile.entries();

		URL[] urls = { new URL("jar:file:" + pathToJar + "!/") };
		URLClassLoader classLoader = new URLClassLoader(urls);

		while (enumeration.hasMoreElements()) {
			JarEntry jarEntry = (JarEntry) enumeration.nextElement();
			System.out.println(jarEntry);
			String clazzName = jarEntry.getName().replace("/", ".").replace(".class", "");
//			if(clazzName.startsWith(driver) ){
//				Class class1=classLoader.loadClass(clazzName);
//				Method main=class1.getMethod("main", String[].class);
//				String[] params = {"animals","true","true"};
//				main.invoke(null, (Object)params);
//			}

		}

	}

}
