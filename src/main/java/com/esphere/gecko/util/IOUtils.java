package com.esphere.gecko.util;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IOUtils {

	public static String saveFile(InputStream inputStream, String destinationFolder, int fileSize) {
		byte[] buffer = new byte[8192];
		int bytesread = 0, bytesBuffered = 0;
		int totalBytesReceived = 0;

		try (FileOutputStream outputStream = new FileOutputStream(destinationFolder);) {

			while ((bytesread = inputStream.read(buffer)) > -1) {

				outputStream.write(buffer, 0, bytesread);
				totalBytesReceived += bytesread;
				if (bytesBuffered > 1024 * 1024) {
					bytesBuffered = 0;
					outputStream.flush();
				}
				if (totalBytesReceived >= fileSize) {
					outputStream.flush();
					break;
				}
			}
			outputStream.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return destinationFolder;
	}

	public static String saveFileNIO(InputStream inputStream, String destinationFolder, int fileSize) {

		try (FileOutputStream outputStream = new FileOutputStream(destinationFolder);) {

			Files.copy(inputStream, Paths.get(destinationFolder));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return destinationFolder;
	}

}
