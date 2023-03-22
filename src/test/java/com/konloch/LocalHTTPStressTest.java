package com.konloch;

import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author Konloch
 * @since 3/2/2023
 */
public class LocalHTTPStressTest
{
	public static long connections = 0;
	
	public static void main(String[] args)
	{
		final InetSocketAddress address = new InetSocketAddress("localhost", 80);
		
		//generally I would recommend more than two threads per server preforming the test, but adjust and experiment to fit your stack
		for(int i = 0; i < 2; i++)
		{
			Thread thread = new Thread(()->
			{
				while (true)
				{
					try
					{
						SocketChannel socket = SocketChannel.open(address);
						ByteBuffer buffer = ByteBuffer.wrap("GET / HTTP/1.1\nHost: localhost\nUser-Agent: Chrome\nAccept: */*\n\n".getBytes(StandardCharsets.UTF_8));
						socket.write(buffer);
						buffer.clear();
						socket.close();
						connections++;
						
						if (connections % 1000 == 0)
							System.out.println("Successful connections: " + connections);
					}
					catch (BindException e)
					{
					
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			});
			
			thread.start();
		}
	}
}
