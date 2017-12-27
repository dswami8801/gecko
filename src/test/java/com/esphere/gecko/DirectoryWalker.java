package com.esphere.gecko;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;

public class DirectoryWalker implements FileVisitor<Path> {

	

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		//System.out.println(file);
		Path path = Paths.get("C:\\temp\\streamEngine\\WEB-INF\\classes");
		String classPAth = file.toString().split("classes")[1].replaceFirst("[\\\\/]", "");
		if(classPAth.endsWith(".class")){
			try {
				LoadFromWar.loadClass(classPAth.replaceAll("[\\\\/]", ".").replace(".class", ""));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return FileVisitResult.CONTINUE;
	}

	

	public static void main(String[] args) throws IOException {
		Path path = Paths.get("C:\\temp\\streamEngine\\WEB-INF\\classes");
		Files.walkFileTree(path, new DirectoryWalker());
	}



	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		System.out.println("=========================="+dir.getFileName()+"==============================");
		return FileVisitResult.CONTINUE;
	}



	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		// TODO Auto-generated method stub
		return FileVisitResult.CONTINUE;
	}



	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		// TODO Auto-generated method stub
		return FileVisitResult.CONTINUE;
	}

}
