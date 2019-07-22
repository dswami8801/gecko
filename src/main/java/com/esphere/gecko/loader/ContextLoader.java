package com.esphere.gecko.loader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esphere.gecko.annotation.Endpoint;
import com.esphere.gecko.core.Context;
import com.esphere.gecko.core.HadlerMapping;
import com.esphere.gecko.core.ResourceMeta;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ContextLoader implements Loader {

	private static final Logger logger = LoggerFactory.getLogger(ContextLoader.class);

	private String contextPath;

	private final static String resource_path = "resources";

	public ContextLoader(String workingDirectory) {
		this.contextPath = workingDirectory;
	}

	public Path getResourcePath() {
		Path path = Paths.get(contextPath);
		return path.resolve(resource_path);
	}

	public void readContext() {
		logger.info("Loading contexts from location : {}", getResourcePath());
		try {
			Stream<Path> stream = Files.list(getResourcePath()).filter(new Predicate<Path>() {

				@Override
				public boolean test(Path arg0) {
					return arg0.toFile().isDirectory();
				}
			});

			stream.forEach((Path path) -> {

				logger.info("Loading context from " + path);
				try {
					readJarFile(path.toString());
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadContexts() {
		logger.info("Scanning for context " + getResourcePath());
		try {
			Files.list(getResourcePath()).filter((Path p) -> p.toFile().isFile()).forEach((Path p) -> {
				String jarPath = p.toString();
				String folderPath = jarPath.substring(0, jarPath.lastIndexOf("."));
				if (Paths.get(folderPath).toFile().exists()) {
					try {
						Files.walk(Paths.get(folderPath), FileVisitOption.FOLLOW_LINKS)
								.sorted(Comparator.reverseOrder()).map(Path::toFile).peek(System.out::println)
								.forEach(File::delete);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				extractJar(jarPath, folderPath);

			});
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void extractJar(String jarPath, String pathToExtract) {
		try {

			if (!Paths.get(pathToExtract).toFile().exists()) {
				logger.info("Creating folder for jar " + pathToExtract);
				Paths.get(pathToExtract).toFile().mkdir();
			}

			java.util.jar.JarFile jar = new java.util.jar.JarFile(jarPath);
			Enumeration<JarEntry> e = jar.entries();
			while (e.hasMoreElements()) {
				java.util.jar.JarEntry file = (java.util.jar.JarEntry) e.nextElement();
				java.io.File f = new java.io.File(pathToExtract + File.separator + file);
				if (file.isDirectory()) {
					f.mkdir();
					continue;
				}
				java.io.InputStream is = jar.getInputStream(file);
				java.io.FileOutputStream fos = new java.io.FileOutputStream(f);
				while (is.available() > 0) {
					fos.write(is.read());
				}
				fos.close();
				is.close();
			}
			jar.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}

	}

	public static void readJarFile(String jarPath) throws IOException, ClassNotFoundException {
		Path contextJson = Paths.get(jarPath).resolve("context.json");
		logger.info("Loading a new context from " + contextJson);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(contextJson.toFile());

		String contextPath = jsonNode.get("context").get("path").asText();

		Stream<Path> stream = Files.list(Paths.get(jarPath)).filter(new Predicate<Path>() {

			@Override
			public boolean test(Path arg0) {
				return arg0.toFile().getName().endsWith("jar");
			}
		});
		List<Path> jars = stream.collect(Collectors.toList());
		Set<ResourceMeta> metas = new HashSet<>();
		jars.forEach(jar->{
			try {
				metas.addAll(loadClasses(jar.toString()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Context context = new Context();
		context.setPath(contextPath);
		context.setMeta(metas);
		HadlerMapping.addMapping(contextPath, context);
	}

	private static Set<ResourceMeta> loadClasses(String jarPath)
			throws MalformedURLException, ClassNotFoundException, IOException {
		
		JarFile jarFile = new JarFile(new File(jarPath));
		
		Set<ResourceMeta> metas = new HashSet<>();
		Enumeration<JarEntry> enumeration = jarFile.entries();

		URL[] urls = { new URL("jar:file:" + jarPath + "!/") };
		URLClassLoader classLoader = new URLClassLoader(urls);

		while (enumeration.hasMoreElements()) {
			JarEntry jarEntry = (JarEntry) enumeration.nextElement();
			if (jarEntry.getName().endsWith(".class")) {
				String clazzName = jarEntry.getName().replace("/", ".").replace(".class", "");
				Class<?> clazz = classLoader.loadClass(clazzName);
				if (clazz.isAnnotationPresent(Endpoint.class)) {
					Endpoint endpoint = (Endpoint) (clazz.getAnnotations()[0]);
					ResourceMeta resourceMeta = new ResourceMeta();
					resourceMeta.setEndpoint(endpoint);
					resourceMeta.setClazz(clazz);
					metas.add(resourceMeta);
				}
			}
		}
		jarFile.close();
		classLoader.close();
		return metas;
	}

	public static String getFileType(String file) {
		return file.substring(file.lastIndexOf(".") + 1);
	}

	public static String pathToPackage(String path) {
		return path;

	}

}
