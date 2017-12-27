package com.esphere.gecko;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.esphere.gecko.core.HttpRequestParser;

public class RequestParerTest {

	public static void main(String[] args) throws IOException {
		int port = 1989;
		final ServerSocket serverSocket = new ServerSocket(port);

		while (true) {

			try {
				Socket clientSocket = serverSocket.accept();

				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//
//				String s;
//				StringBuilder builder = new StringBuilder();
//				while ((s = in.readLine()) != null && s.length() > 0) {
//					builder.append(s+"\n");
//				}
				HttpRequestParser httpRequestParser=new HttpRequestParser(in);
				System.out.println(httpRequestParser.getRequest());
				out.write("HTTP/1.0 200 OK\r\n");
				out.write("Date:Sun, 28 June 2017 23:59:59 GMT\r\n");
				out.write("Server:Apache/0.8.4\r\n");
				out.write("Content-Type:text/html\r\n");
				out.write("Content-Length:59\r\n");
				out.write("Expires:Sat, 01 Jan 2018 00:59:59 GMT\r\n");
				out.write("Last-modified:Fri, 09 June 2017 14:21:40 GMT\r\n");
				out.write("\r\n");
				out.write("<h2>Example</h2>");

				out.close();
				in.close();
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
