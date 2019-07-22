package com.esphere.gecko.core;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esphere.gecko.annotation.Endpoint;
import com.esphere.gecko.entity.Resource;

public class HadlerMapping {

	private static Logger logger = LoggerFactory.getLogger(HadlerMapping.class);

	private static Map<String, Context> mappings = new ConcurrentHashMap<>();

	private String packageToScan = "com.esphere.gecko.resources";

	public HadlerMapping() {
		try {
			Context context = new Context();
			Set<ResourceMeta> metas = new HashSet<>();
			context.setMeta(metas);
			mappings.put("gecko", context);
			scanPackage();
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, Context> getMappings() {
		return mappings;
	}

	public static void addMapping(String url, Context context) {
		mappings.put(url, context);
	}

	public static Context getContext(String url) {
		return mappings.get(url);
	}

	public Set<Class<Resource>> getMapperClasses(String url) {
		return null;// mappings
	}

	public void scanPackage() throws IOException, URISyntaxException {
		URL url = getClass().getResource("/" + packageToScan.replaceAll("[\\.]", "/"));

		URLClassLoader classLoader = new URLClassLoader(new URL[] { url });
		Files.list(Paths.get(url.toURI())).forEach(new Consumer<Path>() {

			@Override
			public void accept(Path t) {
				String path = t.toString();
				String classPath = (packageToScan + "."
						+ path.substring(path.lastIndexOf(File.separator) + 1).replace(".class", ""));
				try {
					Class<Resource> clazz = (Class<Resource>) classLoader.loadClass(classPath);
					Endpoint endpoint = (Endpoint) (clazz.getAnnotations()[0]);
					ResourceMeta resourceMeta = new ResourceMeta();
					resourceMeta.setEndpoint(endpoint);
					resourceMeta.setClazz(clazz);
					mappings.get("gecko").getMeta().add(resourceMeta);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		classLoader.close();
	}

	public static ResourceMeta getEndpoint(HttpRequest request) {
		String endpoint = request.getEndpoint();
		endpoint = endpoint.replaceFirst("/", "");
		String context = endpoint.split("/")[0];
		String path = "/" + endpoint.split("/")[1];

		logger.info("In coming request for Context: " + context);
		logger.info("In coming request for Path: " + path);

		Context ctx = getContext(context);
		Set<ResourceMeta> metas = ctx.getMeta();
		List<ResourceMeta> resourceMetas = metas.stream().filter(new Predicate<ResourceMeta>() {

			@Override
			public boolean test(ResourceMeta meta) {
				return meta.getEndpoint().value().equals(path)
						&& meta.getEndpoint().method().equals(request.getRequestType());
			}
		}).distinct().collect(Collectors.toList());

		return resourceMetas.size() > 0 ? resourceMetas.get(0) : null;

	}

	public static ResourceMeta notFound(HttpRequest request) {
		String endpoint = request.getEndpoint();
		endpoint = endpoint.replaceFirst("/", "");
		String context = endpoint.split("/")[0];
		String path = "/404";

		Context ctx = getContext(context);
		Set<ResourceMeta> metas = ctx.getMeta();

		metas.stream().forEach((ResourceMeta meta) -> {
			logger.debug("{}", meta);
		});

		List<ResourceMeta> resourceMetas = metas.stream().filter(new Predicate<ResourceMeta>() {

			@Override
			public boolean test(ResourceMeta meta) {
				return meta.getEndpoint().value().equals(path)
						&& meta.getEndpoint().method().equals(request.getRequestType());
			}
		}).distinct().collect(Collectors.toList());

		return resourceMetas.size() > 0 ? resourceMetas.get(0) : null;

	}

}
