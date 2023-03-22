package com.konloch;

import com.konloch.disklib.DiskReader;
import com.konloch.http.HTTPdLib;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Konloch
 * @since 2/28/2023
 */
public class TestHTTPServer
{
	public static void main(String[] args) throws IOException
	{
		HTTPdLib webserver = new HTTPdLib(80, request ->
		{
			switch(request.getPath())
			{
				case "/":
					return ("Hello!!! " + request.getRemoteIP()).getBytes(StandardCharsets.UTF_8);
					
				case "/hello":
					return "Bye".getBytes(StandardCharsets.UTF_8);
					
				case "/get":
					request.setContentType("text/html; charset=utf-8");
					return ("<html><form action=\"\" method=\"get\">\n" + "  <label for=\"fname\">First name:</label>\n" + "  <input type=\"text\" id=\"fname\" name=\"fname\"><br><br>\n" + "  <label for=\"lname\">Last name:</label>\n" + "  <input type=\"text\" id=\"lname\" name=\"lname\"><br><br>\n" + "  <input type=\"submit\" value=\"Submit\">\n" + "</form></html>").getBytes(StandardCharsets.UTF_8);
					
				case "/post":
					request.setContentType("text/html; charset=utf-8");
					return ("<html><form action=\"\" method=\"post\">\n" + "  <label for=\"fname\">First name:</label>\n" + "  <input type=\"text\" id=\"fname\" name=\"fname\"><br><br>\n" + "  <label for=\"lname\">Last name:</label>\n" + "  <input type=\"text\" id=\"lname\" name=\"lname\"><br><br>\n" + "  <input type=\"submit\" value=\"Submit\">\n" + "</form></html>").getBytes(StandardCharsets.UTF_8);
					
				case "/upload":
					request.setContentType("text/html; charset=utf-8");
					return ("<form action=\"\">\n" + "  <input type=\"file\" id=\"myFile\" name=\"filename\">\n" + "  <input type=\"submit\">\n" + "</form>").getBytes(StandardCharsets.UTF_8);
					
				case "/upload2":
					request.setContentType("text/html; charset=utf-8");
					return ("<form action=\"\" method=\"post\" enctype=\"multipart/form-data\">\n" + "  Select image to upload:\n" + "  <input type=\"file\" name=\"fileToUpload\" id=\"fileToUpload\">\n" + "  <input type=\"submit\" value=\"Upload Image\" name=\"submit\">\n" + "</form>").getBytes(StandardCharsets.UTF_8);
					
				case "/upload3":
					request.setContentType("text/html; charset=utf-8");
					return ("<form action=\"\" method=\"post\" enctype=\"application/x-www-form-urlencoded\">\n" + "  Select image to upload:\n" + "  <input type=\"file\" name=\"fileToUpload\" id=\"fileToUpload\">\n" + "  <input type=\"submit\" value=\"Upload Image\" name=\"submit\">\n" + "</form>").getBytes(StandardCharsets.UTF_8);
					
				case "/upload4":
					request.setContentType("text/html; charset=utf-8");
					return ("<form action=\"\" method=\"post\" enctype=\"text/plain\">\n" + "  Select image to upload:\n" + "  <input type=\"file\" name=\"fileToUpload\" id=\"fileToUpload\">\n" + "  <input type=\"submit\" value=\"Upload Image\" name=\"submit\">\n" + "</form>").getBytes(StandardCharsets.UTF_8);
					
				default:
					File parent = new File("./public");
					File file = new File(parent, request.getPath());
					
					//prevent escaping from the public directory by normalizing the paths
					if(file.getAbsolutePath().startsWith(parent.getAbsolutePath())
							&& file.exists())
						try
						{
							return DiskReader.readBytes(file);
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					
					request.setReturnCode(404);
					return "Error 404 file not found".getBytes(StandardCharsets.UTF_8);
			}
		});
		webserver.start();
		
		System.out.println("Running on http://localhost:" + webserver.getServer().getPort() + "/");
	}
	
	private static void testConnection() throws Exception
	{
	
	}
}
