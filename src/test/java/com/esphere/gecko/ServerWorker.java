package com.esphere.gecko;

/**
 * 
 */

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class ServerWorker {
	public static final int BUFFER_SIZE = 100;

	public static void main(String[] args) throws Exception {
		int port = 1989;

		ServerSocket serverSocket = new ServerSocket(port);

		while (true) {
			try {
				Socket clientSocket = serverSocket.accept();
				//clientSocket.setSoTimeout(1000);
				InputStream inputStream = clientSocket.getInputStream();
				BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

//				String s;
//				int filesize = 0;
//				while ((s = in.readLine()) != null && s.length() > 0) {
//					System.out.println(s);
//					if (s.contains("Content-Length")) {
//						filesize = Integer.parseInt(s.split(":")[1].trim());
//					}
//				}
				saveJarToFolder(inputStream, "input/"+UUID.randomUUID().toString()+".csv", 0);
				System.err.println("jar saved");
				out.close();
				in.close();
				clientSocket.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private static String saveJarToFolder(InputStream inputStream, String destinationFolder, int fileSize) {
		byte[] buffer = new byte[8192];
		int bytesread = 0, bytesBuffered = 0;
		int totalBytesReceived = 0;

		try (FileOutputStream outputStream = new FileOutputStream(destinationFolder);) {
			
			Files.copy(inputStream, Paths.get(destinationFolder));

//			while ((bytesread = inputStream.read(buffer,0,buffer.length)) > -1) {
//				System.err.println(bytesread);
//				outputStream.write(buffer, 0, bytesread);
//				bytesBuffered += bytesread;
//				totalBytesReceived += bytesread;
//				if (bytesBuffered > 1024 * 1024) {
//					bytesBuffered = 0;
//					outputStream.flush();
//				}
////				System.out.println(totalBytesReceived);
////				if (bytesread < buffer.length) {
////					outputStream.flush();
////					break;
////				}
//			}
//			outputStream.flush();
//			outputStream.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return destinationFolder;
	}

}
