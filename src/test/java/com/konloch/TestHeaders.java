package com.konloch;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author Konloch
 * @since 2/28/2023
 */
public class TestHeaders
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		Socket socket = new Socket("localhost", 80);
		socket.getOutputStream().write("method: GET\npath: /\n\n".getBytes(StandardCharsets.UTF_8));
		socket.getOutputStream().flush();
		
		Thread.sleep(2000);
		
		int read;
		while(socket.getInputStream().available() > 0
				&& (read = socket.getInputStream().read()) > 0)
		{
			System.out.print((char) read);
		}
	}
}
